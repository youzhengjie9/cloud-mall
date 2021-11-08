package com.boot.dao;

import com.boot.pojo.VersionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VersionInfoMapper {

    //根据商品id查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPid(@Param("pid")long pid);

    //查询所有版本信息
    List<VersionInfo> selectAllVersionInfo();



}
