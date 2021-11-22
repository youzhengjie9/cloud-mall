package com.boot.feign.user.fallback.impl;

import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.AuthorityFallbackFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorityFallbackFeignImpl implements AuthorityFallbackFeign {


    @Override
    public CommonResult<String> selectAuthorityNameById(int id) {
        log.error("selectAuthorityNameById error");
        return null;
    }
}
