package com.boot.service.impl;

import com.boot.dao.LoginLogMapper;
import com.boot.pojo.LoginLog;
import com.boot.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insertLoginLog(LoginLog loginLog) {
        loginLogMapper.insertLoginLog(loginLog);
    }

    @Override
    public List<LoginLog> selectLoginLogBylimit(int page, int limit) {
        return loginLogMapper.selectLoginLogBylimit(page, limit);
    }

    @Override
    public int selectLoginLogCount() {
        return loginLogMapper.selectLoginLogCount();
    }

    @Override
    public List<String> selectLoginUserBrowser() {
        return loginLogMapper.selectLoginUserBrowser();
    }

    @Override
    public int selectLoginCountByBrowser(String browser) {
        return loginLogMapper.selectLoginCountByBrowser(browser);
    }

    @Override
    public int selectLoginCountByTime(String time) {
        return loginLogMapper.selectLoginCountByTime(time);
    }
}
