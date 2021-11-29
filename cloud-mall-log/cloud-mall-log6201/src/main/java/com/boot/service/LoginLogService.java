package com.boot.service;

import com.boot.pojo.LoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginLogService {

    void insertLoginLog(LoginLog loginLog);

    List<LoginLog> selectLoginLogBylimit(int page,int limit);

    int selectLoginLogCount();

}
