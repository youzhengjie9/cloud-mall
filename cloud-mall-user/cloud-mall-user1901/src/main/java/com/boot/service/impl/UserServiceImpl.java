package com.boot.service.impl;

import com.boot.dao.UserMapper;
import com.boot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 游政杰
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String selectPasswordByuserName(String username) {
        return userMapper.selectPasswordByuserName(username);
    }

    @Override
    public long selectUserIdByName(String username) {
        return userMapper.selectUserIdByName(username);
    }


}
