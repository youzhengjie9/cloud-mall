package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.pojo.Cart;
import com.boot.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(path = "/feign/cart")
@Api("购物车服务api")
public class CartController {

  @Autowired private CartService cartService;

  // 根据用户id查询购物车
  @ResponseBody
  @GetMapping(path = "/selectCartByUserId/{userid}")
  public List<Cart> selectCartByUserId(@PathVariable("userid") long userid) {

    List<Cart> carts = cartService.selectCartByUserId(userid);

    return carts;
  }

  @ResponseBody
  @PostMapping(path = "/addProductToCart")
  public CommonResult<Cart> addProductToCart(@RequestBody Cart cart) {
    CommonResult<Cart> commonResult = new CommonResult<>();
    commonResult.setObj(cart);
    try {
      cartService.addProductToCart(cart);
      return commonResult;
    } catch (Exception e) {
      e.printStackTrace();
      commonResult.setCode(ResultCode.FAILURE);
      return commonResult;
    }
  }

  @ResponseBody
  @GetMapping(path = "/selectCartByCartId/{cartid}")
  public CommonResult<Cart> selectCartByCartId(@PathVariable("cartid") long cartid) {

    CommonResult<Cart> commonResult = new CommonResult<>();

    try {
      Cart cart = cartService.selectCartByCartId(cartid);
      commonResult.setObj(cart);
      return commonResult;
    } catch (Exception e) {
      e.printStackTrace();
      commonResult.setCode(ResultCode.FAILURE);
      return commonResult;
    }
  }

  // 修改指定购物车的购买数量和总价
  @ResponseBody
  @PostMapping(path = "/updateCountAndTotalPrice")
  public CommonResult<String> updateCountAndTotalPrice(@RequestBody JSONObject jsonObject) {

    CommonResult<String> commonResult = new CommonResult<>();
    long cid=(Long) jsonObject.get("cid");
    int count=(Integer) jsonObject.get("newCount");
    BigDecimal total= new BigDecimal(String.valueOf(jsonObject.get("newTotalPrice")));

    try {
      cartService.updateCountAndTotalPrice(cid,count,total);
      return commonResult;
    } catch (Exception e) {
      e.printStackTrace();
      commonResult.setCode(ResultCode.FAILURE);
      return commonResult;
    }
  }
}
