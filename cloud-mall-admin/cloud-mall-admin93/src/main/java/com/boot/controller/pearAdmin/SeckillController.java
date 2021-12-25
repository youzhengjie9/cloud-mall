package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.controller.config.FastDFSClientWrapper;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.search.notfallback.SeckillSearchFeign;
import com.boot.feign.seckill.fallback.SeckillFallbackFeign;
import com.boot.feign.seckill.notfallback.SeckillFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.Seckill;
import com.boot.pojo.User;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("秒杀后台控制器")
public class SeckillController {

    @Autowired
    private SeckillFallbackFeign seckillFallbackFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private SeckillSearchFeign seckillSearchFeign;

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper; //fastdfs

    @Autowired
    private SeckillFeign seckillFeign;

    @RequestMapping(path = "/toSeckill")
    public String toSeckill(){

        return "back/seckill_list";
    }

    @ResponseBody
    @RequestMapping(path = "/seckillData")
    public String seckillData(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "6") int limit,
            @RequestParam(value = "title", defaultValue = "") String title) {
        page=limit*(page-1);
        if (StringUtils.isBlank(title)) {
            layuiData<Seckill> layuiData = new layuiData<>();
            List<Seckill> seckills = seckillFallbackFeign.selectAllSeckillByLimit(page, limit);
            int count = seckillFallbackFeign.selectAllSeckillCount();
            layuiData.setCode(0);
            layuiData.setMsg("");
            layuiData.setCount(count);
            layuiData.setData(seckills);
            return JSON.toJSONString(layuiData);
        } else {

            layuiData<Seckill> layuiData = new layuiData<>();
            Seckill seckill = seckillFallbackFeign.selectSeckillByName(title);
            List<Seckill> Seckills = new ArrayList<>();
            Seckills.add(seckill);
            int count = seckillFallbackFeign.selectAllSeckillCountByName(title);
            layuiData.setCode(0);
            layuiData.setMsg("");
            layuiData.setCount(count);
            layuiData.setData(Seckills);
            return JSON.toJSONString(layuiData);
        }
    }

    @RequestMapping(path = "/toAddSeckill")
    public String toAddSeckill()
    {
        return "back/module/addSeckill";
    }

    @Operation("新增秒杀")
    @ResponseBody
    @PostMapping(path = "/add/seckill")
    public String addSeckill(Seckill seckill, HttpSession session, MultipartFile file) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            seckill.setSeckillId(SnowId.nextId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(new java.util.Date().getTime());
            String created = simpleDateFormat.format(date);
            seckill.setCreateTime(created);
            String currentUser = springSecurityUtil.currentUser(session);
            long userid = userFallbackFeign.selectUserIdByName(currentUser);
            User user = new User();
            user.setId(userid);
            seckill.setUser(user);

            String filepath = fastDFSClientWrapper.uploadFile(file); //fastdfs上传文件

            seckill.setImg(filepath);

            seckillFeign.insertSeckill(seckill); //插入到数据库

            seckillSearchFeign.addSeckillToElasticSearchAndRedis(seckill); //放入es和redis

            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("添加秒杀成功");
            return JSON.toJSONString(layuiJSON);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("添加秒杀失败");
            return JSON.toJSONString(layuiJSON);
        }
    }

    @Operation("删除秒杀")
    @ResponseBody
    @GetMapping(path = "/deleteSeckill/{id}")
    public String deleteSeckill(@PathVariable("id") String id)
    {

        layuiJSON layuiJSON = new layuiJSON();

        try{
            Long aLong = Long.valueOf(id);
            seckillFeign.deleteSeckill(aLong);
            seckillSearchFeign.deleteSeckill(aLong);
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

    @Operation("批量删除秒杀")
    @ResponseBody
    @GetMapping(path = "/batchDeleteSeckill{checkIds}")
    public String batchDeleteSeckill(@PathVariable("checkIds") String checkIds)
    {

        layuiJSON layuiJSON = new layuiJSON();

        try{
            String[] split = checkIds.split(",");
            long [] arr=new long[split.length];

            for (int i = 0; i < split.length; i++) {
                arr[i]=Long.parseLong(split[i]);
            }
            seckillFeign.batchDeleteSeckill(arr);
            seckillSearchFeign.batchDeleteSeckill(arr);
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

    @RequestMapping(path = "/toEditSeckill")
    public String toEditSeckill(String seckillName, Model model)
    {
        Seckill seckill = seckillFallbackFeign.selectSeckillByName(seckillName);

        model.addAttribute("seckill",seckill);

        return "back/module/editSeckill";
    }


    @ResponseBody
    @PostMapping(path = "/updateSeckill")
    public String updateSeckill(Seckill seckill,MultipartFile file)
    {

        layuiJSON layuiJSON = new layuiJSON();

        try{

            seckillSearchFeign.updateSeckill(seckill);

            seckillFeign.updateSeckill(seckill);

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

}
