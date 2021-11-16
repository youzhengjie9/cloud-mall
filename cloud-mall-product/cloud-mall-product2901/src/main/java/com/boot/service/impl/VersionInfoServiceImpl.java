package com.boot.service.impl;

import com.boot.dao.VersionInfoMapper;
import com.boot.pojo.VersionInfo;
import com.boot.service.VersionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VersionInfoServiceImpl implements VersionInfoService {

    @Autowired
    private VersionInfoMapper versionInfoMapper;

    @Override
    public List<VersionInfo> selectVersionInfoByPid(long pid) {
        return versionInfoMapper.selectVersionInfoByPid(pid);
    }

    @Override
    public List<VersionInfo> selectAllVersionInfo() {
        return versionInfoMapper.selectAllVersionInfo();
    }

    @Override
    public int selectOrderCountBypid(long pid) {
        return versionInfoMapper.selectOrderCountBypid(pid);
    }

    @Override
    public List<VersionInfo> selectVersionInfoByPidAndOrder(long pid, long order) {
        return versionInfoMapper.selectVersionInfoByPidAndOrder(pid, order);
    }

    @Override
    public String selectVersionInfoTitle(long pid, long order) {
        return versionInfoMapper.selectVersionInfoTitle(pid, order);
    }

    @Override
    public String selectVersionInfoDesc(long pid, long order) {
        return versionInfoMapper.selectVersionInfoDesc(pid, order);
    }
}
