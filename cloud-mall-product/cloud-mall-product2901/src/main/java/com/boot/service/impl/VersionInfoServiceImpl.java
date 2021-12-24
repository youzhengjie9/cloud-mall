package com.boot.service.impl;

import com.boot.dao.VersionInfoMapper;
import com.boot.pojo.VersionInfo;
import com.boot.service.VersionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
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

    @Override
    public BigDecimal selectPriceByversionId(long versionId) {
        return versionInfoMapper.selectPriceByversionId(versionId);
    }

    @Override
    public String selectNameByversionId(long versionId) {
        return versionInfoMapper.selectNameByversionId(versionId);
    }

    @Override
    public List<VersionInfo> selectVersionInfoByLimit(int page, int size) {
        return versionInfoMapper.selectVersionInfoByLimit(page, size);
    }

    @Override
    public List<VersionInfo> selectVersionInfoByLimitAndPid(long pid, int page, int size) {
        return versionInfoMapper.selectVersionInfoByLimitAndPid(pid, page, size);
    }

    @Override
    public int selectversionInfoCountByPid(long pid) {
        return versionInfoMapper.selectversionInfoCountByPid(pid);
    }

    @Override
    public int selectAllversionInfoCount() {
        return versionInfoMapper.selectAllversionInfoCount();
    }

    @Override
    public int insertVersionInfo(VersionInfo versionInfo) {
        return versionInfoMapper.insertVersionInfo(versionInfo);
    }

    @Override
    public int updateVersionInfo(VersionInfo versionInfo) {
        return versionInfoMapper.updateVersionInfo(versionInfo);
    }

    @Override
    public int deleteVersionInfo(long versionId) {
        return versionInfoMapper.deleteVersionInfo(versionId);
    }

    @Override
    public int batchDeleteVersionInfo(long[] versionIds) {
        try{
            for (long versionId : versionIds) {
                versionInfoMapper.deleteVersionInfo(versionId);
            }
        }catch (Exception e){
            throw new RuntimeException();
        }

        return 0;
    }

    @Override
    public VersionInfo selectVersionByVersionId(long versionId) {
        return versionInfoMapper.selectVersionByVersionId(versionId);
    }


}
