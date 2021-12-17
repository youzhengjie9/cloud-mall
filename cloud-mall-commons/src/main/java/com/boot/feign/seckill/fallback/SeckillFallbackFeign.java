package com.boot.feign.seckill.fallback;

import com.boot.feign.seckill.fallback.impl.SeckillFallbackFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "cloud-mall-seckill",fallback = SeckillFallbackFeignImpl.class)
public interface SeckillFallbackFeign {






}
