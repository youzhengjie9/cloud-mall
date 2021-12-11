package com.boot.feign.system.fallback.impl;

import com.boot.feign.system.fallback.CouponsActivityFallbackFeign;
import com.boot.pojo.CouponsActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CouponsActivityFallbackFeignImpl implements CouponsActivityFallbackFeign {


    @Override
    public List<CouponsActivity> selectAllCouponsActivityByLimit(int page, int size) {
        log.error("selectAllCouponsActivityByLimit error");
        return null;
    }

    @Override
    public int selectCouponsActivityCount() {
        log.error("selectCouponsActivityCount error");
        return 0;
    }

    @Override
    public CouponsActivity selectCouponsActivityById(long id) {
        log.error("selectCouponsActivityById error");
        return null;
    }

    @Override
    public List<CouponsActivity> selectAllCouponsActivityByLimitAndValid(int page, int size) {
        log.error("selectAllCouponsActivityByLimitAndValid error");
        return null;
    }

    @Override
    public int selectCouponsActivityCountByValid() {
        log.error("selectCouponsActivityCountByValid error");
        return 0;
    }
}
