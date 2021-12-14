package com.boot.service;
import com.boot.pojo.CouponsRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponsRecordService {


    String insertCouponsRecord(CouponsRecord couponsRecord);

    int selectCouponsCountByUserIdAndCouponsId(long userid,long couponsid);


    List<CouponsRecord> selectCouponsRecordByUserIdAndLimit(int page, int size, long userid, int usetype);

}
