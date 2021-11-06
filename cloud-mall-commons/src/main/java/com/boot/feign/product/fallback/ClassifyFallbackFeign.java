package com.boot.feign.product.fallback;

import com.boot.feign.product.fallback.impl.ClassifyFallbackFeignImpl;
import com.boot.pojo.Classify;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-product",fallback = ClassifyFallbackFeignImpl.class)
public interface ClassifyFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/Classify/selectAllClassify")
    public List<Classify> selectAllClassify();



}
