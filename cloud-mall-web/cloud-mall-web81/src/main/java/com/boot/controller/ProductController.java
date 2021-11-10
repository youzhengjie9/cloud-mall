package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.feign.product.fallback.VersionInfoFallbackFeign;
import com.boot.pojo.Product;
import com.boot.pojo.VersionInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/product")
@Api("商品服务 web api")
public class ProductController {

    @Autowired
    private ProductFallbackFeign productFallbackFeign;

    @Autowired
    private VersionInfoFallbackFeign versionInfoFallbackFeign;


    @ResponseBody
    @GetMapping(path = "/selectAllProduct",produces = "application/json; charset=utf-8")
    public String selectAllProduct(){
        List<Product> products = productFallbackFeign.selectAllProduct();
        List<VersionInfo> versionInfos = versionInfoFallbackFeign.selectAllVersionInfo();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("products",products);
        jsonObject.put("versions_info",versionInfos);
        return jsonObject.toJSONString();
    }

//    @ResponseBody
//    @GetMapping(path = "/selectIntroduceByPid/{pid}")
//    public String[] selectIntroduceByPid(@PathVariable("pid") long pid){
//
//        String imgs[] = productFallbackFeign.selectIntroduceByPid(pid);
//
//        return imgs;
//    }


}
