package com.boot.dao;

import com.boot.pojo.CouponsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CouponsRecordMapper {

    void insertCouponsRecord(CouponsRecord couponsRecord);

    int selectCouponsCountByUserIdAndCouponsId(@Param("userid") long userid,@Param("couponsid") long couponsid);

    List<CouponsRecord> selectCouponsRecordByUserIdAndLimit(@Param("page") int page,
                                                            @Param("size") int size,
                                                            @Param("userid") long userid,
                                                            @Param("usetype") int usetype);




}
