package com.boot.feign.product.notFallback;

import com.alibaba.fastjson.JSONObject;
import com.boot.data.CommonResult;
import com.boot.pojo.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-product")
public interface CartFeign {

    @ResponseBody
    @PostMapping(path = "/feign/cart/addProductToCart")
    public CommonResult<Cart> addProductToCart(@RequestBody Cart cart);

    // 修改指定购物车的购买数量和总价
    @ResponseBody
    @PostMapping(path = "/feign/cart/updateCountAndTotalPrice")
    public CommonResult<String> updateCountAndTotalPrice(@RequestBody JSONObject jsonObject);


    @ResponseBody
    @GetMapping(path = "/feign/cart/deleteCartByCartId/{cartid}")
    public String deleteCartByCartId(@PathVariable("cartid") long cartid);

}
