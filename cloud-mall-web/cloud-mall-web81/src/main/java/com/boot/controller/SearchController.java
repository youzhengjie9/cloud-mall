package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.feign.product.fallback.BrandFallbackFeign;
import com.boot.feign.product.fallback.ClassifyFallbackFeign;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.feign.search.fallback.SearchFallbackFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.Classify;
import com.boot.pojo.Product;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.LinkedList;
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

    @Autowired
    private BrandFallbackFeign brandFallbackFeign;

    @Autowired
    private ClassifyFallbackFeign classifyFallbackFeign;

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


    @GetMapping(path = "/toSearchPage")
    public String toSearchPage(String text,Model model) throws IOException {

        List<String> brandNames=new LinkedList<>(); //品牌去重
        List<String> classifyName=new LinkedList<>(); //分类去重


        List<Brand> brandList=new LinkedList<>(); //品牌集合
        List<Classify> classifyList=new LinkedList<>(); //分类集合


            List<Product> products = searchFallbackFeign.searchProductByName(text);
            model.addAttribute("products",products);

            if(products!=null&&products.size()>0)
            {

                for (Product product : products) {

                    Brand brand = brandFallbackFeign.selectBrandByid(product.getB_id());
                    Classify classify = classifyFallbackFeign.selectClassifyByid(product.getFl_id());

                    if (!brandNames.contains(brand.getBrandName())) //利用集合来去重
                    {
                        brandList.add(brand);
                        brandNames.add(brand.getBrandName());
                    }
                    if(!classifyName.contains(classify.getText()))
                    {
                        classifyList.add(classify);
                        classifyName.add(classify.getText());
                    }
                }
                model.addAttribute("brandList",brandList);
                model.addAttribute("classifyList",classifyList);
            }else { //假如什么也查不到

                List<Brand> brandList1 = brandFallbackFeign.selectAllBrand();
                List<Classify> classifyList1 = classifyFallbackFeign.selectAllClassify();

                model.addAttribute("brandList",brandList1);
                model.addAttribute("classifyList",classifyList1);

//                List<Product> products1 = productFallbackFeign.selectAllProduct();
//                model.addAttribute("products",products1);

            }

        return "client/view/newpage/search";
    }





}
