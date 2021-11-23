package com.boot.feign.user.fallback.impl;

import com.alibaba.fastjson.JSONObject;
import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class UserFallbackFeignImpl implements UserFallbackFeign {

    @Override
    public String selectPasswordByuserName(String username) {
        log.error("selectPasswordByuserName error");
        return null;
    }

    @Override
    public long selectUserIdByName(String username) {
        log.error("selectUserIdByName error");
        return 0;
    }

    @Override
    public BigDecimal selectUserMoneyByUserId(long userid) {
        log.error("selectUserMoneyByUserId error");
        return null;
    }

    @Override
    public CommonResult<User> registerUser(User user) {
        log.error("registerUser error");
        return null;
    }

    @Override
    public int selectUserCount() {
        log.error("selectUserCount error");
        return 0;
    }

}
