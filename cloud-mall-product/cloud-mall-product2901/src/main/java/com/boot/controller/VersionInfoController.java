package com.boot.controller;

import com.boot.data.CommonResult;
import com.boot.pojo.VersionInfo;
import com.boot.service.VersionInfoService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/** @author 游政杰 */
@Controller
@RequestMapping(path = "/feign/versioninfo")
@Api("商品具体版本服务api")
public class VersionInfoController {

  @Autowired private VersionInfoService versionInfoService;

  @ResponseBody
  @GetMapping(path = "/selectVersionInfoByPid/{pid}")
  public List<VersionInfo> selectVersionInfoByPid(@PathVariable("pid") long pid) {
    return versionInfoService.selectVersionInfoByPid(pid);
  }

  @ResponseBody
  @GetMapping(path = "/selectAllVersionInfo")
  public List<VersionInfo> selectAllVersionInfo() {
    return versionInfoService.selectAllVersionInfo();
  }

  @ResponseBody
  @GetMapping(path = "/selectOrderCountBypid/{pid}")
  public int selectOrderCountBypid(@PathVariable("pid") long pid) {

    return versionInfoService.selectOrderCountBypid(pid);
  }

  // 根据商品id和order查询对应的版本信息
  @ResponseBody
  @GetMapping(path = "/selectVersionInfoByPidAndOrder/{pid}/{order}")
  public List<VersionInfo> selectVersionInfoByPidAndOrder(
      @PathVariable("pid") long pid, @PathVariable("order") long order) {

    return versionInfoService.selectVersionInfoByPidAndOrder(pid, order);
  }

  @ResponseBody
  @GetMapping(path = "/selectVersionInfoTitle/{pid}/{order}")
  public String selectVersionInfoTitle(
      @PathVariable("pid") long pid, @PathVariable("order") long order) {

    return versionInfoService.selectVersionInfoTitle(pid, order);
  }

  @ResponseBody
  @GetMapping(path = "/selectVersionInfoDesc/{pid}/{order}")
  public String selectVersionInfoDesc(
      @PathVariable("pid") long pid, @PathVariable("order") long order) {

    return versionInfoService.selectVersionInfoDesc(pid, order);
  }

  // 查价格
  @ResponseBody
  @GetMapping(path = "/selectPriceByversionId/{versionId}")
  public BigDecimal selectPriceByversionId(@PathVariable("versionId") long versionId) {

    return versionInfoService.selectPriceByversionId(versionId);
  }
  //查询版本名称
  @ResponseBody
  @GetMapping(path = "/selectNameByversionId/{versionId}")
  public String selectNameByversionId(@PathVariable("versionId") long versionId){

    return versionInfoService.selectNameByversionId(versionId);
  }

  @ResponseBody
  @GetMapping(path = "/selectVersionInfoByLimit/{page}/{size}")
  public List<VersionInfo> selectVersionInfoByLimit(@PathVariable("page") int page,
                                                    @PathVariable("size") int size){

    return versionInfoService.selectVersionInfoByLimit(page, size);
  }

  @ResponseBody
  @GetMapping(path = "/selectVersionInfoByLimitAndPid/{pid}/{page}/{size}")
  public List<VersionInfo> selectVersionInfoByLimitAndPid(@PathVariable("pid") long pid,
                                                          @PathVariable("page") int page,
                                                          @PathVariable("size") int size){


    return versionInfoService.selectVersionInfoByLimitAndPid(pid,page, size);
  }

  @ResponseBody
  @GetMapping(path = "/selectversionInfoCountByPid/{pid}")
  public int selectversionInfoCountByPid(@PathVariable("pid") long pid){


    return versionInfoService.selectversionInfoCountByPid(pid);

  }
  @ResponseBody
  @GetMapping(path = "/selectAllversionInfoCount")
  public int selectAllversionInfoCount(){


    return versionInfoService.selectAllversionInfoCount();
  }

  @ResponseBody
  @PostMapping(path = "/insertVersionInfo")
  public int insertVersionInfo(@RequestBody VersionInfo versionInfo){

    return versionInfoService.insertVersionInfo(versionInfo);
  }
  @ResponseBody
  @PostMapping(path = "/updateVersionInfo")
  public int updateVersionInfo(@RequestBody VersionInfo versionInfo){


    return versionInfoService.updateVersionInfo(versionInfo);
  }

  @ResponseBody
  @GetMapping(path = "/deleteVersionInfo/{versionId}")
  public int deleteVersionInfo(@PathVariable("versionId") long versionId){

    return versionInfoService.deleteVersionInfo(versionId);
  }

  @ResponseBody
  @GetMapping(path = "/batchDeleteVersionInfo")
  public CommonResult<String> batchDeleteVersionInfo(@RequestParam("ids")long[] ids){

    CommonResult<String> commonResult = new CommonResult<>();
    versionInfoService.batchDeleteVersionInfo(ids);
    return commonResult;
  }

  @ResponseBody
  @GetMapping(path = "/selectVersionByVersionId/{versionId}")
  public VersionInfo selectVersionByVersionId(@PathVariable("versionId") long versionId){


    return versionInfoService.selectVersionByVersionId(versionId);
  }
}
