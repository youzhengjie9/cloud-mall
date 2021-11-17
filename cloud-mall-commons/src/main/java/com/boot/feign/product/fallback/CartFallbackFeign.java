package com.boot.feign.product.fallback;

import com.boot.feign.product.fallback.impl.CartFallbackFeignImpl;
import com.boot.pojo.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-product",fallback = CartFallbackFeignImpl.class)
public interface CartFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/cart/selectCartByUserId/{userid}")
    public List<Cart> selectCartByUserId(@PathVariable("userid") long userid);




}
