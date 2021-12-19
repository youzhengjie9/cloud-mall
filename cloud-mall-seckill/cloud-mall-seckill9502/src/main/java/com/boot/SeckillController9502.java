package com.boot;
import com.boot.config.ScanClassProperties;
import com.boot.config.SwaggerConfig;
import com.boot.pojo.Seckill;
import com.boot.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀模块
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import({SwaggerConfig.class}) //导入swaggerConfig的配置类
@EnableSwagger2  //开启Swagger2
@EnableConfigurationProperties(ScanClassProperties.class)
@EnableFeignClients
@EnableDiscoveryClient
public class SeckillController9502 {

    @Autowired
    private RedisTemplate redisTemplate;

    private final String SECKILL_NUMBER_KEY="seckill_number_"; //秒杀库存key

    private final String SECKILL_LIMIT_KEY="seckill_limit_";//秒杀限购key

    private final String SECKILL_TIME_KEY="seckill_time_";//秒杀开始时间和结束时间key

    @Autowired
    private SeckillService seckillService;

    /**
     * 初始化秒杀
     */
    @PostConstruct
    public void initSeckill()
    {
        List<Seckill> seckills = seckillService.selectAllSeckill();

       seckills.forEach((e)->{
           //key =SECKILL_NUMBER_KEY加上秒杀商品id，value是库存
           redisTemplate.opsForValue().set(SECKILL_NUMBER_KEY+e.getSeckillId(),e.getSeckillNumber(),7, TimeUnit.DAYS);

           //key =SECKILL_LIMIT_KEY加上秒杀商品id，value是限购数量
           redisTemplate.opsForValue().set(SECKILL_LIMIT_KEY+e.getSeckillId(),e.getLimitCount(),7,TimeUnit.DAYS);

           //key =SECKILL_TIME_KEY加上秒杀商品id，value是（开始时间?结束时间）
           String startTime = e.getStartTime();
           String endTime = e.getEndTime();
           String value=startTime+","+endTime;
           redisTemplate.opsForValue().set(SECKILL_TIME_KEY+e.getSeckillId(),value,7,TimeUnit.DAYS);


       });

    }


  public static void main(String[] args) {
      SpringApplication.run(SeckillController9502.class,args);

  }

}
