package com.boot.dao;

import com.boot.pojo.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDetailMapper {


    void insertUserDetail(UserDetail userDetail);






}
