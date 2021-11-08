package com.boot.feign.product.fallback.impl;

import com.boot.feign.product.fallback.VersionInfoFallbackFeign;
import com.boot.pojo.VersionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
}
