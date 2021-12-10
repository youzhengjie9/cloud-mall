package com.boot.controller;

import com.boot.enums.ResultConstant;
import com.boot.pojo.CouponsActivity;
import com.boot.service.CouponsActivityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/feign/couponsActivity")
@Api("优惠券活动服务api")
public class CouponsActivityController {

    @Autowired
    private CouponsActivityService couponsActivityService;

    @ResponseBody
    @PostMapping(path = "/insertCouponsActivity")
    public String insertCouponsActivity(@RequestBody CouponsActivity couponsActivity){

        couponsActivityService.insertCouponsActivity(couponsActivity);
        return ResultConstant.SUCCESS.getCodeStat();
    }



}
