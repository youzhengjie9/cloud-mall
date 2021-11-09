package com.boot.controller;

import com.boot.pojo.VersionInfo;
import com.boot.service.VersionInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/feign/versioninfo")
@Api("商品具体版本服务api")
public class VersionInfoController {

    @Autowired
    private VersionInfoService versionInfoService;


    @ResponseBody
    @GetMapping(path = "/selectVersionInfoByPid/{pid}")
    public List<VersionInfo> selectVersionInfoByPid(@PathVariable("pid") long pid)
    {
        return versionInfoService.selectVersionInfoByPid(pid);
    }

    @ResponseBody
    @GetMapping(path = "/selectAllVersionInfo")
    public List<VersionInfo> selectAllVersionInfo()
    {
        return versionInfoService.selectAllVersionInfo();
    }


}
