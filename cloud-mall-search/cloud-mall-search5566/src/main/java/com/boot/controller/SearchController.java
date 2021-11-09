package com.boot.controller;

import com.boot.pojo.Product;
import com.boot.service.SearchService;
import io.swagger.annotations.Api;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
//            "b_id":{
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

    //根绝name进行查询
    @ResponseBody
    @GetMapping(path = "/searchProductByName/{text}")
    public List<Product> searchProductByName(@PathVariable("text") String text) throws IOException {

        SearchHit[] searchHits = searchService.searchProductHitByName(text);

        List<Product> products = searchService.searchProductByHit(searchHits);

        return products;
    }



}
