package com.boot.controller;

import com.boot.feign.product.fallback.VersionInfoFallbackFeign;
import com.boot.pojo.VersionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/versionInfo")
public class VersionInfoController {

    @Autowired
    private VersionInfoFallbackFeign versionInfoFallbackFeign;

    @ResponseBody
    @GetMapping(path = "/selectVersionInfoByPid/{pid}")
    public List<VersionInfo> selectVersionInfoByPid(@PathVariable("pid") long pid)
    {
        return versionInfoFallbackFeign.selectVersionInfoByPid(pid);
    }

}
