package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.feign.system.fallback.SlideShowFallbackFeign;
import com.boot.pojo.SlideShow;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/web/slideshow")
@Api("轮播图服务 web api")
public class SlideShowController {

    @Autowired
    private SlideShowFallbackFeign slideShowFallbackFeign;

    @ResponseBody
    @GetMapping(path = "/selectSlideShow",produces = "application/json; charset=utf-8")
    public String selectSlideShow(){
        JSONObject jsonObject = new JSONObject();
        List<SlideShow> slideShows = slideShowFallbackFeign.selectSlideShow();

        jsonObject.put("focusImg",slideShows);

        return jsonObject.toJSONString();
    }
}
