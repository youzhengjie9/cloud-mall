package com.boot.controller;

import com.boot.enums.ResultConstant;
import com.boot.pojo.SlideShow;
import com.boot.service.SlideShowService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/slideshow")
@Api("轮播图服务api")
public class SlideShowController {

    @Autowired
    private SlideShowService slideShowService;

    @ResponseBody
    @GetMapping(path = "/selectSlideShow")
    public List<SlideShow> selectSlideShow(){

        List<SlideShow> slideShows = slideShowService.selectSlideShow();

        return slideShows;
    }

    //查找所有轮播图，并排序和分页
    @ResponseBody
    @GetMapping(path = "/selectAllSlideShowByLimit/{page}/{limit}")
    public List<SlideShow> selectAllSlideShowByLimit(@PathVariable("page") int page,
                                                     @PathVariable("limit") int limit){

        List<SlideShow> slideShows = slideShowService.selectAllSlideShowByLimit(page, limit);

        return slideShows;
    }

    //查询轮播图数量
    @ResponseBody
    @GetMapping(path = "/selectSlideShowCount")
    public int selectSlideShowCount(){

        return slideShowService.selectSlideShowCount();
    }

    //修改排序
    @ResponseBody
    @GetMapping(path = "/updateSort/{id}/{sort}")
    public String updateSort(@PathVariable("id") long id,
                             @PathVariable("sort") int sort){

        slideShowService.updateSort(id, sort);

        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @GetMapping(path = "/deleteSlideShow/{id}")
    public String deleteSlideShow(@PathVariable("id") long id){

        slideShowService.deleteSlideShow(id);

        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @GetMapping(path = "/batchDeleteSlideShow")
    public String batchDeleteSlideShow(@RequestParam("ids")long[] ids){

        slideShowService.batchDeleteSlideShow(ids);
        return ResultConstant.SUCCESS.getCodeStat();
    }



}
