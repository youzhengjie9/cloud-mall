package com.boot.feign.system.fallback;

import com.boot.feign.system.fallback.impl.ClassifyBarFallbackFeignImpl;
import com.boot.pojo.ClassifyBar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-system",fallback = ClassifyBarFallbackFeignImpl.class)
public interface ClassifyBarFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/classifybar/selectAllClassifyBar")
    public List<ClassifyBar> selectAllClassifyBar();


}
