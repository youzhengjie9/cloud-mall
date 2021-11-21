package com.boot.service.impl;

import com.boot.dao.UserAuthorityMapper;
import com.boot.dao.UserMapper;
import com.boot.pojo.User;
import com.boot.pojo.UserAuthority;
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

/**
 * @author 游政杰
 */
@Transactional //本地事务
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

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

//        try{
            java.util.Date date1 = new java.util.Date();
            Date date = new Date(date1.getTime());
            long userid = SnowId.nextId(); //分布式id
            user.setId(userid);
            user.setValid(1);
            user.setDate(date);
            user.setMoney(new BigDecimal("16200"));//默认给用户16200块钱
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodePassword = bCryptPasswordEncoder.encode(user.getPassword()); //加密后的密码
            user.setPassword(encodePassword);

            userMapper.insertUser(user); //添加用户


            //添加用户权限
            UserAuthority userAuthority = new UserAuthority();

            userAuthority.setId(SnowId.nextId());
            userAuthority.setUser_id(userid);
            userAuthority.setAuthority_id(2); //普通用户

            userAuthorityMapper.insertUserAuthority(userAuthority);

        }
//        catch (Exception e)
//        {
//            throw new RuntimeException("注册用户失败");
//        }

    }




