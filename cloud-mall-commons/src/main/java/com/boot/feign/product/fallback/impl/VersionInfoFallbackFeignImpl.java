package com.boot.feign.product.fallback.impl;

import com.boot.feign.product.fallback.VersionInfoFallbackFeign;
import com.boot.pojo.VersionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class VersionInfoFallbackFeignImpl implements VersionInfoFallbackFeign {


    @Override
    public List<VersionInfo> selectVersionInfoByPid(long pid) {
        log.error("查询商品编号为:"+pid+"的版本信息失败");
        return null;
    }

    @Override
    public List<VersionInfo> selectAllVersionInfo() {
        log.error("selectAllVersionInfo error");
        return null;
    }

    @Override
    public int selectOrderCountBypid(long pid) {
        log.error("selectOrderCountBypid error");
        return 0;
    }

    @Override
    public List<VersionInfo> selectVersionInfoByPidAndOrder(long pid, long order) {
        log.error("selectVersionInfoByPidAndOrder error");
        return null;
    }

    @Override
    public String selectVersionInfoTitle(long pid, long order) {
        log.error("selectVersionInfoTitle error");
        return null;
    }

    @Override
    public String selectVersionInfoDesc(long pid, long order) {
        log.error("selectVersionInfoDesc error");
        return null;
    }

    @Override
    public BigDecimal selectPriceByversionId(long versionId) {
        log.error("selectPriceByversionId error");
        return null;
    }

    @Override
    public String selectNameByversionId(long versionId) {
        log.error("selectNameByversionId error");
        return null;
    }
}
