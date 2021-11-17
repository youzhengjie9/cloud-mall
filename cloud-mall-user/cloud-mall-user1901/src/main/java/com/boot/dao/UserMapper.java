package com.boot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {


    //通过用户名查询密码
    String selectPasswordByuserName(@Param("username")String username);




}
