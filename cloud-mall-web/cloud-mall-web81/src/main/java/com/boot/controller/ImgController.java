package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.feign.system.fallback.ImgFallbackFeign;
import com.boot.pojo.RecommandImg;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/web/img")
@Api("图片服务 web api")
public class ImgController {

    @Autowired
    private ImgFallbackFeign imgFallbackFeign;



//    查询明星单品和为你推荐
    @ResponseBody
    @GetMapping(path = "/selectmxdpAndwntj",produces = "application/json; charset=utf-8")
    public String selectmxdp()
    {
        List<RecommandImg> mxdp = imgFallbackFeign.selectMxdp();
        List<RecommandImg> Wntj = imgFallbackFeign.selectWntj();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("mxdp",mxdp);
        jsonObject.put("wntj",Wntj);
        return jsonObject.toJSONString();
    }


}
