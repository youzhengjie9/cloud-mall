package com.boot.dao;

import com.boot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Mapper
@Repository
public interface UserMapper {


    //通过用户名查询密码
    String selectPasswordByuserName(@Param("username")String username);

    long selectUserIdByName(@Param("username")String username);

    //根据用户id查询余额
    BigDecimal selectUserMoneyByUserId(@Param("userid") long userid);

    //减余额
    void decrMoneyByUserId(@Param("userid") long userid,@Param("money") BigDecimal money);

    void insertUser(User user);

    //查询用户数量
    int selectUserCount();

}
