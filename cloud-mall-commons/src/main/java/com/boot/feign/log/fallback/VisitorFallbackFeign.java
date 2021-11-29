package com.boot.feign.log.fallback;

import com.boot.feign.log.fallback.impl.VisitorFallbackFeignImpl;
import com.boot.pojo.Visitor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-log",fallback = VisitorFallbackFeignImpl.class)
public interface VisitorFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/visitor/selectVisitorBylimit/{page}/{size}")
    public List<Visitor> selectVisitorBylimit(@PathVariable("page") int page,
                                              @PathVariable("size") int size);

    @ResponseBody
    @GetMapping(path = "/feign/visitor/selectVisitorCount")
    public int selectVisitorCount();

}
