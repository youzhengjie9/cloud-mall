package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.feign.product.fallback.VersionInfoFallbackFeign;
import com.boot.pojo.Product;
import com.boot.pojo.VersionInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/** @author 游政杰 */
@Controller
@RequestMapping(path = "/web/versionInfo")
@Api("商品具体版本服务 web api")
public class VersionInfoController {

  @Autowired private VersionInfoFallbackFeign versionInfoFallbackFeign;

  @Autowired private ProductFallbackFeign productFallbackFeign;

  @ResponseBody
  @GetMapping(path = "/selectVersionInfoByPid/{pid}")
  public List<VersionInfo> selectVersionInfoByPid(@PathVariable("pid") long pid) {
    return versionInfoFallbackFeign.selectVersionInfoByPid(pid);
  }

  // 查价格
  @ResponseBody
  @GetMapping(path = "/selectPriceByversionId")
  public BigDecimal selectPriceByversionId(
      @RequestParam(value = "skuarr[]", required = true) long[] skuarr,
      @RequestParam(value = "productid", required = true) long productid) {

    Product product = productFallbackFeign.selectProductByPid(productid);
    BigDecimal productPrice = product.getPrice();

    if (skuarr != null && skuarr.length > 0) {
      for (long l : skuarr) {

        BigDecimal versionPrice = versionInfoFallbackFeign.selectPriceByversionId(l);

        productPrice = productPrice.add(versionPrice); // 这里记得要重新赋值
      }
    }

    return productPrice; // 结算成功就返回给前端
  }

  @ResponseBody
  @GetMapping(path = "/selectNameByversionId")
  public JSONObject selectNameByversionId(
      @RequestParam(value = "skuarr[]", required = true) long[] skuarr,
      @RequestParam(value = "productid", required = true) long productid) {

    Product product = productFallbackFeign.selectProductByPid(productid);
      String name = product.getName();

      if (skuarr != null && skuarr.length > 0) {
      for (long l : skuarr) {

          String n = versionInfoFallbackFeign.selectNameByversionId(l);

          name+="  "+n;
      }
    }
    JSONObject jsonObject = new JSONObject();
      jsonObject.put("name",name);

    return jsonObject;

  }
}
