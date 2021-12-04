package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.system.fallback.SlideShowFallbackFeign;
import com.boot.feign.system.notfallback.SlideShowFeign;
import com.boot.pojo.SlideShow;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("轮播图控制器")
public class SlideShowController {


    @Autowired
    private SlideShowFallbackFeign slideShowFallbackFeign;

    @Autowired
    private SlideShowFeign slideShowFeign;

    @RequestMapping(path = "/toSlideShow")
    public String toSlideShow()
    {

        return "back/slideshow_list";
    }


    @ResponseBody
    @GetMapping(path = "/slideShowData")
    public String slideShowData(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "limit", defaultValue = "5") int limit){

        page=limit*(page-1);

        layuiData<SlideShow> layuiData = new layuiData<>();

        List<SlideShow> slideShows = slideShowFallbackFeign.selectAllSlideShowByLimit(page, limit);

        int count = slideShowFallbackFeign.selectSlideShowCount();
        layuiData.setCount(count);
        layuiData.setMsg("");
        layuiData.setData(slideShows);
        layuiData.setCode(0);

        return JSON.toJSONString(layuiData);
    }
    @RequestMapping(path = "/toEditSlideShow")
    public String toEditSlideShow(String id,Model model)
    {

        model.addAttribute("id",id);
        return "back/module/editSlideShow";
    }

    @ResponseBody
    @PostMapping(path = "/updateSort")
    public String updateSort(long id,int sort)
    {

        layuiJSON layuiJSON = new layuiJSON();

        try{
            slideShowFeign.updateSort(id,sort);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("修改成功");
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("修改失败");
            return JSON.toJSONString(layuiJSON);
        }

    }

    @ResponseBody
    @GetMapping(path = "/deleteSlideShow/{id}")
    public String deleteSlideShow(@PathVariable("id") String id)
    {

        layuiJSON layuiJSON = new layuiJSON();

        try{
            slideShowFeign.deleteSlideShow(Long.parseLong(id));
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("删除成功");
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("删除失败");
            return JSON.toJSONString(layuiJSON);
        }

    }

    @ResponseBody
    @GetMapping(path = "/batchDeleteSlideShow/{checkIds}")
    public String batchDeleteSlideShow(@PathVariable("checkIds") String checkIds)
    {

        layuiJSON layuiJSON = new layuiJSON();

        try{
            String[] split = checkIds.split(",");
            long [] arr=new long[split.length];

            for (int i = 0; i < split.length; i++) {
                arr[i]=Long.parseLong(split[i]);
            }
            slideShowFeign.batchDeleteSlideShow(arr);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("删除成功");
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("删除失败");
            return JSON.toJSONString(layuiJSON);
        }

    }

    @RequestMapping(path = "/toAddSlideShow")
    public String toAddSlideShow()
    {
        return "back/module/addSlideShow";
    }



}
