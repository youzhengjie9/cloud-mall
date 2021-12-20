package com.boot.controller;

import com.boot.pojo.Product;
import com.boot.pojo.Seckill;
import com.boot.service.SeckillSearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/feign/search")
@Api("秒杀搜索服务api")
public class SeckillSearchController {

    @Autowired
    private SeckillSearchService seckillSearchService;

//    创建秒杀商品索引
//    PUT cloud-mall-seckill
//    {
//        "mappings":{
//        "properties":{
//            "seckillName":{
//                "type":"text",
//                        "analyzer":"standard"
//            },
//            "seckillNumber":{
//                "type":"integer"
//            },
//            "price":{
//                "type":"double"
//            },
//            "img":{
//                "type":"keyword"
//            },
//            "limitCount":{
//                "type":"integer"
//            },
//            "startTime":{
//                "type":"keyword"
//            },
//            "endTime":{
//                "type":"keyword"
//            },
//            "createTime":{
//                "type":"keyword"
//            },
//            "userid":{
//                "type":"keyword"
//            }
//        }
//
//    }
//
//    }

    //秒杀搜索服务初始化
    @ResponseBody
    @GetMapping(path = "/initSeckillSearch")
    public String initSeckillSearch()
    {
        try{
            seckillSearchService.initSeckillSearch();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }

    }

    @ResponseBody
    @GetMapping(path = "/searchAllSeckill")
    public List<Seckill> searchAllSeckill(@RequestParam("text") String text,
                                          @RequestParam("from")int from,
                                          @RequestParam("size")int size,
                                          @RequestParam("ip") String ip) throws IOException
    {

        List<Seckill> seckills = seckillSearchService.searchAllSeckill(text, from, size,ip);

        return seckills;
    }


    //专门把数据查询给秒杀详情，所以只查询必须要的数据
    @ResponseBody
    @GetMapping(path = "/searchSeckilltoDetailByseckillId/{seckillId}")
    public Seckill searchSeckilltoDetailByseckillId(@PathVariable("seckillId") long seckillId) throws IOException{

        return seckillSearchService.searchSeckilltoDetailByseckillId(seckillId);
    }




}
