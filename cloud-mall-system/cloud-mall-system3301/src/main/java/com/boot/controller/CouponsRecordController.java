package com.boot.controller;

import com.boot.enums.ResultConstant;
import com.boot.pojo.CouponsActivity;
import com.boot.pojo.CouponsRecord;
import com.boot.service.CouponsActivityService;
import com.boot.service.CouponsRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/couponsRecord")
@Api("优惠券记录服务api")
public class CouponsRecordController {

    @Autowired
    private CouponsRecordService couponsRecordService;

    @ResponseBody
    @PostMapping(path = "/insertCouponsRecord")
    public String insertCouponsRecord(@RequestBody CouponsRecord couponsRecord){

        String res = couponsRecordService.insertCouponsRecord(couponsRecord);

        return res;
    }


}
