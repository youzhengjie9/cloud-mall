package com.boot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 游政杰
 */

@SpringBootApplication
@EnableAdminServer //说明这个SpringBoot应用是SpringBoot admin server主程序
public class MonitorApplication8921 {

  public static void main(String[] args) {
    SpringApplication.run(MonitorApplication8921.class,args);
  }
}
