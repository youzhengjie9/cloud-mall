package com.boot.dao;

import com.boot.pojo.UserAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserAuthorityMapper {


    void insertUserAuthority(UserAuthority userAuthority);


}
