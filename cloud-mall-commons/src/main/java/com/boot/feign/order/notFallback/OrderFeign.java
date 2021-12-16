package com.boot.feign.order.notFallback;

import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.impl.OrderFallbackFeignImpl;
import com.boot.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;


@FeignClient(value = "cloud-mall-order")
@Component
public interface OrderFeign {


    @ResponseBody
    @PostMapping(path = "/feign/order/insertOrder")
    public CommonResult<Order> insertOrder(@RequestBody Order order);


    @ResponseBody
    @PostMapping(path = "/feign/order/orderBegin/{addressid}/{id}/{couponsid}")
    public CommonResult<Order> orderBegin(@PathVariable("addressid") String addressid,@PathVariable("id") long id,
                                          @PathVariable("couponsid") String couponsid) throws InterruptedException, ParseException;


    //修改订单状态
    @ResponseBody
    @GetMapping(path = "/feign/order/updateOrderStatus/{id}/{statusid}")
    public String updateOrderStatus(@PathVariable("id") long id,
                                    @PathVariable("statusid") long statusid);

    //同意退货
    @ResponseBody
    @GetMapping(path = "/feign/order/agreedReturnGoods/{userid}/{orderid}")
    public String agreedReturnGoods(@PathVariable("userid") long userid,
                                    @PathVariable("orderid") long orderid);

}
