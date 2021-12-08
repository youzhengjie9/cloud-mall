package com.boot.service;

import com.boot.data.layuiJSON;
import com.boot.pojo.RechargeCard;
import org.apache.ibatis.annotations.Param;

public interface RechargeCardService {


    //根据卡号和密码查询卡
    RechargeCard selectOneRechargeCard(String cardNumber, long password);

    //卡被使用
    void updateCardStatus(String cardNumber, long password);


    //充值的核心方法
    String recharge(String cardNumber, long password, long userid);


}
