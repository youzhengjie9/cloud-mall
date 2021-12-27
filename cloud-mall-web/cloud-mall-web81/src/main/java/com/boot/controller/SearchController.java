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
import com.boot.utils.IpUtils;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@Api("搜索服务 web api")
@RequestMapping(path = "/web/search")
public class SearchController {

    private final String SEARCH_TEXT_KEY="searchText_"; //搜索文本key
    private final String SEARCH_BRAND_KEY="searchBrand_";//搜索品牌key
    private final String SEARCH_CLASSIFY_KEY="searchClassify_"; //搜索分类key
    private final String PAGE_PRODUCT_COUNT="pageProductCount_"; //分页之前查询的总数
    private final int from=0; //分页起始，从id=from+1 开始

    private final int size=15;//分页大小

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

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
    public String SearchData(String text,HttpServletRequest request) throws IOException {
        JSONObject jsonObject = new JSONObject();
        String ip = IpUtils.getIpAddr(request);
        if(!StringUtils.isBlank(text)) //当搜索有结果返回
        {
            List<Product> products = searchFallbackFeign.searchProductByName(text,ip);
            jsonObject.put("products",products);
        }else {
            List<Product> products = productFallbackFeign.selectAllProduct();
            jsonObject.put("products",products);
        }
        return JSON.toJSONString(jsonObject);
    }


    @GetMapping(path = "/toSearchPage")
    public String toSearchPage(String text, Model model, HttpServletRequest request, HttpSession session) throws IOException {
        String ipAddr = IpUtils.getIpAddr(request); //获取ip

        if(text==null||text=="")
        {
            text="^";
        }
        List<String> brandNames=new LinkedList<>(); //品牌去重
        List<String> classifyName=new LinkedList<>(); //分类去重


        List<Brand> brandList=new LinkedList<>(); //品牌集合
        List<Classify> classifyList=new LinkedList<>(); //分类集合


            List<Product> products = searchFallbackFeign.searchProductByName(text,ipAddr);

            if(products!=null&&products.size()>0)
            {
                model.addAttribute("products",products);
                for (Product product : products) {

                    Brand brand = brandFallbackFeign.selectBrandByid(product.getBrand().getId());
                    Classify classify = classifyFallbackFeign.selectClassifyByid(product.getClassify().getId());

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

            redisTemplate.opsForValue().set(SEARCH_TEXT_KEY+ipAddr,text);
            redisTemplate.opsForValue().set(SEARCH_BRAND_KEY+ipAddr,0+"");
            redisTemplate.opsForValue().set(SEARCH_CLASSIFY_KEY+ipAddr,0+"");



        int pageProductCount = (int) redisTemplate.opsForValue().get(PAGE_PRODUCT_COUNT+ipAddr);//获取分页前查询的总数

        int x=size-from; //计算出每一页数量的Max
        int pagecount=(pageProductCount%x==0)?pageProductCount/x:(pageProductCount/x)+1; //页的总数

        model.addAttribute("pagecount",pagecount);

        model.addAttribute("curPage",1); //默认第一页

        try{
            String currentUser = springSecurityUtil.currentUser(session);
            model.addAttribute("username",currentUser);
        }catch (Exception e){
            model.addAttribute("username",null);
        }

        return "client/view/newpage/search";
    }



    //查询所有数据并分页
    @ResponseBody
    @GetMapping(path = "/searchProductsByCondition",produces = "application/json; charset=utf-8")
    public String searchProductsByCondition(@RequestParam(value = "brandid",defaultValue = "-10") long brandid,
                                                   @RequestParam(value = "classifyid",defaultValue = "-10") long classifyid,
                                                   @RequestParam(value = "from",defaultValue = "1") int from,
                                                   @RequestParam(value = "size",defaultValue = "15") int size,
                                            HttpServletRequest request)
                                                    throws IOException{

        String ip = IpUtils.getIpAddr(request);
        int curPage=from;  //当前页

        from=size*(from-1);


        JSONObject jsonObject = new JSONObject();

        jsonObject.put("curPage",curPage);//传入当前页

        if(brandid==-10) //如果是默认，代表点击的不是品牌
       {
            brandid= Long.valueOf((String) redisTemplate.opsForValue().get(SEARCH_BRAND_KEY+ip));
       }else {
           //如果不是默认，就要把新的品牌id放进去
           redisTemplate.opsForValue().set(SEARCH_BRAND_KEY+ip,brandid+"");
       }
       if(classifyid==-10)
       {
           classifyid= Long.valueOf((String) redisTemplate.opsForValue().get(SEARCH_CLASSIFY_KEY+ip));
       }else {

           redisTemplate.opsForValue().set(SEARCH_CLASSIFY_KEY+ip,classifyid+"");
       }
        String text=""; //搜索文本
        //获取搜索文本
        text = (String) redisTemplate.opsForValue().get(SEARCH_TEXT_KEY+ip);


        List<Product> products = searchFallbackFeign.searchProductsByCondition(text, brandid, classifyid, from, size,ip);


        jsonObject.put("products",products);

        //品牌分类去重复
        List<String> brandNames=new LinkedList<>(); //品牌去重
        List<String> classifyName=new LinkedList<>(); //分类去重


        List<Brand> brandList=new LinkedList<>(); //品牌集合
        List<Classify> classifyList=new LinkedList<>(); //分类集合

        if(products!=null&&products.size()>0)
        {
            for (Product product : products) {

                Brand brand = brandFallbackFeign.selectBrandByid(product.getBrand().getId());
                Classify classify = classifyFallbackFeign.selectClassifyByid(product.getClassify().getId());

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

                Brand brand = brandFallbackFeign.selectBrandByid(product.getBrand().getId());
                Classify classify = classifyFallbackFeign.selectClassifyByid(product.getClassify().getId());

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

        int pageProductCount = (int) redisTemplate.opsForValue().get(PAGE_PRODUCT_COUNT+ip);//获取分页前查询的总数

        int x=size; //计算出每一页数量的Max
        int pagecount=(pageProductCount%x==0)?pageProductCount/x:(pageProductCount/x)+1; //页的总数

        jsonObject.put("pagecount",pagecount);



        int curPageGroup=(curPage%5==0)?curPage/5:(curPage/5)+1; //当前页属于第几组
        //1-5为第一组导航 ,6-10为第二组以此类推
        int pageGroup=(pagecount%5==0)?pagecount/5:(pagecount/5)+1; //能够分多少组导航

        jsonObject.put("curPageGroup",curPageGroup);
        jsonObject.put("pageGroup",pageGroup);

        //比如总共15页,5个一组，15%5=0;此时租后一组就为5个
        int odd=(pagecount%5==0)?5:pagecount%5; //求最后一组有多少页
        jsonObject.put("odd",odd);


        return JSON.toJSONString(jsonObject);
    }



}
