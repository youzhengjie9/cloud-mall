package com.boot.dao;

import com.boot.pojo.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AuthorityMapper {


    String selectAuthorityNameById(@Param("id") int id);


    //查找除了当前权限之外的权限
    List<Authority> selectAuthorityExcludeCurAuthority(@Param("id") int id);



}
