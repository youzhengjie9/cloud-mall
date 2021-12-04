package com.boot.feign.system.notfallback;

import com.boot.feign.system.fallback.impl.SlideShowFallbackFeignImpl;
import com.boot.pojo.SlideShow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-system")
public interface SlideShowFeign {

    //修改排序
    @ResponseBody
    @GetMapping(path = "/feign/slideshow/updateSort/{id}/{sort}")
    public String updateSort(@PathVariable("id") long id,
                             @PathVariable("sort") int sort);

    @ResponseBody
    @GetMapping(path = "/feign/slideshow/deleteSlideShow/{id}")
    public String deleteSlideShow(@PathVariable("id") long id);


    @ResponseBody
    @GetMapping(path = "/feign/slideshow/batchDeleteSlideShow")
    public String batchDeleteSlideShow(@RequestParam("ids")long[] ids);

}
