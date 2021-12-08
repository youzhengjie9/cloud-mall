package com.boot.service.impl;

import com.boot.dao.UserAuthorityMapper;
import com.boot.dao.UserDetailMapper;
import com.boot.dao.UserMapper;
import com.boot.pojo.User;
import com.boot.pojo.UserAuthority;
import com.boot.pojo.UserDetail;
import com.boot.service.UserService;
import com.boot.utils.SnowId;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/** @author 游政杰 */
@Transactional // 本地事务
@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired private UserMapper userMapper;

  @Autowired private UserAuthorityMapper userAuthorityMapper;

  @Autowired
  private UserDetailMapper userDetailMapper;

  @Override
  public String selectPasswordByuserName(String username) {
    return userMapper.selectPasswordByuserName(username);
  }

  @Override
  public long selectUserIdByName(String username) {
    return userMapper.selectUserIdByName(username);
  }

  @Override
  public BigDecimal selectUserMoneyByUserId(long userid) {
    return userMapper.selectUserMoneyByUserId(userid);
  }

  @Override
  public void decrMoneyByUserId(long userid, BigDecimal money) {
    userMapper.decrMoneyByUserId(userid, money);
  }

  //    @GlobalTransactional(name = "seata_register",rollbackFor = Exception.class)
  @Override
  public void register(User user) {

    try {
      java.util.Date date1 = new java.util.Date();
      Date date = new Date(date1.getTime());
      long userid = SnowId.nextId(); // 分布式id
      user.setId(userid);
      user.setValid(1);
      user.setDate(date);
      user.setMoney(new BigDecimal("16200")); // 默认给用户16200块钱
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      String encodePassword = bCryptPasswordEncoder.encode(user.getPassword()); // 加密后的密码
      user.setPassword(encodePassword);

      userMapper.insertUser(user); // 添加用户

      // 添加用户权限
      UserAuthority userAuthority = new UserAuthority();

      userAuthority.setId(SnowId.nextId());
      userAuthority.setUser_id(userid);
      userAuthority.setAuthority_id(2); // 普通用户

      userAuthorityMapper.insertUserAuthority(userAuthority);


      //添加用户详情
      UserDetail userDetail = new UserDetail();
      userDetail.setId(SnowId.nextId());
      userDetail.setSignature("默认的个性签名");
      userDetail.setIcon("/static/img/user-icon/default-icon.jpg");
      userDetail.setUserid(userid);
      userDetailMapper.insertUserDetail(userDetail);

    } catch (Exception e) {
      throw new RuntimeException("注册用户失败");
    }
  }

  @Override
  public int selectUserCount() {
    return userMapper.selectUserCount();
  }

  @Override
  public void incrMoneyByUserId(long userid, BigDecimal money) {
    userMapper.incrMoneyByUserId(userid, money);
  }

  @Override
  public List<User> selectAllUserInfo(int page, int limit) {
    return userMapper.selectAllUserInfo(page, limit);
  }

  @Override
  public User selectUserInfoById(long userid) {
    return userMapper.selectUserInfoById(userid);
  }

  @Override
  public void updateValid(long userid, int valid) {
    userMapper.updateValid(userid, valid);
  }

  @Override
  public void updateUserName(long id, String username) {
    userMapper.updateUserName(id, username);
  }

  @Override
  public void modifyUserNameAndAuthority(String id, String userName, String authorityId) {

    //修改权限
    UserAuthority userAuthority = new UserAuthority();
    userAuthority.setUser_id(Long.parseLong(id));
    userAuthority.setAuthority_id(Integer.parseInt(authorityId));
    userAuthorityMapper.updateUserAuthority(userAuthority);

    //修改用户名
    userMapper.updateUserName(Long.parseLong(id),userName);

  }

  @Override
  public void deleteUserById(long id) {
    userMapper.deleteUserById(id);
  }

  @Override
  public void updatePassword(long id, String password) {
    userMapper.updatePassword(id, password);
  }

  @Override
  public String selectPassword(long id) {
    return userMapper.selectPassword(id);
  }


}



