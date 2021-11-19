package com.boot.feign.product.fallback;

import com.alibaba.fastjson.JSONObject;
import com.boot.data.CommonResult;
import com.boot.feign.product.fallback.impl.CartFallbackFeignImpl;
import com.boot.pojo.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-product",fallback = CartFallbackFeignImpl.class)
public interface CartFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/cart/selectCartByUserId/{userid}")
    public List<Cart> selectCartByUserId(@PathVariable("userid") long userid);

    @ResponseBody
    @PostMapping(path = "/feign/cart/addProductToCart")
    public CommonResult<Cart> addProductToCart(@RequestBody Cart cart);

    @ResponseBody
    @GetMapping(path = "/feign/cart/selectCartByCartId/{cartid}")
    public CommonResult<Cart> selectCartByCartId(@PathVariable("cartid") long cartid);

    // 修改指定购物车的购买数量和总价
    @ResponseBody
    @PostMapping(path = "/feign/cart/updateCountAndTotalPrice")
    public CommonResult<String> updateCountAndTotalPrice(@RequestBody JSONObject jsonObject);
}
