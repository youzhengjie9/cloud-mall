package com.boot.aop;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 游政杰
 * 限流注解@RateLimiter的aop切面
 */
@Slf4j
@Component
@Aspect //定义切面
public class RateLimiterAspect {

    private static final AtomicInteger count=new AtomicInteger(0); //统计限流次数

    //限流切面切入点
    @Pointcut("@annotation(com.boot.annotation.RateLimiter)")
    public void ratelimiterPointCut(){
    }

    @Around("ratelimiterPointCut()")
    public Object ratelimiter(ProceedingJoinPoint joinPoint)
    {
        Object proceed=null;
        try{
            //获取限流实例
            RateLimiter rateLimiter = this.getInstance();
            //获取类
            Class<?> aClass = joinPoint.getTarget().getClass();
            //获取目标方法名
            String methodname = joinPoint.getSignature().getName();
            //获取目标方法
            Method method = aClass.getMethod(methodname);
            //获取注解
            com.boot.annotation.RateLimiter annotation = method.getAnnotation(com.boot.annotation.RateLimiter.class);
            boolean valid = annotation.valid();

            if(valid) //进行限流
            {
                double v = annotation.permitsPerSecond();
                rateLimiter.setRate(v);
                if (rateLimiter.tryAcquire()) { //获取到令牌

                    proceed = joinPoint.proceed();

                }else {
                    log.warn("当前未获取到令牌，限流成功,当前限流次数共:"+RateLimiterAspect.count.incrementAndGet());
                }
            }else { //不进行限流
                proceed = joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.error("@RateLimiter 限流失败......");
        }
        return proceed;
    }


    /**
     * 静态内部类单例
     */
    private static class singletonRatelimiter{

        private static RateLimiter INSTANCE=RateLimiter.create(20.0);

    }

    //获取实例
    public RateLimiter getInstance()
    {
        return singletonRatelimiter.INSTANCE;
    }

}
