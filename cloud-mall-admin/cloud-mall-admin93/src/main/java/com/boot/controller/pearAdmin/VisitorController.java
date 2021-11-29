package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.constant.ResultCode;
import com.boot.data.ResponseJSON;
import com.boot.data.layuiData;
import com.boot.feign.log.fallback.VisitorFallbackFeign;
import com.boot.pojo.Visitor;
import com.boot.utils.IpToAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 游政杰
 *
 */
@Controller
@RequestMapping(path = "/pear")
@CrossOrigin
public class VisitorController {

    @Autowired
    private VisitorFallbackFeign visitorFallbackFeign;

    @com.boot.annotation.Visitor(desc = "进入访客记录")
    @Operation("进入访客记录")
    @GetMapping(path = "/toVisitorList")
    public String toVisitorList()
    {

        return "back/visitor_list";
    }

    @ResponseBody
    @RequestMapping(path = "/visitorData")
    public String visitorData(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "limit", defaultValue = "10") int limit){

        page=limit*(page-1);

        layuiData<Visitor> visitorlayuiData = new layuiData<>();

        List<Visitor> visitors = visitorFallbackFeign.selectVisitorBylimit(page, limit);

        int count = visitorFallbackFeign.selectVisitorCount();
        visitorlayuiData.setCode(0);
        visitorlayuiData.setCount(count);
        visitorlayuiData.setMsg("");
        visitorlayuiData.setData(visitors);

        return JSON.toJSONString(visitorlayuiData);
    }


    @RequestMapping(path = "/searchIpaddress")
    @ResponseBody
    public String searchIpAddress(String ip1, String ip2, String ip3, String ip4) {
        String ip = ip1 + "." + ip2 + "." + ip3 + "." + ip4; //真正的ip
        String cityInfo = IpToAddressUtil.getCityInfo(ip);
        ResponseJSON responseJSON = new ResponseJSON();
        responseJSON.setData(cityInfo);
        responseJSON.setResult(ResultCode.SUCCESS);
        return JSON.toJSONString(responseJSON);
    }


}
