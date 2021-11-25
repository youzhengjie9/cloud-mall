package com.boot.controller;

import com.boot.data.CommonResult;
import com.boot.pojo.Product;
import com.boot.service.SearchService;
import io.swagger.annotations.Api;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/feign/search")
@Api("搜索服务api")
public class SearchController {

    @Autowired
    private SearchService searchService;


    //***启动时先执行以下命令创建索引
//    PUT cloud-mall
//    {
//        "mappings":{
//        "properties":{
//            "name":{
//                "type":"text",
//                        "analyzer":"standard"
//            },
//            "price":{
//                "type":"double"
//            },
//            "img":{
//                "type":"keyword"
//            },
//            "number":{
//                "type":"integer"
//            },
//            "fl_id":{
//                "type":"keyword"
//            },
//            "fl_name":{
//                "type":"keyword"
//            },
//            "b_id":{
//                "type":"keyword"
//            },
//            "b_name":{
//                "type":"keyword"
//            },
//            "introduce_img":{
//                "type":"keyword"
//            }
//        }
//
//    }
//
//    }


    //搜索服务初始化
    @ResponseBody
        @GetMapping(path = "/initSearch")
    public String initSearch() throws IOException {

        try {
            searchService.initSearch();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    //根据name进行查询
    @ResponseBody
    @GetMapping(path = "/searchProductByName/{text}")
    public List<Product> searchProductByName(@PathVariable("text") String text) throws IOException {


        SearchHit[] searchHits = searchService.searchProductHitByName(text);

        List<Product> products = searchService.searchProductByHit(text,searchHits);

        return products;
    }

    //查询所有数据并分页
    @ResponseBody
    @GetMapping(path = "/searchAllProductByLimit/{from}/{size}")
    public List<Product> searchAllProductByLimit(@PathVariable("from") int from,
                                                 @PathVariable("size") int size) throws IOException {


        List<Product> products = searchService.searchAllProductByLimit(from, size);

        return products;
    }

    //from size 为分页
    @ResponseBody
    @GetMapping(path = "/searchProductsByCondition")
    public List<Product> searchProductsByCondition(@RequestParam("text") String text,
                                                   @RequestParam("brandid") long brandid,
                                                   @RequestParam("classifyid")long classifyid,
                                                   @RequestParam("from")int from,
                                                   @RequestParam("size")int size) throws IOException{

        List<Product> products = searchService.searchProductsByCondition(text, brandid, classifyid, from, size);

        return products;
    }

    @ResponseBody
    @GetMapping(path = "/searchAllProductsCount")
    public CommonResult<Long> searchAllProductsCount() throws IOException {

        CommonResult<Long> commonResult = new CommonResult<>();
        long count = searchService.searchAllProductsCount();

        commonResult.setObj(count);
        return commonResult;
    }

    //根据商品名搜索商品集合并且分页
    @ResponseBody
    @GetMapping(path = "/searchProductsByNameAndLimit/{from}/{size}/{text}")
    public CommonResult<List<Product>> searchProductsByNameAndLimit(@PathVariable("from") int from ,
                                                      @PathVariable("size") int size ,
                                                      @PathVariable("text") String text) throws IOException{

        CommonResult<List<Product>> commonResult = new CommonResult<>();
        List<Product> products = searchService.searchProductsByNameAndLimit(from, size, text);

        commonResult.setObj(products);

        return commonResult;
    }


    //根据商品名搜索商品的目标数
    @ResponseBody
    @GetMapping(path = "/searchProductCountByName/{text}")
    public CommonResult<Long> searchProductCountByName(@PathVariable("text") String text) throws IOException{

        CommonResult<Long> commonResult = new CommonResult<>();

        long count = searchService.searchProductCountByName(text);

        commonResult.setObj(count);

        return commonResult;
    }

}
