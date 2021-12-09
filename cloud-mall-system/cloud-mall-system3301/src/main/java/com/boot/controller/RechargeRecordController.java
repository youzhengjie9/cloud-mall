package com.boot.controller;

import com.boot.pojo.RechargeRecord;
import com.boot.service.RechargeRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/rechargeRecord")
@Api("充值记录服务api")
public class RechargeRecordController {

    @Autowired
    private RechargeRecordService rechargeRecordService;

    //查询用户充值记录
    @ResponseBody
    @GetMapping(path = "/selectUserRechargeRecord/{page}/{size}/{userid}")
    public List<RechargeRecord> selectUserRechargeRecord(@PathVariable("page") int page,
                                                         @PathVariable("size") int size,
                                                         @PathVariable("userid") long userid){

        return rechargeRecordService.selectUserRechargeRecord(page, size, userid);
    }



}
