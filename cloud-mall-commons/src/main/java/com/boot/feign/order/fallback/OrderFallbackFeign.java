package com.boot.feign.order.fallback;

import com.boot.feign.order.fallback.impl.OrderFallbackFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "cloud-mall-order",fallback = OrderFallbackFeignImpl.class)
@Component
public interface OrderFallbackFeign {








}
