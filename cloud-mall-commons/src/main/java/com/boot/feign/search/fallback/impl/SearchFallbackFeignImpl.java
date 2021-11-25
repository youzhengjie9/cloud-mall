package com.boot.feign.search.fallback.impl;

import com.boot.data.CommonResult;
import com.boot.feign.search.fallback.SearchFallbackFeign;
import com.boot.pojo.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class SearchFallbackFeignImpl implements SearchFallbackFeign {

    @Override
    public String initSearch() throws IOException {
        log.error("initSearch error");
        return null;
    }

    @Override
    public List<Product> searchProductByName(String text) throws IOException {
        log.error("searchProductByName error");
        return null;
    }

    @Override
    public List<Product> searchAllProductByLimit(int from, int size) throws IOException {
        log.error("searchAllProductByLimit error");
        return null;
    }

    @Override
    public List<Product> searchProductsByCondition(String text, long brandid, long classifyid, int from, int size) throws IOException {
        log.error("searchProductsByCondition error");
        return null;
    }

    @Override
    public CommonResult<Long> searchAllProductsCount() {
        log.error("searchAllProductsCount error");
        return null;
    }

    @Override
    public CommonResult<List<Product>> searchProductsByNameAndLimit(int from, int size, String text) throws IOException {
        log.error("searchProductsByNameAndLimit error");
        return null;
    }

    @Override
    public CommonResult<Long> searchProductCountByName(String text) throws IOException {
        log.error("searchProductCountByName error");
        return null;
    }
}
