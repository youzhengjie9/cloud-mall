package com.boot.controller;

import com.boot.pojo.Brand;
import com.boot.service.BrandService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 游政杰
 *
 */
@Controller
@RequestMapping(path = "/feign/brand")
@Api("品牌服务api")
public class BrandController {

    @Autowired
    private BrandService brandService;


    @ResponseBody
    @GetMapping(path = "/selectAllBrand")
    public List<Brand> selectAllBrand()
    {

        return brandService.selectAllBrand();
    }

    @ResponseBody
    @GetMapping(path = "/selectBrandIdNameByPid/{pid}")
    public long selectBrandIdNameByPid(@PathVariable("pid") long pid)
    {

        return brandService.selectBrandIdNameByPid(pid);
    }

    @ResponseBody
    @GetMapping(path = "/selectBrandByid/{bid}")
    public Brand selectBrandByid(@PathVariable("bid") long bid)
    {

        return brandService.selectBrandByid(bid);
    }

}
