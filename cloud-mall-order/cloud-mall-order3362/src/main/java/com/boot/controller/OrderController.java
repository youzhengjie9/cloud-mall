package com.boot.controller;

import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.pojo.Order;
import com.boot.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/feign/order")
@Api("订单服务api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @PostMapping(path = "/insertOrder")
    public CommonResult<Order> insertOrder(@RequestBody Order order){

        CommonResult<Order> commonResult = new CommonResult<>();
        try {
            orderService.insertOrder(order);
            return commonResult;
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(ResultCode.FAILURE);
            return commonResult;
        }
    }




}
