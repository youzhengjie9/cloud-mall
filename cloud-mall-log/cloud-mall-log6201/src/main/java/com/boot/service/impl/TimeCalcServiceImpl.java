package com.boot.service.impl;

import com.boot.dao.TimeCalcMapper;
import com.boot.pojo.TimeCalc;
import com.boot.service.TimeCalcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TimeCalcServiceImpl implements TimeCalcService {

    @Autowired
    private TimeCalcMapper timeCalcMapper;

    @Override
    public void insertTimeCalc(TimeCalc timeCalc) {
        timeCalcMapper.insertTimeCalc(timeCalc);
    }

    @Override
    public List<TimeCalc> selectTimeCalcBylimit(int page, int size) {
        return timeCalcMapper.selectTimeCalcBylimit(page, size);
    }

    @Override
    public int selectTimeCalcCount() {
        return timeCalcMapper.selectTimeCalcCount();
    }

    @Override
    public List<TimeCalc> selectTimeCalcByUriLimit(String uri, int page, int size) {
        return timeCalcMapper.selectTimeCalcByUriLimit(uri, page, size);
    }

    @Override
    public int selectTimeCalcCountBylimit(String uri) {
        return timeCalcMapper.selectTimeCalcCountBylimit(uri);
    }
}
