package com.boot.feign.product.notFallback;

import com.boot.pojo.Classify;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 游政杰
 */
@Component
@FeignClient(value = "cloud-mall-product")
public interface ClassifyFeign {

    @ResponseBody
    @GetMapping(path = "/feign/Classify/navEnable/{id}")
    public int navEnable(@PathVariable("id") long id);


    @ResponseBody
    @GetMapping(path = "/feign/Classify/navDisable/{id}")
    public int navDisable(@PathVariable("id") long id);


    //添加分类
    @ResponseBody
    @PostMapping(path = "/feign/Classify/insertClassify")
    public String insertClassify(@RequestBody Classify classify);

    //修改分类
    @ResponseBody
    @PostMapping(path = "/feign/Classify/updateClassify")
    public String updateClassify(@RequestBody Classify classify);

    //删除分类
    @ResponseBody
    @GetMapping(path = "/feign/Classify/deleteClassify/{id}")
    public String deleteClassify(@PathVariable("id") long id);

    //批量删除分类
    @ResponseBody
    @GetMapping(path = "/feign/Classify/batchDeleteClassify")
    public String batchDeleteClassify(@RequestParam("ids") long[] ids);

}
