package com.boot.feign.product.notFallback;

import com.boot.data.CommonResult;
import com.boot.feign.product.fallback.impl.ProductFallbackFeignImpl;
import com.boot.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-product")
public interface ProductFeign {

    //减库存
    @ResponseBody
    @GetMapping(path = "/feign/product/decrNumberByPid/{productId}/{number}")
    public CommonResult<Product> decrNumberByPid(@PathVariable("productId") long productId,
                                                 @PathVariable("number") int number);


}
