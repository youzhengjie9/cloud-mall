package com.boot.service;

import com.boot.pojo.VersionInfo;

import java.util.List;

public interface VersionInfoService {


    //根据商品id查询对应的版本信息
    List<VersionInfo> selectVersionInfoByPid(long pid);


}
