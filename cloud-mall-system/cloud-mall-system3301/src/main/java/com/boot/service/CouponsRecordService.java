package com.boot.service;
import com.boot.pojo.CouponsRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponsRecordService {


    String insertCouponsRecord(CouponsRecord couponsRecord);

    int selectCouponsCountByUserIdAndCouponsId(long userid,long couponsid);


    List<CouponsRecord> selectCouponsRecordByUserIdAndLimit(int page, int size, long userid, int usetype,String nowtime);

    CouponsRecord selectCouponsRecord(long couponsid, long userid, int usetype);

    void updateCouponsRecordUsetype(long couponsid, int usetype,String usetime);

}
