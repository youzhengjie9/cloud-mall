package com.boot.config;

import com.boot.interceptors.OperationInterceptor;
import com.boot.interceptors.TimeCalcInterceptor;
import com.boot.interceptors.VisitorInterceptor;
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
    @Bean
    public TimeCalcInterceptor timeCalcInterceptor(){

        return new TimeCalcInterceptor();
    }

    @Bean
    public VisitorInterceptor visitorInterceptor()
    {

        return new VisitorInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(operationInterceptor())
                //添加拦截路径
                .addPathPatterns("/pear/**")
                //排除静态资源和提供数据的接口
                .excludePathPatterns("/static/**","/pear/userInfoData");

        registry.addInterceptor(timeCalcInterceptor())
                //拦截所有接口
                .addPathPatterns("/**")
                //只需排除一下静态资源即可
                .excludePathPatterns("/user/**","/assets/**","/user_img/**"
                        ,"/back/**","/component/**","/config/**"
                        ,"/email/**","/pear-admin/**","/plugins/**"
                        ,"/favicon.ico","/error"
                        ,"/feign/timecalc/insertTimeCalc");

        registry.addInterceptor(visitorInterceptor())
                .addPathPatterns("/admin/**","/");

    }
}
