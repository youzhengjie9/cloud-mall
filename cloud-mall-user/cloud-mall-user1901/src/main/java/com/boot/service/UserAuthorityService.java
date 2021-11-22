package com.boot.service;

import org.apache.ibatis.annotations.Param;

public interface UserAuthorityService {


    int selectAuthorityIdByUserId(long userid);


}
