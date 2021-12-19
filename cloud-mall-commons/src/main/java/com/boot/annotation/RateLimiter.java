package com.boot.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 游政杰
 *
 *  暂不希望使用，因为这个限流注解不完善！
 *
 * 加了这个注解就会被限流，底层封装了google的ratelimiter令牌桶算法实现类，通过spring aop去动态代理含有
 * 此注解的method
 *
 * ***使用要求：
 * 此注解必须放在可以跳转页面的restful方法上，不能有@RestController或者@ResponseBody。。。。。。。。
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
//    @AliasFor("value")
    double permitsPerSecond() default 20.0; //每秒会往令牌桶放多少个令牌，默认令牌速率为20个/s

    boolean valid() default true; //是否生效，默认是生效
}
