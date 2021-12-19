package com.boot.controller;

import com.boot.feign.search.fallback.SeckillSearchFallbackFeign;
import com.boot.pojo.Seckill;
import com.boot.utils.IpUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/web/seckill")
@Api("秒杀服务 web api")
public class SeckillController {

    @Autowired
    private SeckillSearchFallbackFeign seckillSearchFallbackFeign;

    private final int from=0; //分页起始，从id=from+1 开始

    private final int size=15;//分页大小

    @GetMapping(path = "/toSearchSeckillPage")
    public String toSearchSeckillPage(Model model, String text, HttpServletRequest request) throws IOException {

        if(text==null||text=="")
        {
            text="^";
        }
        String ipAddr = IpUtils.getIpAddr(request);
        List<Seckill> seckills = seckillSearchFallbackFeign.searchAllSeckill(text, from, size,ipAddr);
        model.addAttribute("seckills",seckills);

        return "client/view/newpage/seckill";
    }



}
