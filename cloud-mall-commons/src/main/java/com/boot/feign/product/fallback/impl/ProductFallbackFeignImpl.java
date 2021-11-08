package com.boot.feign.product.fallback.impl;

import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.pojo.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 游政杰
 */
@Component
@Slf4j
public class ProductFallbackFeignImpl implements ProductFallbackFeign {


    @Override
    public List<Product> selectAllProduct() {
        log.error("查询所有商品失败");
        return null;
    }

    @Override
    public String[] selectIntroduceByPid(long pid) {
        log.error("selectIntroduceByPid error");
        return null;
    }

}
