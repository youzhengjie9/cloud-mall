package com.boot.service;


import com.boot.pojo.RechargeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeRecordService {


    void insertRechargeRecord(RechargeRecord rechargeRecord);

    //查询用户充值记录
    List<RechargeRecord> selectUserRechargeRecord(int page,int size, long userid);


}
