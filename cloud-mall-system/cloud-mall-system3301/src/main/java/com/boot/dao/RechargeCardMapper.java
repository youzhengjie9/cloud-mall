package com.boot.dao;

import com.boot.pojo.RechargeCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RechargeCardMapper {

    //根据卡号和密码查询卡
    RechargeCard selectOneRechargeCard(@Param("cardNumber") String cardNumber,
                                       @Param("password") long password);

    //卡被使用
    void updateCardStatus(@Param("cardNumber") String cardNumber,
                          @Param("password") long password);






}
