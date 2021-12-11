package com.boot.service;
import com.boot.pojo.CouponsRecord;
import org.apache.ibatis.annotations.Param;

public interface CouponsRecordService {


    String insertCouponsRecord(CouponsRecord couponsRecord);

    int selectCouponsCountByUserIdAndCouponsId(@Param("userid") long userid, @Param("couponsid") long couponsid);


}
