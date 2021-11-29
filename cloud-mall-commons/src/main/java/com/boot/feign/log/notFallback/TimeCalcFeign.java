package com.boot.feign.log.notFallback;

import com.boot.data.CommonResult;
import com.boot.pojo.LoginLog;
import com.boot.pojo.TimeCalc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-log")
public interface TimeCalcFeign {


    @ResponseBody
    @PostMapping(path = "/feign/timecalc/insertTimeCalc")
    public String insertTimeCalc(@RequestBody TimeCalc timeCalc);

}
