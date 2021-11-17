package com.boot.service;

import org.apache.ibatis.annotations.Param;

/**
 * @author 游政杰
 */
public interface UserService {


    //通过用户名查询密码
    String selectPasswordByuserName(String username);




}
