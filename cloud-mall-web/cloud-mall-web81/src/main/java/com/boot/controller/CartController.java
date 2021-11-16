package com.boot.controller;

import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.pojo.Product;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Api("购物车服务 web api")
@Controller
@RequestMapping(path = "/web/cart")
public class CartController {

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
}
