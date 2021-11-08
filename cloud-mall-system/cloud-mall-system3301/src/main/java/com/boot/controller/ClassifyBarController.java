package com.boot.controller;

import com.boot.pojo.ClassifyBar;
import com.boot.service.ClassifyBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/classifybar")
public class ClassifyBarController {

    @Autowired
    private ClassifyBarService classifyBarService;

    @ResponseBody
    @GetMapping(path = "/selectAllClassifyBar")
    public List<ClassifyBar> selectAllClassifyBar(){

        return classifyBarService.selectAllClassifyBar();
    }



}



