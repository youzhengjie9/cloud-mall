package com.boot.controller;


import com.boot.annotation.Visitor;
import com.boot.feign.system.fallback.ImgFallbackFeign;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping("/admin")
@Api(value = "后台管理控制器")
@Slf4j
public class AdminController {

    @Autowired
    private SpringSecurityUtil springSecurityUtil;
    @Autowired
    private RedisTemplate redisTemplate;


    @Visitor(desc = "进入后台界面")
    @GetMapping(path = "/")
    @ApiOperation(value = "去后台管理界面", notes = "以/作为路径进入")
    public String toAdmin() {

        return "back/index";
    }

    @GetMapping(path = "/nologin")
    public String nologin(){

        return "error/no_login";
    }



}