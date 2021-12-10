package com.boot.controller.pearAdmin;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("优惠券后台控制器")
public class CouponsController {

    @RequestMapping(path = "/toCoupons")
    public String toCoupons()
    {

        return "back/coupons_list";
    }




}
