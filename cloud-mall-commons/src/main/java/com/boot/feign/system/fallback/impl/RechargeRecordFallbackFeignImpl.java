package com.boot.feign.system.fallback.impl;

import com.boot.feign.system.fallback.RechargeRecordFallbackFeign;
import com.boot.pojo.RechargeRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RechargeRecordFallbackFeignImpl implements RechargeRecordFallbackFeign {


    @Override
    public List<RechargeRecord> selectUserRechargeRecord(int page, int size, long userid) {
        log.error("selectUserRechargeRecord error");
        return null;
    }
}
