package com.boot.feign.order.notFallback;

import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.impl.OrderFallbackFeignImpl;
import com.boot.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@FeignClient(value = "cloud-mall-order")
@Component
public interface OrderFeign {


    @ResponseBody
    @PostMapping(path = "/feign/order/insertOrder")
    public CommonResult<Order> insertOrder(@RequestBody Order order);


    @ResponseBody
    @PostMapping(path = "/feign/order/orderBegin/{addressid}/{id}")
    public CommonResult<Order> orderBegin(@PathVariable("addressid") String addressid,@PathVariable("id") long id);


}
