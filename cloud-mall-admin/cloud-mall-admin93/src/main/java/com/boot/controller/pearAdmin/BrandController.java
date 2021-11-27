package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.product.fallback.BrandFallbackFeign;
import com.boot.feign.product.notFallback.BrandFeign;
import com.boot.pojo.Brand;
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

/** @author 游政杰 */
@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("品牌后台控制器")
public class BrandController {

  @Autowired private SpringSecurityUtil springSecurityUtil;

  @Autowired private BrandFallbackFeign brandFallbackFeign;

  @Autowired private BrandFeign brandFeign;

  @Operation("进入品牌界面")
  @RequestMapping(path = "/toBrandManager")
  public String toBrandManager() {

    return "back/brand_list";
  }

  @ResponseBody
  @RequestMapping(path = "/brandData")
  public String brandData(
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "limit", defaultValue = "6") int limit,
      @RequestParam(value = "title", defaultValue = "") String title) {
    if (StringUtils.isBlank(title)) {
      layuiData<Brand> layuiData = new layuiData<>();
      PageHelper.startPage(page, limit);
      List<Brand> brandList = brandFallbackFeign.selectAllBrand();
      int count = brandFallbackFeign.selectBrandCount();
      layuiData.setCode(0);
      layuiData.setMsg("");
      layuiData.setCount(count);
      layuiData.setData(brandList);
      return JSON.toJSONString(layuiData);
    } else {

      layuiData<Brand> layuiData = new layuiData<>();
      PageHelper.startPage(page, limit);
      List<Brand> brandList = brandFallbackFeign.selectBrandByName(title);
      int count = brandFallbackFeign.selectBrandCountByName(title);
      layuiData.setCode(0);
      layuiData.setMsg("");
      layuiData.setCount(count);
      layuiData.setData(brandList);
      return JSON.toJSONString(layuiData);
    }
  }

  @RequestMapping(path = "/addBrandPage")
  public String addBrandPage() {

    return "back/module/addBrand";
  }

  @ResponseBody
  @PostMapping(path = "/add/brand")
  public String addBrand(String brandName) {

    layuiJSON layuiJSON = new layuiJSON();
    try {

      Brand brand = new Brand();
      brand.setId(SnowId.nextId());
      brand.setBrandName(brandName);
      // 添加
      brandFeign.insertBrand(brand);

      layuiJSON.setSuccess(true);
      layuiJSON.setMsg("添加品牌成功");
      return JSON.toJSONString(layuiJSON);
    } catch (Exception e) {
      e.printStackTrace();
      layuiJSON.setSuccess(false);
      layuiJSON.setMsg("添加品牌失败");
      return JSON.toJSONString(layuiJSON);
    }
  }

  @RequestMapping(path = "/modifyBrandPage")
  public String modifyBrandPage(String id, Model model) {

    model.addAttribute("id", id);

    return "back/module/editBrand";
  }

  @ResponseBody
  @PostMapping(path = "/modify/brand")
  public String modifyBrand(String id, String brandName) {

    layuiJSON layuiJSON = new layuiJSON();
    try {
      Brand brand = new Brand();
      brand.setId(Long.parseLong(id));
      brand.setBrandName(brandName);
      brandFeign.updateBrandName(brand);
      layuiJSON.setSuccess(true);
      layuiJSON.setMsg("修改品牌成功");
      return JSON.toJSONString(layuiJSON);
    } catch (Exception e) {
      e.printStackTrace();
      layuiJSON.setSuccess(false);
      layuiJSON.setMsg("修改品牌失败");
      return JSON.toJSONString(layuiJSON);
    }
  }

  @Operation("删除品牌")
  @ResponseBody
  @RequestMapping(path = "/deleteBrand/{brandId}")
  public String deleteBrand(
      @PathVariable("brandId") String brandId, HttpSession session, HttpServletRequest request) {

    layuiJSON layuiJSON = new layuiJSON();
    try {
      long bid = Long.parseLong(brandId);

      // 删除
      brandFeign.deleteBrand(bid);

      String username = springSecurityUtil.currentUser(session);
      java.util.Date date = new java.util.Date();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String time = simpleDateFormat.format(date);
      String ipAddr = IpUtils.getIpAddr(request);
      log.debug(time + "   用户名：" + username + "删除品牌成功，删除的品牌id为：" + bid + ",ip为：" + ipAddr);
      layuiJSON.setMsg("删除品牌成功");
      layuiJSON.setSuccess(true);
    } catch (Exception e) {
      e.printStackTrace();
      layuiJSON.setMsg("删除品牌失败");
      layuiJSON.setSuccess(false);
    }

    return JSON.toJSONString(layuiJSON);
  }

  @Operation("批量删除品牌")
  @ResponseBody
  @RequestMapping(path = "/batchDeleteBrand/{checkIds}")
  public String batchDeleteBrand(@PathVariable("checkIds") String checkIds) {

    layuiJSON layuiJSON = new layuiJSON();
    try {
      String[] split = checkIds.split(",");
      long[] arr = new long[split.length];

      for (int i = 0; i < split.length; i++) {
        arr[i] = Long.parseLong(split[i]);
      }
      brandFeign.batchDeleteBrand(arr);

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
