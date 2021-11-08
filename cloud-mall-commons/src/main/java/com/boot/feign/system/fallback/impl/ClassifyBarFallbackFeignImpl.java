package com.boot.feign.system.fallback.impl;

import com.boot.feign.system.fallback.ClassifyBarFallbackFeign;
import com.boot.pojo.ClassifyBar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ClassifyBarFallbackFeignImpl implements ClassifyBarFallbackFeign {


    @Override
    public List<ClassifyBar> selectAllClassifyBar() {
        log.error("selectAllClassifyBar error");
        return null;
    }
}
