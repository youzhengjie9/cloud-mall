package com.boot;

import com.boot.config.ScanClassProperties;
import com.boot.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Import({SwaggerConfig.class}) //导入配置类
@EnableSwagger2  //开启Swagger2
@EnableConfigurationProperties({ScanClassProperties.class})
@EnableFeignClients //开启openFeign客户端
@EnableDiscoveryClient
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*60*3) //开启springSession+redis解决分布式session问题
public class AdminApplication93 {

  public static void main(String[] args) {
      SpringApplication.run(AdminApplication93.class,args);
  }
}
