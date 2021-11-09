package com.boot.controller;

import com.boot.pojo.RecommandImg;
import com.boot.service.MxdpService;
import com.boot.service.WntjService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/img")
@Api("图片服务api")
public class ImgController {

    @Autowired
    private WntjService wntjService;

    @Autowired
    private MxdpService mxdpService;


    @ResponseBody
    @GetMapping(path = "/selectWntj")
    public List<RecommandImg> selectWntj()
    {

        return wntjService.selectWntj();
    }

    @ResponseBody
    @GetMapping(path = "/selectMxdp")
    public List<RecommandImg> selectMxdp()
    {

        return mxdpService.selectMxdp();
    }



}
