package com.boot.service;

import com.boot.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 游政杰
 */
public interface UserService {


    //通过用户名查询密码
    String selectPasswordByuserName(String username);

    long selectUserIdByName(String username);

    //根据用户id查询余额
    BigDecimal selectUserMoneyByUserId(long userid);

    //减余额
    void decrMoneyByUserId(long userid,BigDecimal money);

    //注册帐号
    void register(User user);

    //查询用户数量
    int selectUserCount();

    //加余额
    void incrMoneyByUserId(long userid,BigDecimal money);

    //查询用户所有信息（包括权限和详情等，除了密码）
    List<User> selectAllUserInfo(int page,int limit);

    User selectUserInfoById(long userid);

    //修改是否生效
    void updateValid(long userid,int valid);

    //修改用户名
    void updateUserName(long id,String username);

    //修改用户名和权限
    void modifyUserNameAndAuthority(String id,String userName,String authorityId);

    void deleteUserById(long id);


}
