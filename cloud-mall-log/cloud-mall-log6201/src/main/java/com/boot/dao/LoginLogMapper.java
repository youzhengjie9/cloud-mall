package com.boot.dao;

import com.boot.pojo.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginLogMapper {


    void insertLoginLog(LoginLog loginLog);

    List<LoginLog> selectLoginLogBylimit(@Param("page") int page, @Param("limit") int limit);

    int selectLoginLogCount();
}
