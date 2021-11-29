package com.boot.service;

import com.boot.pojo.TimeCalc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimeCalcService {


    void insertTimeCalc(TimeCalc timeCalc);

    //分页查询
    List<TimeCalc> selectTimeCalcBylimit(int page,int size);

    //查询接口监控记录总数量
    int selectTimeCalcCount();

    //根据uri分页查询
    List<TimeCalc> selectTimeCalcByUriLimit(String uri,int page, int size);

    //根据uri查询数量
    int selectTimeCalcCountBylimit(String uri);

}
