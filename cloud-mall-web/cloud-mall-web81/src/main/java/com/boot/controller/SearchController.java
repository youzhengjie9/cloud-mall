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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final String SEARCH_TEXT_KEY="searchText"; //搜索文本key
    private final String SEARCH_BRAND_KEY="searchBrand";//搜索品牌key
    private final String SEARCH_CLASSIFY_KEY="searchClassify"; //搜索分类key
    private final String PAGE_PRODUCT_COUNT="pageProductCount"; //分页之前查询的总数
    private final int from=0; //分页头

    private final int size=15;//分页尾


    @Autowired
    private SearchFallbackFeign searchFallbackFeign;

    @Autowired
    private ProductFallbackFeign productFallbackFeign;

    @Autowired
    private BrandFallbackFeign brandFallbackFeign;

    @Autowired
    private ClassifyFallbackFeign classifyFallbackFeign;

    @Autowired
    private RedisTemplate redisTemplate;

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
    public String toSearchPage(String text, Model model) throws IOException {

        if(text==null||text=="")
        {
            text="^";
        }
        List<String> brandNames=new LinkedList<>(); //品牌去重
        List<String> classifyName=new LinkedList<>(); //分类去重


        List<Brand> brandList=new LinkedList<>(); //品牌集合
        List<Classify> classifyList=new LinkedList<>(); //分类集合


            List<Product> products = searchFallbackFeign.searchProductByName(text);

            if(products!=null&&products.size()>0)
            {
                model.addAttribute("products",products);
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
            }
            //把搜索内容放入redis

            redisTemplate.opsForValue().set(SEARCH_TEXT_KEY,text);
            redisTemplate.opsForValue().set(SEARCH_BRAND_KEY,0+"");
            redisTemplate.opsForValue().set(SEARCH_CLASSIFY_KEY,0+"");

        int pageProductCount = (int) redisTemplate.opsForValue().get(PAGE_PRODUCT_COUNT);//获取分页前查询的总数

        int x=size-from; //计算出每一页数量的Max
        int pagecount=(pageProductCount%x==0)?pageProductCount/x:(pageProductCount/x)+1; //页的总数

        model.addAttribute("pagecount",pagecount);

        return "client/view/newpage/search";
    }



    //查询所有数据并分页
    @ResponseBody
    @GetMapping(path = "/searchProductsByCondition",produces = "application/json; charset=utf-8")
    public String searchProductsByCondition(@RequestParam(value = "brandid",defaultValue = "-10") long brandid,
                                                   @RequestParam(value = "classifyid",defaultValue = "-10") long classifyid,
                                                   @RequestParam(value = "from",defaultValue = "0") int from,
                                                   @RequestParam(value = "size",defaultValue = "15") int size)
                                                    throws IOException{


        JSONObject jsonObject = new JSONObject();
        if(brandid==-10) //如果是默认，代表点击的不是品牌
       {
            brandid= Long.valueOf((String) redisTemplate.opsForValue().get(SEARCH_BRAND_KEY));
       }else {
           //如果不是默认，就要把新的品牌id放进去
           redisTemplate.opsForValue().set(SEARCH_BRAND_KEY,brandid+"");
       }
       if(classifyid==-10)
       {
           classifyid= Long.valueOf((String) redisTemplate.opsForValue().get(SEARCH_CLASSIFY_KEY));
       }else {

           redisTemplate.opsForValue().set(SEARCH_CLASSIFY_KEY,classifyid+"");
       }
        String text=""; //搜索文本
        //获取搜索文本
        text = (String) redisTemplate.opsForValue().get(SEARCH_TEXT_KEY);

        List<Product> products = searchFallbackFeign.searchProductsByCondition(text, brandid, classifyid, from, size);

        jsonObject.put("products",products);

        //品牌分类去重复
        List<String> brandNames=new LinkedList<>(); //品牌去重
        List<String> classifyName=new LinkedList<>(); //分类去重


        List<Brand> brandList=new LinkedList<>(); //品牌集合
        List<Classify> classifyList=new LinkedList<>(); //分类集合


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
            jsonObject.put("brandList",brandList);
            jsonObject.put("classifyList",classifyList);

        }else{

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
            jsonObject.put("brandList",brandList);
            jsonObject.put("classifyList",classifyList);

        }

        jsonObject.put("curbrandid",brandid);
        jsonObject.put("curclassifyid",classifyid);


        int pageProductCount = (int) redisTemplate.opsForValue().get(PAGE_PRODUCT_COUNT);//获取分页前查询的总数
//        jsonObject.put("pageCount",pageProductCount);

        int x=size-from; //计算出每一页数量的Max
        int pagecount=(pageProductCount%x==0)?pageProductCount/x:(pageProductCount/x)+1; //页的总数

        jsonObject.put("pagecount",pagecount);



        return JSON.toJSONString(jsonObject);
    }



}
