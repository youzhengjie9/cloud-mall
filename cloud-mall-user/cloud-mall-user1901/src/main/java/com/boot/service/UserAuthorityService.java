package com.boot.service;

import com.boot.pojo.UserAuthority;
import org.apache.ibatis.annotations.Param;

public interface UserAuthorityService {


    int selectAuthorityIdByUserId(long userid);

    //修改用户权限
    void updateUserAuthority(UserAuthority userAuthority);



}
