package com.boot.service;

import com.boot.pojo.VersionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionInfoService {


    //根据商品id查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPid(long pid);

    //查询所有版本信息
    List<VersionInfo> selectAllVersionInfo();

    //通过pid查询order的数量（被去重后）
    int selectOrderCountBypid(long pid);

    //根据商品id和order查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPidAndOrder(long pid,long order);


    String selectVersionInfoTitle(long pid,long order);

    String selectVersionInfoDesc(long pid,long order);
}
