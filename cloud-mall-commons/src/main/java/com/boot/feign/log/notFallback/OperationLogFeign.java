package com.boot.feign.log.notFallback;

import com.boot.pojo.OperationLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-log")
public interface OperationLogFeign {


    @ResponseBody
    @PostMapping(path = "/feign/operationlog/insertOperationLog")
    public String insertOperationLog(@RequestBody OperationLog operationLog);




}
