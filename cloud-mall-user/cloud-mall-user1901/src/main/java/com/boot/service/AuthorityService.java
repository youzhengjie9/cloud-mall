package com.boot.service;

import com.boot.pojo.Authority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorityService {


    String selectAuthorityNameById(int id);

    //查找除了当前权限之外的权限
    List<Authority> selectAuthorityExcludeCurAuthority(int id);
}
