package com.boot.controller;

import com.boot.pojo.ClassifyBar;
import com.boot.service.ClassifyBarService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/classifybar")
@Api("分类条服务api")
public class ClassifyBarController {

    @Autowired
    private ClassifyBarService classifyBarService;

    @ResponseBody
    @GetMapping(path = "/selectAllClassifyBar")
    public List<ClassifyBar> selectAllClassifyBar(){

        return classifyBarService.selectAllClassifyBar();
    }



}



