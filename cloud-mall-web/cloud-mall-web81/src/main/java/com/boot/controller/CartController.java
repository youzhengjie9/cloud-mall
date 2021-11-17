package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.feign.product.fallback.CartFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Cart;
import com.boot.pojo.Product;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Api("购物车服务 web api")
@Controller
@RequestMapping(path = "/web/cart")
public class CartController {

  @Autowired
  private SpringSecurityUtil springSecurityUtil;

  @Autowired
  private UserFallbackFeign userFallbackFeign;

  @Autowired
  private CartFallbackFeign cartFallbackFeign;

  //进入购物车
  @RequestMapping(path = "/tocart")
  public String tocart()
  {

    return "client/view/newpage/cart";
  }


  // 放入购物车
  @ResponseBody
  @PostMapping(path = "/pushCart")
  public CommonResult<Product> pushCart(@RequestParam(value = "skuarr[]",required = true) long[] skuarr,
                                        @RequestParam(value = "productid",required = true) long productid) {

    CommonResult<Product> commonResult = new CommonResult<>();
    // 二次校验数组数据
    for (long e : skuarr) {

      if (e == 0) // 非法数据
      {
          commonResult.setCode(ResultCode.FAILURE);
        break;
      }
    }

    return commonResult;
  }


  //根据用户id查询购物车
  @ResponseBody
  @GetMapping(path = "/selectCartByUserId")
  public JSONObject selectCartByUserId(HttpSession session){

    String curname = springSecurityUtil.currentUser(session); //用户名是唯一的

    long id = userFallbackFeign.selectUserIdByName(curname);

    List<Cart> carts = cartFallbackFeign.selectCartByUserId(id);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("carts",carts);

    return jsonObject;
  }







}
