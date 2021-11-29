package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.pojo.TimeCalc;
import com.boot.service.TimeCalcService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/timecalc")
@Api("耗时接口监控Api")
public class TimeCalcController {

    @Autowired
    private TimeCalcService timeCalcService;

    @ResponseBody
    @PostMapping(path = "/insertTimeCalc")
    public String insertTimeCalc(@RequestBody TimeCalc timeCalc){

        timeCalcService.insertTimeCalc(timeCalc);
        return "success";
    }
    //分页查询
    @ResponseBody
    @GetMapping(path = "/selectTimeCalcBylimit/{page}/{size}")
    public List<TimeCalc> selectTimeCalcBylimit(@PathVariable("page") int page,
                                                @PathVariable("size") int size){

        return timeCalcService.selectTimeCalcBylimit(page, size);
    }

    //查询接口监控记录总数量
    @ResponseBody
    @GetMapping(path = "/selectTimeCalcCount")
    public int selectTimeCalcCount(){

        int count = timeCalcService.selectTimeCalcCount();

        return count;
    }

    //根据uri分页查询
    @ResponseBody
    @PostMapping(path = "/selectTimeCalcByUriLimit")
    public List<TimeCalc> selectTimeCalcByUriLimit(@RequestBody JSONObject jsonObject){

        String uri = (String) jsonObject.get("uri");
        int page = (int) jsonObject.get("page");
        int size = (int) jsonObject.get("size");

        return timeCalcService.selectTimeCalcByUriLimit(uri, page, size);
    }

    //根据uri查询数量
    @ResponseBody
    @PostMapping(path = "/selectTimeCalcCountBylimit")
    public int selectTimeCalcCountBylimit(@RequestBody JSONObject jsonObject){

        String uri = (String) jsonObject.get("uri");
        return timeCalcService.selectTimeCalcCountBylimit(uri);
    }



}
