package com.boot.dao;

import com.boot.pojo.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginLogMapper {


    void insertLoginLog(LoginLog loginLog);



}
