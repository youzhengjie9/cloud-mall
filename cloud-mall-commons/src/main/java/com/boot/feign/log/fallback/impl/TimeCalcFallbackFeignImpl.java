package com.boot.feign.log.fallback.impl;

import com.alibaba.fastjson.JSONObject;
import com.boot.feign.log.fallback.TimeCalcFallbackFeign;
import com.boot.pojo.TimeCalc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TimeCalcFallbackFeignImpl implements TimeCalcFallbackFeign {


    @Override
    public List<TimeCalc> selectTimeCalcBylimit(int page, int size) {
        log.error("selectTimeCalcBylimit error");
        return null;
    }

    @Override
    public int selectTimeCalcCount() {
        log.error("selectTimeCalcCount error");
        return 0;
    }

    @Override
    public List<TimeCalc> selectTimeCalcByUriLimit(JSONObject jsonObject) {
        log.error("selectTimeCalcByUriLimit error");
        return null;
    }

    @Override
    public int selectTimeCalcCountBylimit(JSONObject jsonObject) {
        log.error("selectTimeCalcCountBylimit error");
        return 0;
    }
}
