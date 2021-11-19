package com.boot.service;

import com.boot.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {


    //根据用户id查询购物车
    List<Cart> selectCartByUserId(long userid);

    void  addProductToCart(Cart cart);

    Cart selectCartByCartId(long cartid);

    //修改指定购物车的购买数量和总价
    void updateCountAndTotalPrice(long id,
                                  int goodsCount,
                                  BigDecimal singleGoodsMoney);
}
