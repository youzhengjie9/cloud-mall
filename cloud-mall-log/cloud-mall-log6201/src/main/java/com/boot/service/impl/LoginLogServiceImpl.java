package com.boot.service.impl;

import com.boot.dao.LoginLogMapper;
import com.boot.pojo.LoginLog;
import com.boot.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insertLoginLog(LoginLog loginLog) {
        loginLogMapper.insertLoginLog(loginLog);
    }
}
