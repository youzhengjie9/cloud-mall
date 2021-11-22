package com.boot.controller;

import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/feign/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @ResponseBody
    @GetMapping(path = "/selectAuthorityNameById/{id}")
    public CommonResult<String> selectAuthorityNameById(@PathVariable("id") int id){
        CommonResult<String> commonResult = new CommonResult<>();

        try {
            String authorityName = authorityService.selectAuthorityNameById(id);
            commonResult.setObj(authorityName);
            return commonResult;
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(ResultCode.FAILURE);
            return commonResult;
        }

    }




}
