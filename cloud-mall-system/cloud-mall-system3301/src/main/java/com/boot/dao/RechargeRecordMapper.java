package com.boot.dao;

import com.boot.pojo.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RechargeRecordMapper {


    void insertRechargeRecord(RechargeRecord rechargeRecord);

    //查询用户充值记录
    List<RechargeRecord> selectUserRechargeRecord(@Param("page") int page,@Param("size") int size,
                                                  @Param("userid") long userid);





}
