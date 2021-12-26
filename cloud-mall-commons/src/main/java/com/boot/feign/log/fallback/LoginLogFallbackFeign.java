package com.boot.feign.log.fallback;

import com.boot.data.CommonResult;
import com.boot.feign.log.fallback.impl.LoginLogFallbackFeignImpl;
import com.boot.pojo.LoginLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-log",fallback = LoginLogFallbackFeignImpl.class)
public interface LoginLogFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/loginlog/selectLoginLogBylimit/{page}/{limit}")
    public List<LoginLog> selectLoginLogBylimit(@PathVariable("page") int page,
                                                @PathVariable("limit") int limit);

    @ResponseBody
    @GetMapping(path = "/feign/loginlog/selectLoginLogCount")
    public int selectLoginLogCount();

    //查询登录者使用的浏览器(默认展示2个)
    @ResponseBody
    @GetMapping(path = "/feign/loginlog/selectLoginUserBrowser")
    public List<String> selectLoginUserBrowser();

    @ResponseBody
    @GetMapping(path = "/feign/loginlog/selectLoginCountByBrowser/{browser}")
    public int selectLoginCountByBrowser(@PathVariable("browser") String browser);

    @ResponseBody
    @GetMapping(path = "/feign/loginlog/selectLoginCountByTime/{time}")
    public int selectLoginCountByTime(@PathVariable("time") String time);


}
