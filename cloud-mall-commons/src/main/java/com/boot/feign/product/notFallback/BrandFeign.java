package com.boot.feign.product.notFallback;

import com.boot.data.CommonResult;
import com.boot.feign.product.fallback.impl.BrandFallbackFeignImpl;
import com.boot.pojo.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author 游政杰
 */
@Component
@FeignClient(value = "cloud-mall-product")
public interface BrandFeign {


    @ResponseBody
    @PostMapping(path = "/feign/brand/insertBrand")
    public CommonResult insertBrand(@RequestBody Brand brand);


    @ResponseBody
    @PostMapping(path = "/feign/brand/updateBrandName")
    public CommonResult updateBrandName(@RequestBody Brand brand);

    @ResponseBody
    @GetMapping(path = "/feign/brand/deleteBrand/{id}")
    public CommonResult deleteBrand(@PathVariable("id") long id);

    @ResponseBody
    @GetMapping(path = "/feign/brand/batchDeleteBrand")
    public CommonResult batchDeleteBrand(@RequestParam("ids")long[] ids);


}
