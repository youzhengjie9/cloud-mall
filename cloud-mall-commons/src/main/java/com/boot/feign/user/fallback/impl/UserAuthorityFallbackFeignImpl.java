package com.boot.feign.user.fallback.impl;

import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.UserAuthorityFallbackFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserAuthorityFallbackFeignImpl implements UserAuthorityFallbackFeign {


    @Override
    public CommonResult<Integer> selectAuthorityIdByUserId(long userid) {
        log.error("selectAuthorityIdByUserId error");
        return null;
    }
}
