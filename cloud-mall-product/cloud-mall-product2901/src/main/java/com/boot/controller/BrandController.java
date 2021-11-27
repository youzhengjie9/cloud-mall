package com.boot.controller;

import com.boot.data.CommonResult;
import com.boot.pojo.Brand;
import com.boot.service.BrandService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    @GetMapping(path = "/selectBrandCount")
    public int selectBrandCount(){

        return brandService.selectBrandCount();
    }
    @ResponseBody
    @GetMapping(path = "/selectBrandByName/{brandName}")
    public List<Brand> selectBrandByName(@PathVariable("brandName") String brandName){

        return brandService.selectBrandByName(brandName);
    }
    @ResponseBody
    @GetMapping(path = "/selectBrandCountByName/{brandName}")
    public int selectBrandCountByName(@PathVariable("brandName") String brandName){

        return brandService.selectBrandCountByName(brandName);
    }

    @ResponseBody
    @PostMapping(path = "/insertBrand")
    public CommonResult insertBrand(@RequestBody Brand brand){

        CommonResult<Object> commonResult = new CommonResult<>();

        brandService.insertBrand(brand);

        return commonResult;
    }

    @ResponseBody
    @PostMapping(path = "/updateBrandName")
    public CommonResult updateBrandName(@RequestBody Brand brand){

        CommonResult<Object> commonResult = new CommonResult<>();

        brandService.updateBrandName(brand);

        return commonResult;
    }

    @ResponseBody
    @GetMapping(path = "/deleteBrand/{id}")
    public CommonResult deleteBrand(@PathVariable("id") long id){

        CommonResult<Object> commonResult = new CommonResult<>();

        brandService.deleteBrand(id);

        return commonResult;
    }
    @ResponseBody
    @GetMapping(path = "/batchDeleteBrand")
    public CommonResult batchDeleteBrand(@RequestParam("ids")long[] ids){


        CommonResult<Object> commonResult = new CommonResult<>();

        brandService.batchDeleteBrand(ids);

        return commonResult;
    }

}
