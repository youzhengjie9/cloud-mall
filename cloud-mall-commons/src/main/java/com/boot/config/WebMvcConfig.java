package com.boot.config;

import com.boot.interceptors.OperationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 游政杰
 */
//配置拦截器
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public OperationInterceptor operationInterceptor(){

        return new OperationInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(operationInterceptor())
                //添加拦截路径
                .addPathPatterns("/pear/**")
                //排除静态资源和提供数据的接口
                .excludePathPatterns("/static/**","/pear/userInfoData");


    }
}
