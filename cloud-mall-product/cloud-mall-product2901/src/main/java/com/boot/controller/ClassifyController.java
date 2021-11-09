package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.pojo.Classify;
import com.boot.service.ClassifyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/feign/Classify")
@Api("分类服务api")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    @ResponseBody
    @GetMapping(path = "/selectAllClassify")
    public List<Classify> selectAllClassify()
    {
        //构建json
        List<Classify> classifies = classifyService.selectAllClassify();
        return classifies;
    }




}
