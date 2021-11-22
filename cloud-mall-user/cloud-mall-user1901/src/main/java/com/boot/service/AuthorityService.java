package com.boot.service;

import org.apache.ibatis.annotations.Param;

public interface AuthorityService {


    String selectAuthorityNameById(int id);


}
