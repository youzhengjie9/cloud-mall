package com.boot.dao;

import com.boot.pojo.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthorityMapper {


    String selectAuthorityNameById(@Param("id") int id);





}
