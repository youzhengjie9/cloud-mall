package com.boot.dao;

import com.boot.pojo.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RechargeRecordMapper {


    void insertRechargeRecord(RechargeRecord rechargeRecord);



}
