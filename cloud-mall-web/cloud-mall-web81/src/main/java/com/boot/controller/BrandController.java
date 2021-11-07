package com.boot.controller;

import com.boot.feign.product.fallback.BrandFallbackFeign;
import com.boot.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/brand")
public class BrandController {

    @Autowired
    private BrandFallbackFeign brandFallbackFeign;

    @ResponseBody
    @GetMapping(path = "/selectAllBrand")
    public List<Brand> selectAllBrand()
    {

        return brandFallbackFeign.selectAllBrand();
    }

    @ResponseBody
    @GetMapping(path = "/selectBrandIdNameByPid/{pid}")
    public long selectBrandIdNameByPid(@PathVariable("pid") long pid)
    {

        return brandFallbackFeign.selectBrandIdNameByPid(pid);
    }

    @ResponseBody
    @GetMapping(path = "/selectBrandByid/{bid}")
    public Brand selectBrandByid(@PathVariable("bid") long bid)
    {

        return brandFallbackFeign.selectBrandByid(bid);
    }


}
