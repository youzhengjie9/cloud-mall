package com.boot.feign.user.fallback.impl;

import com.boot.feign.user.fallback.UserDetailFallbackFeign;
import com.boot.pojo.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailFallbackFeignImpl implements UserDetailFallbackFeign {


    @Override
    public UserDetail selectUserDetail(long userid) {
        log.error("selectUserDetail error");
        return null;
    }
}
