package com.boot.controller;

import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.enums.ResultConstant;
import com.boot.pojo.UserAuthority;
import com.boot.service.UserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    //修改用户权限
    @ResponseBody
    @PostMapping(path = "/updateUserAuthority")
    public String updateUserAuthority(@RequestBody UserAuthority userAuthority){

        userAuthorityService.updateUserAuthority(userAuthority);

        return ResultConstant.SUCCESS.getCodeStat();
    }



}
