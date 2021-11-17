package com.boot.feign.product.fallback.impl;

import com.boot.feign.product.fallback.CartFallbackFeign;
import com.boot.pojo.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CartFallbackFeignImpl implements CartFallbackFeign {

    @Override
    public List<Cart> selectCartByUserId(long userid) {
        log.error("selectCartByUserId error");
        return null;
    }
}
