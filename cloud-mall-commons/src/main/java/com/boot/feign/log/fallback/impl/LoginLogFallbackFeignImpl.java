package com.boot.feign.log.fallback.impl;

import com.boot.feign.log.fallback.LoginLogFallbackFeign;
import com.boot.pojo.LoginLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LoginLogFallbackFeignImpl implements LoginLogFallbackFeign {
    @Override
    public List<LoginLog> selectLoginLogBylimit(int page, int limit) {
        log.error("selectLoginLogBylimit error");
        return null;
    }

    @Override
    public int selectLoginLogCount() {
        log.error("selectLoginLogCount error");
        return 0;
    }
}
