package com.boot.feign.order.fallback;

import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.impl.OrderFallbackFeignImpl;
import com.boot.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "cloud-mall-order",fallback = OrderFallbackFeignImpl.class)
@Component
public interface OrderFallbackFeign {


    @ResponseBody
    @PostMapping(path = "/feign/order/insertOrder")
    public CommonResult<Order> insertOrder(@RequestBody Order order);





}
