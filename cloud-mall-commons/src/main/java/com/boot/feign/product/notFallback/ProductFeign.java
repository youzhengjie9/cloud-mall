package com.boot.feign.product.notFallback;

import com.boot.data.CommonResult;
import com.boot.feign.product.fallback.impl.ProductFallbackFeignImpl;
import com.boot.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-product")
public interface ProductFeign {

    //减库存
    @ResponseBody
    @GetMapping(path = "/feign/product/decrNumberByPid/{productId}/{number}")
    public CommonResult<Product> decrNumberByPid(@PathVariable("productId") long productId,
                                                 @PathVariable("number") int number);

    //添加商品
    @ResponseBody
    @PostMapping(path = "/feign/product/insertProduct")
    public CommonResult<Product> insertProduct(@RequestBody Product product);

    @ResponseBody
    @PostMapping(path = "/feign/product/updateProduct")
    public CommonResult<Product> updateProduct(@RequestBody Product product);

    @ResponseBody
    @GetMapping(path = "/feign/product/deleteProduct/{productId}")
    public CommonResult<String> deleteProduct(@PathVariable("productId") long productId);

    @ResponseBody
    @GetMapping(path = "/feign/product/batchDeleteProducts")
    public CommonResult<String> batchDeleteProducts(@RequestParam("ids")long[] ids);
}
