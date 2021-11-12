package com.boot.feign.search.fallback.impl;

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
}
