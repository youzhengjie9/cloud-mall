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

    @Override
    public List<String> selectLoginUserBrowser() {
        log.error("selectLoginUserBrowser error");
        return null;
    }

    @Override
    public int selectLoginCountByBrowser(String browser) {
        log.error("selectLoginCountByBrowser error");
        return 0;
    }

    @Override
    public int selectLoginCountByTime(String time) {
        log.error("selectLoginCountByTime error");
        return 0;
    }
}
