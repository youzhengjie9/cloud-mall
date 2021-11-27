package com.boot.feign.product.fallback.impl;

import com.boot.feign.product.fallback.ClassifyFallbackFeign;
import com.boot.pojo.Classify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ClassifyFallbackFeignImpl implements ClassifyFallbackFeign {


    @Override
    public List<Classify> selectAllClassify() {
        log.error("查询所有分类失败");
        return null;
    }

    @Override
    public Classify selectClassifyByid(long id) {
        log.error("selectClassifyByid error");
        return null;
    }

    @Override
    public int selectClassifyCount() {
        log.error("selectClassifyCount error");
        return 0;
    }

    @Override
    public List<Classify> selectClassifiesByText(String text) {
        log.error("selectClassifiesByText error");
        return null;
    }

    @Override
    public int selectClassifiesCountByText(String text) {
        log.error("selectClassifiesCountByText error");
        return 0;
    }

}
