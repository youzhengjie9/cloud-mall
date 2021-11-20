package com.boot.feign.order.fallback.impl;

import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.OrderFallbackFeign;
import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OrderFallbackFeignImpl implements OrderFallbackFeign {

    @Override
    public CommonResult<List<Order>> selectAllOrdersByUserId(long userid) {
        log.error("selectAllOrdersByUserId error");
        return null;
    }

    @Override
    public OrderStatus selectOrderStatusById(long id) {
        log.error("selectOrderStatusById error");
        return null;
    }

}
