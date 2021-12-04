package com.boot.dao;

import com.boot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

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

    //加余额
    void incrMoneyByUserId(@Param("userid") long userid,@Param("money") BigDecimal money);


    //查询用户所有信息（包括权限和详情等，除了密码）
    List<User> selectAllUserInfo(@Param("page") int page,@Param("limit") int limit);

    User selectUserInfoById(@Param("userid") long userid);

    //修改是否生效
    void updateValid(@Param("userid") long userid,@Param("valid") int valid);

    //修改用户名
    void updateUserName(@Param("id") long id,@Param("username") String username);

    void deleteUserById(@Param("id") long id);

}
