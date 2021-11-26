package com.boot.feign.product.fallback;

import com.boot.feign.product.fallback.impl.BrandFallbackFeignImpl;
import com.boot.pojo.Brand;
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
@FeignClient(value = "cloud-mall-product",fallback = BrandFallbackFeignImpl.class)
public interface BrandFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/brand/selectAllBrand")
    public List<Brand> selectAllBrand();

    @ResponseBody
    @GetMapping(path = "/feign/brand/selectBrandIdNameByPid/{pid}")
    public long selectBrandIdNameByPid(@PathVariable("pid") long pid);

    @ResponseBody
    @GetMapping(path = "/feign/brand/selectBrandByid/{bid}")
    public Brand selectBrandByid(@PathVariable("bid") long bid);

    @ResponseBody
    @GetMapping(path = "/feign/brand/selectBrandCount")
    public int selectBrandCount();

    @ResponseBody
    @GetMapping(path = "/feign/brand/selectBrandByName/{brandName}")
    public List<Brand> selectBrandByName(@PathVariable("brandName") String brandName);



    @ResponseBody
    @GetMapping(path = "/feign/brand/selectBrandCountByName/{brandName}")
    public int selectBrandCountByName(@PathVariable("brandName") String brandName);

}
