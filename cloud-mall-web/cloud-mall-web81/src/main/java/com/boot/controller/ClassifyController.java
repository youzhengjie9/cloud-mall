package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.feign.product.fallback.ClassifyFallbackFeign;
import com.boot.pojo.Classify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/Classify")
public class ClassifyController {

    @Autowired
    private ClassifyFallbackFeign classifyFallbackFeign;


    //produces = "application/json; charset=utf-8"，解决返回JSON乱码
    @ResponseBody
    @GetMapping(path = "/selectAllClassify",produces = "application/json; charset=utf-8")
    public String selectAllClassify()
    {
        List<Classify> classifies = classifyFallbackFeign.selectAllClassify();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("classify",classifies);
        return jsonObject.toJSONString();
    }



}
