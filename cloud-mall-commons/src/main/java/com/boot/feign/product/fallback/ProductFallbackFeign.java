package com.boot.feign.product.fallback;

import com.boot.feign.product.fallback.impl.ProductFallbackFeignImpl;
import com.boot.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 游政杰
 */
@Component
@FeignClient(value = "cloud-mall-product",fallback = ProductFallbackFeignImpl.class)
public interface ProductFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/product/selectAllProduct")
    public List<Product> selectAllProduct();

    @ResponseBody
    @GetMapping(path = "/feign/product/selectIntroduceByPid/{pid}")
    public String[] selectIntroduceByPid(@PathVariable("pid") long pid);

    @ResponseBody
    @GetMapping(path = "/feign/product/selectProductByPid/{productId}")
    public Product selectProductByPid(@PathVariable("productId") long productId);

    //查询所有商品数量
    @ResponseBody
    @GetMapping(path = "/feign/product/selectProductCount")
    public int selectProductCount();

}
