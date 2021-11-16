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

    //通过pid查询order的数量（被去重后）
    int selectOrderCountBypid(@Param("pid")long pid);

    //根据商品id和order查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPidAndOrder(@Param("pid")long pid,@Param("order") long order);

    String selectVersionInfoTitle(@Param("pid")long pid,@Param("order") long order);

    String selectVersionInfoDesc(@Param("pid")long pid,@Param("order") long order);


}
