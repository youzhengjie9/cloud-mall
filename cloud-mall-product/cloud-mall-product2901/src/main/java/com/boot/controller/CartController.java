package com.boot.controller;

import com.boot.pojo.Cart;
import com.boot.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/cart")
@Api("购物车服务api")
public class CartController {

    @Autowired
    private CartService cartService;

    //根据用户id查询购物车
    @ResponseBody
    @GetMapping(path = "/selectCartByUserId/{userid}")
    public List<Cart> selectCartByUserId(@PathVariable("userid") long userid){

        List<Cart> carts = cartService.selectCartByUserId(userid);

        return carts;
    }



}
