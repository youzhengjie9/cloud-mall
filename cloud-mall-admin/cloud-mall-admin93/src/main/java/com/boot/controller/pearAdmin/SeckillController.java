package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.seckill.fallback.SeckillFallbackFeign;
import com.boot.feign.seckill.notfallback.SeckillFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.Seckill;
import com.boot.utils.SnowId;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @PostMapping(path = "/add/seckill")
    public String addSeckill(Seckill seckill) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            seckillFeign.insertSeckill(seckill);
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


}
