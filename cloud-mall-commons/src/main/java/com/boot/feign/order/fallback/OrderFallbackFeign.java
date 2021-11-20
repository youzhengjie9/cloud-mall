package com.boot.feign.order.fallback;

import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.impl.OrderFallbackFeignImpl;
import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "cloud-mall-order",fallback = OrderFallbackFeignImpl.class)
@Component
public interface OrderFallbackFeign {

    // 查询指定用户的所有订单
    @ResponseBody
    @GetMapping(path = "/feign/order/selectAllOrdersByUserId/{userid}")
    public CommonResult<List<Order>> selectAllOrdersByUserId(@PathVariable("userid") long userid);

    //查询订单状态
    @ResponseBody
    @GetMapping(path = "/feign/order/selectOrderStatusById/{id}")
    public OrderStatus selectOrderStatusById(@PathVariable("id") long id);


}
