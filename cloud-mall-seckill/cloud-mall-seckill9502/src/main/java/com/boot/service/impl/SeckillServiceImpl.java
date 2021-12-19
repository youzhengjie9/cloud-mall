package com.boot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boot.constant.SeckillState;
import com.boot.dao.SeckillMapper;
import com.boot.pojo.Seckill;
import com.boot.pojo.SeckillSuccess;
import com.boot.service.SeckillService;
import com.boot.utils.SnowId;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 游政杰
 * @date 2021/12/17
 */
@Transactional
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

  private final String SECKILL_NUMBER_KEY = "seckill_number_"; // 秒杀库存key

  private final String SECKILL_LIMIT_KEY = "seckill_limit_"; // 秒杀限购key

  private final String SECKILL_USER_KEY = "seckill_user_"; // 秒杀用户key

  private final String SECKILL_TIME_KEY = "seckill_time_"; // 秒杀开始时间和结束时间key

  @Autowired private RedisTemplate redisTemplate;

  @Autowired private SeckillMapper seckillMapper;

  @Autowired private RabbitTemplate rabbitTemplate;

  @GlobalTransactional(name = "seata_seckill", rollbackFor = Exception.class)
  @Override
  public String seckill(long seckillId, long userid) {

    try {
      // 判断是否超出秒杀时间
      Object o = redisTemplate.opsForValue().get(SECKILL_TIME_KEY + seckillId);
      String value = String.valueOf(o);
      String[] split = value.split(",");
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date nowtime = new Date(System.currentTimeMillis()); // 当前时间
      Date startTime = simpleDateFormat.parse(split[0]);
      Date endTime = simpleDateFormat.parse(split[1]);
      if (nowtime.before(endTime) && nowtime.after(startTime)) { // 秒杀的时间合法

        // 从缓存中查询秒杀商品库存
        Integer number =
            (Integer) redisTemplate.opsForValue().get(SECKILL_NUMBER_KEY + seckillId); // 库存

        if (number <= 0) {
          return SeckillState.LOW_STOCK;
        } else { // 说明库存足够
          JSONObject jsonObject = new JSONObject();
          jsonObject.put("userid", userid);
          jsonObject.put("seckillId", seckillId);
          String msg = jsonObject.toJSONString(); // 变成json发送到rabbitmq
          // 判断用户是否超出秒杀限制
          Object st = redisTemplate.opsForValue().get(SECKILL_USER_KEY + userid + "_" + seckillId);
          if (st == null) { // 说明压根就没有参与这件商品的秒杀
            // 然后库存缓存-1
            number--;
            redisTemplate
                .opsForValue()
                .set(SECKILL_NUMBER_KEY + seckillId, number, 7, TimeUnit.DAYS);
            // 设置用户参与过秒杀
            redisTemplate
                .opsForValue()
                .set(SECKILL_USER_KEY + userid + "_" + seckillId, 1, 7, TimeUnit.DAYS);
            // 把秒杀请求发送到消息队列
            rabbitTemplate.convertAndSend("seckill_Exchange", "seckill_key", msg);
            return SeckillState.SUCCESS;
          } else { // 如果有缓存，则判断它的值是否允许秒杀
            String s = String.valueOf(st);
            int limit = Integer.parseInt(s);
            Integer secLimit =
                (Integer) redisTemplate.opsForValue().get(SECKILL_LIMIT_KEY + seckillId);
            if (limit < secLimit) {
              // 然后库存缓存-1
              number--;
              redisTemplate
                  .opsForValue()
                  .set(SECKILL_NUMBER_KEY + seckillId, number, 7, TimeUnit.DAYS);
              limit++;
              // 设置用户参与过秒杀
              redisTemplate
                  .opsForValue()
                  .set(SECKILL_USER_KEY + userid + "_" + seckillId, limit, 7, TimeUnit.DAYS);
              // 把秒杀请求发送到消息队列
              rabbitTemplate.convertAndSend("seckill_Exchange", "seckill_key", msg);
              return SeckillState.SUCCESS;
            }
            else {
              return SeckillState.BEYOUND_LIMIT;
            }
          }
        }

      } else {

        return SeckillState.NOT_IN_TIME;
      }
    } catch (Exception e) {

      throw new RuntimeException("秒杀失败");
    }
  }

  @Override
  public List<Seckill> selectAllSeckill() {
    return seckillMapper.selectAllSeckill();
  }

  @Override
  public void decrSeckillNumber(long seckillId) {
    seckillMapper.decrSeckillNumber(seckillId);
  }

  @Override
  public void insertSeckillSuccess(SeckillSuccess seckillSuccess) {
    seckillMapper.insertSeckillSuccess(seckillSuccess);
  }

  @GlobalTransactional(name = "seata_seckillbegin", rollbackFor = Exception.class)
  @Override
  public void seckillbegin(SeckillSuccess seckillSuccess) {

    try {
      seckillMapper.decrSeckillNumber(seckillSuccess.getSecKill().getSeckillId());

      seckillMapper.insertSeckillSuccess(seckillSuccess);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
