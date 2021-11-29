package com.boot.feign.log.notFallback;

import com.boot.pojo.TimeCalc;
import com.boot.pojo.Visitor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-log")
public interface VisitorFeign {


    @ResponseBody
    @PostMapping(path = "/feign/visitor/insertVisitor")
    public String insertVisitor(@RequestBody Visitor visitor);

}
