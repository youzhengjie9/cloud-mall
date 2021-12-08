package com.boot.controller;

import com.boot.enums.ResultConstant;
import com.boot.pojo.UserDetail;
import com.boot.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/feign/userDetail")
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @ResponseBody
    @PostMapping(path = "/updateSex")
    public String updateSex(@RequestBody UserDetail userDetail){

        userDetailService.updateSex(userDetail.getUserid(), userDetail.getSex());

        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @PostMapping(path = "/updateSignature")
    public String updateSignature(@RequestBody UserDetail userDetail){

        userDetailService.updateSignature(userDetail.getUserid(),userDetail.getSignature());

        return ResultConstant.SUCCESS.getCodeStat();
    }

    //修改头像
    @ResponseBody
    @PostMapping(path = "/updateIcon")
    public String updateIcon(@RequestBody UserDetail userDetail){

        userDetailService.updateIcon(userDetail.getUserid(),userDetail.getIcon());

        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @GetMapping(path = "/selectUserDetail/{userid}")
    public UserDetail selectUserDetail(@PathVariable("userid") long userid){


        UserDetail userDetail = userDetailService.selectUserDetail(userid);
        return userDetail;
    }




}
