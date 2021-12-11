package com.boot.dao;

import com.boot.pojo.CouponsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CouponsRecordMapper {

    void insertCouponsRecord(CouponsRecord couponsRecord);

    int selectCouponsCountByUserIdAndCouponsId(@Param("userid") long userid,@Param("couponsid") long couponsid);


}
