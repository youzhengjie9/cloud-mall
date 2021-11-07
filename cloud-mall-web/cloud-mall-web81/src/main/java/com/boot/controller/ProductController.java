package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductFallbackFeign productFallbackFeign;


    @ResponseBody
    @GetMapping(path = "/selectAllProduct",produces = "application/json; charset=utf-8")
    public String selectAllProduct(){
        List<Product> products = productFallbackFeign.selectAllProduct();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("products",products);
        return jsonObject.toJSONString();
    }


}
