package com.boot.service.impl;

import com.boot.dao.CartMapper;
import com.boot.pojo.Cart;
import com.boot.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> selectCartByUserId(long userid) {
        return cartMapper.selectCartByUserId(userid);
    }

    @Override
    public void addProductToCart(Cart cart) {
        cartMapper.addProductToCart(cart);
    }

    @Override
    public Cart selectCartByCartId(long cartid) {
        return cartMapper.selectCartByCartId(cartid);
    }

    @Override
    public void updateCountAndTotalPrice(long id, int goodsCount, BigDecimal singleGoodsMoney) {
        cartMapper.updateCountAndTotalPrice(id, goodsCount, singleGoodsMoney);
    }
}
