package com.boot.controller;

import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.pojo.LoginLog;
import com.boot.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ResponseBody
    @GetMapping(path = "/selectLoginLogBylimit/{page}/{limit}")
    public List<LoginLog> selectLoginLogBylimit(@PathVariable("page") int page,
                                                @PathVariable("limit") int limit){

        return loginLogService.selectLoginLogBylimit(page, limit);
    }

    @ResponseBody
    @GetMapping(path = "/selectLoginLogCount")
    public int selectLoginLogCount(){

        int count = loginLogService.selectLoginLogCount();
        return count;
    }

    //查询登录者使用的浏览器(默认展示2个)
    @ResponseBody
    @GetMapping(path = "/selectLoginUserBrowser")
    public List<String> selectLoginUserBrowser(){

        return loginLogService.selectLoginUserBrowser();
    }

    @ResponseBody
    @GetMapping(path = "/selectLoginCountByBrowser/{browser}")
    public int selectLoginCountByBrowser(@PathVariable("browser") String browser){

        return loginLogService.selectLoginCountByBrowser(browser);
    }

    @ResponseBody
    @GetMapping(path = "/selectLoginCountByTime/{time}")
    public int selectLoginCountByTime(@PathVariable("time") String time){

        return loginLogService.selectLoginCountByTime(time);
    }

}
