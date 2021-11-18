package com.boot.feign.order.fallback.impl;

import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.OrderFallbackFeign;
import com.boot.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderFallbackFeignImpl implements OrderFallbackFeign {


    @Override
    public CommonResult<Order> insertOrder(Order order) {
        log.error("insertOrder error");
        return null;
    }
}
