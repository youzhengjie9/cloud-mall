package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.data.layuiData;
import com.boot.feign.product.fallback.BrandFallbackFeign;
import com.boot.pojo.Brand;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/pear")
@Api("品牌后台控制器")
public class BrandController {

    @Autowired
    private BrandFallbackFeign brandFallbackFeign;

    @Operation("进入品牌界面")
    @RequestMapping(path = "/toBrandManager")
    public String toBrandManager()
    {


        return "back/brand_list";

    }
    @ResponseBody
    @RequestMapping(path = "/brandData")
    public String brandData(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "limit", defaultValue = "6") int limit,
                           @RequestParam(value = "title",defaultValue = "")String title)
    {
        if(StringUtils.isBlank(title)){
            layuiData<Brand> linklayuiData = new layuiData<>();
            PageHelper.startPage(page,limit);
            List<Brand> brandList = brandFallbackFeign.selectAllBrand();
            int count = brandFallbackFeign.selectBrandCount();
            linklayuiData.setCode(0);
            linklayuiData.setMsg("");
            linklayuiData.setCount(count);
            linklayuiData.setData(brandList);
            return JSON.toJSONString(linklayuiData);
        }else {

            layuiData<Brand> linklayuiData = new layuiData<>();
            PageHelper.startPage(page,limit);
            List<Brand> brandList = brandFallbackFeign.selectBrandByName(title);
            int count = brandFallbackFeign.selectBrandCountByName(title);
            linklayuiData.setCode(0);
            linklayuiData.setMsg("");
            linklayuiData.setCount(count);
            linklayuiData.setData(brandList);
            return JSON.toJSONString(linklayuiData);

        }

    }


}
