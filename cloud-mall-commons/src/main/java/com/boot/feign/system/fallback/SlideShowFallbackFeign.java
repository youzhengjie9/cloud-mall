package com.boot.feign.system.fallback;

import com.boot.feign.system.fallback.impl.SlideShowFallbackFeignImpl;
import com.boot.pojo.SlideShow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Component
@FeignClient(value = "cloud-mall-system",fallback = SlideShowFallbackFeignImpl.class)
public interface SlideShowFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/slideshow/selectSlideShow")
    public List<SlideShow> selectSlideShow();

    //查找所有轮播图，并排序和分页
    @ResponseBody
    @GetMapping(path = "/feign/slideshow/selectAllSlideShowByLimit/{page}/{limit}")
    public List<SlideShow> selectAllSlideShowByLimit(@PathVariable("page") int page,
                                                     @PathVariable("limit") int limit);

    //查询轮播图数量
    @ResponseBody
    @GetMapping(path = "/feign/slideshow/selectSlideShowCount")
    public int selectSlideShowCount();
}
