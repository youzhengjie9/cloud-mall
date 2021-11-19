package com.boot.feign.product.fallback.impl;

import com.alibaba.fastjson.JSONObject;
import com.boot.data.CommonResult;
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

    @Override
    public CommonResult<Cart> addProductToCart(Cart cart) {
        log.error("addProductToCart error");
        return null;
    }

    @Override
    public CommonResult<Cart> selectCartByCartId(long cartid) {
        log.error("selectCartByCartId error");
        return null;
    }

    @Override
    public CommonResult<String> updateCountAndTotalPrice(JSONObject jsonObject) {
        log.error("updateCountAndTotalPrice error");
        return null;
    }
}
