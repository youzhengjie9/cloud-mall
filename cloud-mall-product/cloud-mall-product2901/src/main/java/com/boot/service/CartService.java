package com.boot.service;

import com.boot.pojo.Cart;
import java.util.List;

public interface CartService {


    //根据用户id查询购物车
    List<Cart> selectCartByUserId(long userid);



}
