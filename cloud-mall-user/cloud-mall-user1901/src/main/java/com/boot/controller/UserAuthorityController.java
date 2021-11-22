package com.boot.controller;

import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.service.UserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/feign/userAuthority")
public class UserAuthorityController {

    @Autowired
    private UserAuthorityService userAuthorityService;

    @ResponseBody
    @GetMapping(path = "/selectAuthorityIdByUserId/{userid}")
    public CommonResult<Integer> selectAuthorityIdByUserId(@PathVariable("userid") long userid){

        CommonResult<Integer> commonResult = new CommonResult<>();
        try {
            int authorityId = userAuthorityService.selectAuthorityIdByUserId(userid);
            commonResult.setObj(authorityId);
            return commonResult;
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(ResultCode.FAILURE);
            return commonResult;
        }

    }


}
