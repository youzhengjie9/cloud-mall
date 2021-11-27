package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.data.CommonResult;
import com.boot.pojo.Classify;
import com.boot.service.ClassifyService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @GetMapping(path = "/selectClassifyByPid/{id}")
    public Classify selectClassifyByid(@PathVariable("id") long id){

        Classify classify = classifyService.selectClassifyByid(id);

        return classify;
    }

    @ResponseBody
    @GetMapping(path = "/selectClassifyCount")
    public int selectClassifyCount(){
        int count = classifyService.selectClassifyCount();
        return count;
    }

    @ResponseBody
    @GetMapping(path = "/selectClassifiesByText/{text}")
    public List<Classify> selectClassifiesByText(@PathVariable("text") String text){
        List<Classify> classifyList = classifyService.selectClassifiesByText(text);
        return classifyList;
    }

    @ResponseBody
    @GetMapping(path = "/selectClassifiesCountByText/{text}")
    public int selectClassifiesCountByText(@PathVariable("text") String text){

        return classifyService.selectClassifiesCountByText(text);
    }

    @ResponseBody
    @GetMapping(path = "/navEnable/{id}")
    public int navEnable(@PathVariable("id") long id){

        return classifyService.navEnable(id);
    }
    @ResponseBody
    @GetMapping(path = "/navDisable/{id}")
    public int navDisable(@PathVariable("id") long id){

        return classifyService.navDisable(id);
    }

    //添加分类
    @ResponseBody
    @PostMapping(path = "/insertClassify")
    public String insertClassify(@RequestBody Classify classify){

        classifyService.insertClassify(classify);
        return "success";
    }

    //修改分类
    @ResponseBody
    @PostMapping(path = "/updateClassify")
    public String updateClassify(@RequestBody Classify classify){

        classifyService.updateClassify(classify);
        return "success";
    }

    //删除分类
    @ResponseBody
    @GetMapping(path = "/deleteClassify/{id}")
    public String deleteClassify(@PathVariable("id") long id){

        classifyService.deleteClassify(id);
        return "success";
    }

    //批量删除分类
    @ResponseBody
    @GetMapping(path = "/batchDeleteClassify")
    public String batchDeleteClassify(@RequestParam("ids") long[] ids){

        classifyService.batchDeleteClassify(ids);
        return "success";
    }

}
