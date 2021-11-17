package com.boot.feign.user.fallback.impl;

import com.boot.feign.user.fallback.UserFallbackFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFallbackFeignImpl implements UserFallbackFeign {

    @Override
    public String selectPasswordByuserName(String username) {
        log.error("selectPasswordByuserName error");
        return null;
    }
}
