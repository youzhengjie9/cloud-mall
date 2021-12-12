package com.boot;

import com.boot.config.ScanClassProperties;
import com.boot.config.SwaggerConfig;
import com.boot.pojo.CouponsActivity;
import com.boot.service.CouponsActivityService;
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

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import({SwaggerConfig.class}) //导入swaggerConfig的配置类
@EnableSwagger2  //开启Swagger2
@EnableConfigurationProperties(ScanClassProperties.class)
@EnableFeignClients
@EnableDiscoveryClient
public class SystemApplication3301 {

    private static final String COUPONS_ACTIVITY_KEY="coupons_activity_key_";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CouponsActivityService couponsActivityService;


    private String link(String ...strs)
    {
        if(strs.length==0){
            return null;
        }else if(strs.length==1){
            return strs[0];
        }else {
            String res="";
            String cut=",";
            res+=strs[0];
            for (int i = 1; i < strs.length; i++) {
                res+=cut+strs[i];
            }
            return res;
        }
    }

    /** 初始化优惠券数据到Redis*/
    @PostConstruct  //Spring Bean的生命周期，预加载
    public synchronized void initCouponsActivity()
    {

        couponsActivityService.selectAllCouponsActivityLimitAndPoint().forEach(
                (e)->{

                    String linkres = link(String.valueOf(e.getLimitCount()),e.getMinPoint().toString());

                    redisTemplate.opsForValue().set(COUPONS_ACTIVITY_KEY+e.getId(),linkres);

                });
    }



  public static void main(String[] args) {
      SpringApplication.run(SystemApplication3301.class,args);
  }
}
