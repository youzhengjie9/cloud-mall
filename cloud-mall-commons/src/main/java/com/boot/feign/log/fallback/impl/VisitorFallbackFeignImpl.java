package com.boot.feign.log.fallback.impl;

import com.boot.feign.log.fallback.VisitorFallbackFeign;
import com.boot.pojo.Visitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class VisitorFallbackFeignImpl implements VisitorFallbackFeign {
    @Override
    public List<Visitor> selectVisitorBylimit(int page, int size) {
        log.error("selectVisitorBylimit error");
        return null;
    }

    @Override
    public int selectVisitorCount() {
        log.error("selectVisitorCount error");
        return 0;
    }
}
