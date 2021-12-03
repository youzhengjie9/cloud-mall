package com.boot.feign.user.fallback.impl;

import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.AuthorityFallbackFeign;
import com.boot.pojo.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AuthorityFallbackFeignImpl implements AuthorityFallbackFeign {


    @Override
    public CommonResult<String> selectAuthorityNameById(int id) {
        log.error("selectAuthorityNameById error");
        return null;
    }

    @Override
    public List<Authority> selectAuthorityExcludeCurAuthority(int id) {
        log.error("selectAuthorityExcludeCurAuthority error");
        return null;
    }
}
