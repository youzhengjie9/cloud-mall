package com.boot.feign.product.fallback.impl;

import com.boot.feign.product.fallback.BrandFallbackFeign;
import com.boot.pojo.Brand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BrandFallbackFeignImpl implements BrandFallbackFeign {

    @Override
    public List<Brand> selectAllBrand() {
        log.error("selectAllBrand error");
        return null;
    }

    @Override
    public long selectBrandIdNameByPid(long pid) {
        log.error("selectBrandIdNameByPid error");
        return 0;
    }

    @Override
    public Brand selectBrandByid(long bid) {
        log.error("selectBrandByid error");
        return null;
    }
}
