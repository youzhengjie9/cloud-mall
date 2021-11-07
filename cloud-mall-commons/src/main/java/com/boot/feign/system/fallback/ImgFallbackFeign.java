package com.boot.feign.system.fallback;


import com.boot.feign.system.fallback.impl.ImgFallbackFeignImpl;
import com.boot.pojo.RecommandImg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-system",fallback = ImgFallbackFeignImpl.class)
public interface ImgFallbackFeign {



    @ResponseBody
    @GetMapping(path = "/feign/img/selectWntj")
    public List<RecommandImg> selectWntj();

    @ResponseBody
    @GetMapping(path = "/feign/img/selectMxdp")
    public List<RecommandImg> selectMxdp();

}



