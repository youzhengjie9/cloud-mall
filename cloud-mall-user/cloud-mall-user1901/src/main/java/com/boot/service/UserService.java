package com.boot.service;

import com.boot.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author 游政杰
 */
public interface UserService {


    //通过用户名查询密码
    String selectPasswordByuserName(String username);

    long selectUserIdByName(String username);

    //根据用户id查询余额
    BigDecimal selectUserMoneyByUserId(long userid);

}
