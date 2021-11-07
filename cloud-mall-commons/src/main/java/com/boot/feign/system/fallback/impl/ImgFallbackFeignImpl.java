package com.boot.feign.system.fallback.impl;

import com.boot.feign.system.fallback.ImgFallbackFeign;
import com.boot.pojo.RecommandImg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ImgFallbackFeignImpl implements ImgFallbackFeign {


    @Override
    public List<RecommandImg> selectWntj() {
        log.error("wntj error");
        return null;
    }

    @Override
    public List<RecommandImg> selectMxdp() {
        log.error("mxdp error");
        return null;
    }
}
