package com.boot.feign.system.fallback.impl;

import com.boot.feign.system.fallback.SlideShowFallbackFeign;
import com.boot.pojo.SlideShow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SlideShowFallbackFeignImpl implements SlideShowFallbackFeign {

    @Override
    public List<SlideShow> selectSlideShow() {
        log.error("selectSlideShow error");
        return null;
    }

    @Override
    public List<SlideShow> selectAllSlideShowByLimit(int page, int limit) {
        log.error("selectAllSlideShowByLimit error");
        return null;
    }

    @Override
    public int selectSlideShowCount() {
        log.error("selectSlideShowCount error");
        return 0;
    }
}
