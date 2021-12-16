package com.boot.feign.system.fallback.impl;

import com.boot.feign.system.fallback.CouponsRecordFallbackFeign;
import com.boot.pojo.CouponsRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CouponsRecordFallbackFeignImpl implements CouponsRecordFallbackFeign {


    @Override
    public List<CouponsRecord> selectCouponsRecordByUserIdAndLimit(int page, int size, long userid, int usetype,String nowtime) {
        log.error("selectCouponsRecordByUserIdAndLimit error");
        return null;
    }

    @Override
    public CouponsRecord selectCouponsRecord(long couponsid, long userid, int usetype) {
        log.error("selectCouponsRecord error");
        return null;
    }
}
