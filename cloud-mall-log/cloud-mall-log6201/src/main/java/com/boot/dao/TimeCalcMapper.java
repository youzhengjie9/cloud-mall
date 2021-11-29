package com.boot.dao;

import com.boot.pojo.TimeCalc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TimeCalcMapper {


    void insertTimeCalc(TimeCalc timeCalc);

    //分页查询
    List<TimeCalc> selectTimeCalcBylimit(@Param("page") int page, @Param("size") int size);

    //查询接口监控记录总数量
    int selectTimeCalcCount();

    //根据uri分页查询
    List<TimeCalc> selectTimeCalcByUriLimit(@Param("uri") String uri,@Param("page") int page,
                                            @Param("size") int size);

    //根据uri查询数量
    int selectTimeCalcCountBylimit(@Param("uri") String uri);


}
