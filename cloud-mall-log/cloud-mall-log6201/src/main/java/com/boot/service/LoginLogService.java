package com.boot.service;

import com.boot.pojo.LoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginLogService {

    void insertLoginLog(LoginLog loginLog);

    List<LoginLog> selectLoginLogBylimit(int page,int limit);

    int selectLoginLogCount();

    //查询登录者使用的浏览器(默认展示2个)
    List<String> selectLoginUserBrowser();

    int selectLoginCountByBrowser(String browser);

    int selectLoginCountByTime(String time);

}
