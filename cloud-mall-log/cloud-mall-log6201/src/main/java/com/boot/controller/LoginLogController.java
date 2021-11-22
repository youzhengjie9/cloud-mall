package com.boot.controller;

import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.pojo.LoginLog;
import com.boot.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/feign/loginlog")
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    @ResponseBody
    @PostMapping(path = "/insertLoginLog")
    public CommonResult<LoginLog> insertLoginLog(@RequestBody LoginLog loginLog){
        CommonResult<LoginLog> commonResult = new CommonResult<>();
        try {
            loginLogService.insertLoginLog(loginLog);
            return commonResult;
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(ResultCode.FAILURE);
            return commonResult;
        }
    }


}
