package com.boot.annotation;

import java.lang.annotation.*;

/**
 * @author 游政杰
 * 加了这个注解就会被限流，底层封装了google的ratelimiter令牌桶算法实现类，通过spring aop去动态代理含有
 * 此注解的method
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    double permitsPerSecond() default 20.0; //每秒会往令牌桶放多少个令牌，默认令牌速率为20个/s

    boolean valid() default true; //是否生效，默认是生效
}
