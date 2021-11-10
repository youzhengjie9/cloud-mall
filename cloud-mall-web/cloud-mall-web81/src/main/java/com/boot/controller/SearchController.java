package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.feign.search.fallback.SearchFallbackFeign;
import com.boot.pojo.Product;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@Api("搜索服务 web api")
@RequestMapping(path = "/search")
public class SearchController {

    @Autowired
    private SearchFallbackFeign searchFallbackFeign;

    @Autowired
    private ProductFallbackFeign productFallbackFeign;

    //搜索服务初始化
    @ResponseBody
    @GetMapping(path = "/initSearch")
    public String initSearch() throws IOException{

        String res = searchFallbackFeign.initSearch();
        return res;
    }

    @GetMapping(path = "/SearchData",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String SearchData(String text) throws IOException {
        JSONObject jsonObject = new JSONObject();
        if(!StringUtils.isBlank(text)) //当搜索有结果返回
        {
            List<Product> products = searchFallbackFeign.searchProductByName(text);
            jsonObject.put("products",products);
        }else {
            List<Product> products = productFallbackFeign.selectAllProduct();
            jsonObject.put("products",products);
        }
        return JSON.toJSONString(jsonObject);
    }


    @PostMapping(path = "/toSearchPage")
    public String toSearchPage(String text,Model model) throws IOException {
        if(!StringUtils.isBlank(text))
        {
            List<Product> products = searchFallbackFeign.searchProductByName(text);
            model.addAttribute("products",products);
        }else {

        }
        return "client/view/newpage/search";
    }


}
