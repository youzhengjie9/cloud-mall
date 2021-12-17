package com.boot.service.impl;

import com.boot.dao.SeckillMapper;
import com.boot.pojo.Seckill;
import com.boot.pojo.SeckillSuccess;
import com.boot.service.SeckillService;
import com.boot.utils.SnowId;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private final String SECKILL_NUMBER_KEY="seckill_number_"; //秒杀库存key

    private final String SECKILL_LIMIT_KEY="seckill_limit_";//秒杀限购key

    private final String SECKILL_USER_KEY="seckill_user_"; //秒杀用户key

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillMapper seckillMapper;

    @GlobalTransactional(name = "seata_seckill",rollbackFor = Exception.class)
    @Override
    public boolean seckill(long seckillId,long userid) {

        try{

            //判断是否超出秒杀时间


            //从缓存中查询秒杀商品库存
            Integer number = (Integer) redisTemplate.opsForValue().get(SECKILL_NUMBER_KEY + seckillId);//库存

            if(number<=0)
            {
                return false;
            }else { //说明库存足够

                //判断用户是否超出秒杀限制
                Object st = redisTemplate.opsForValue().get(SECKILL_USER_KEY + userid + "_" + seckillId);
                if(st==null){ //说明压根就没有参与这件商品的秒杀
                    //把秒杀请求发送到消息队列
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    String createtime = simpleDateFormat.format(date);
                    SeckillSuccess seckillSuccess = new SeckillSuccess();
                    seckillSuccess.setId(SnowId.nextId())
                                  .setSecKill(new Seckill().setSeckillId(seckillId))
                                  .setState(0)
                                  .setUserId(userid)
                                  .setCreateTime(createtime);
                    seckillMapper.decrSeckillNumber(seckillId);
                    seckillMapper.insertSeckillSuccess(seckillSuccess);
                    //然后库存缓存-1
                    number--;
                    redisTemplate.opsForValue().set(SECKILL_NUMBER_KEY+seckillId,number,7, TimeUnit.DAYS);
                    //设置用户参与过秒杀
                    redisTemplate.opsForValue().set(SECKILL_USER_KEY+userid+"_"+seckillId,1,7,TimeUnit.DAYS);
                    return true;
                }else { //如果有缓存，则判断它的值是否允许秒杀
                    String s = String.valueOf(st);
                    int limit = Integer.parseInt(s);
                    Integer secLimit = (Integer) redisTemplate.opsForValue().get(SECKILL_LIMIT_KEY + seckillId);
                    if(limit<secLimit)
                    {
                        //把秒杀请求发送到消息队列
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        String createtime = simpleDateFormat.format(date);
                        SeckillSuccess seckillSuccess = new SeckillSuccess();
                        seckillSuccess.setId(SnowId.nextId())
                                .setSecKill(new Seckill().setSeckillId(seckillId))
                                .setState(0)
                                .setUserId(userid)
                                .setCreateTime(createtime);

                        seckillMapper.decrSeckillNumber(seckillId);
                        seckillMapper.insertSeckillSuccess(seckillSuccess);

                        //然后库存缓存-1
                        number--;
                        redisTemplate.opsForValue().set(SECKILL_NUMBER_KEY+seckillId,number,7, TimeUnit.DAYS);
                        limit++;
                        //设置用户参与过秒杀
                        redisTemplate.opsForValue().set(SECKILL_USER_KEY+userid+"_"+seckillId,limit,7,TimeUnit.DAYS);

                        return true;
                    }else {
                        return false;
                    }

                }

            }

        }catch (Exception e){

            throw new RuntimeException("秒杀失败");
        }

    }

    @Override
    public List<Seckill> selectAllSeckill() {
        return seckillMapper.selectAllSeckill();
    }
}
