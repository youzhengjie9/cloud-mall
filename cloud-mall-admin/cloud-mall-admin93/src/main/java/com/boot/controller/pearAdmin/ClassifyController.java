package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.product.fallback.ClassifyFallbackFeign;
import com.boot.feign.product.notFallback.ClassifyFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.Classify;
import com.boot.utils.IpUtils;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("分类后台控制器")
public class ClassifyController {

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private ClassifyFallbackFeign classifyFallbackFeign;

    @Autowired
    private ClassifyFeign classifyFeign;


    @Operation("进入分类界面")
    @RequestMapping(path = "/toClassifyManager")
    public String toClassifyManager()
    {


        return "back/classify_list";
    }

    @ResponseBody
    @RequestMapping(path = "/classifyData")
    public String classifyData(@RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "limit", defaultValue = "6") int limit,
                            @RequestParam(value = "title",defaultValue = "")String title)
    {
        if(StringUtils.isBlank(title)){
            layuiData<Classify> layuiData = new layuiData<>();
            PageHelper.startPage(page,limit);
            List<Classify> classifyList = classifyFallbackFeign.selectAllClassify();
            int count = classifyFallbackFeign.selectClassifyCount();
            layuiData.setCode(0);
            layuiData.setMsg("");
            layuiData.setCount(count);
            layuiData.setData(classifyList);
            return JSON.toJSONString(layuiData);
        }else {

            layuiData<Classify> layuiData = new layuiData<>();
            PageHelper.startPage(page,limit);
            List<Classify> classifyList = classifyFallbackFeign.selectClassifiesByText(title);
            int count = classifyFallbackFeign.selectClassifiesCountByText(title);
            layuiData.setCode(0);
            layuiData.setMsg("");
            layuiData.setCount(count);
            layuiData.setData(classifyList);
            return JSON.toJSONString(layuiData);

        }

    }


    //开启导航
    @ResponseBody
    @GetMapping(path = "/enableNav")
    public String enableNav(String classifyid)
    {
        long id = Long.parseLong(classifyid);
        layuiJSON layuiJSON = new layuiJSON();

        try{
        // 修改
            classifyFeign.navEnable(id);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("开启成功");
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("开启失败");
            return JSON.toJSONString(layuiJSON);
        }


    }

    //关闭导航
    @ResponseBody
    @GetMapping(path = "/disableNav")
    public String disableNav(String classifyid)
    {
        long id = Long.parseLong(classifyid);
        layuiJSON layuiJSON = new layuiJSON();

        try{
            // 修改
            classifyFeign.navDisable(id);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("关闭成功");
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("关闭失败");
            return JSON.toJSONString(layuiJSON);
        }

    }


    @RequestMapping(path = "/addClassifyPage")
    public String addClassifyPage() {

        return "back/module/addClassify";
    }


    @ResponseBody
    @PostMapping(path = "/add/classify")
    public String addClassify(String classifyName,String nav){

        layuiJSON layuiJSON = new layuiJSON();
        try{
            int n = Integer.parseInt(nav);
            Classify classify = new Classify();
            classify.setId(SnowId.nextId());
            classify.setText(classifyName);
            classify.setIsNav((n==1)?1:0);
            // 添加
            classifyFeign.insertClassify(classify);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("添加分类成功");
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("添加分类失败");
            return JSON.toJSONString(layuiJSON);
        }

    }


    @RequestMapping(path = "/modifyClassifyPage")
    public String modifyClassifyPage(String id, Model model) {

        model.addAttribute("id",id);
        long cid = Long.parseLong(id);
        Classify classify = classifyFallbackFeign.selectClassifyByid(cid);
        model.addAttribute("curNav",classify.getIsNav());


        return "back/module/editClassify";
    }


    @ResponseBody
    @PostMapping(path = "/modify/classify")
    public String modifyClassify(String id,String classifyName,String nav){

        layuiJSON layuiJSON = new layuiJSON();
        try{
            int n = Integer.parseInt(nav);
            Classify classify = new Classify();
            classify.setId(Long.parseLong(id));
            classify.setText(classifyName);
            classify.setIsNav((n==1)?1:0);
            classifyFeign.updateClassify(classify);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("修改品牌成功");
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("修改品牌失败");
            return JSON.toJSONString(layuiJSON);
        }

    }


    @Operation("删除分类")
    @ResponseBody
    @RequestMapping(path = "/deleteClassify/{classifyId}")
    public String deleteClassify(
            @PathVariable("classifyId") String classifyId, HttpSession session, HttpServletRequest request) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            long bid = Long.parseLong(classifyId);
            // 删除
            classifyFeign.deleteClassify(bid);
            layuiJSON.setMsg("删除分类成功");
            layuiJSON.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setMsg("删除分类失败");
            layuiJSON.setSuccess(false);
        }

        return JSON.toJSONString(layuiJSON);
    }

    @Operation("批量删除分类")
    @ResponseBody
    @RequestMapping(path = "/batchDeleteClassify/{checkIds}")
    public String batchDeleteClassify(@PathVariable("checkIds") String checkIds) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            String[] split = checkIds.split(",");
            long[] arr = new long[split.length];

            for (int i = 0; i < split.length; i++) {
                arr[i] = Long.parseLong(split[i]);
            }
            classifyFeign.batchDeleteClassify(arr);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("批量删除失败");
        }

        return JSON.toJSONString(layuiJSON);
    }




}
