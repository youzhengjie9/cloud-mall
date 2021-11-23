package com.boot.feign.user.fallback.impl;

import com.boot.feign.user.fallback.MenuFallbackFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MenuFallbackFeignImpl implements MenuFallbackFeign {
    @Override
    public String selectMenuDataByAuthority(int authority) {
        log.error("selectMenuDataByAuthority error");
        return null;
    }
}
