package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.annotation.Operation;
import com.boot.data.layuiData;
import com.boot.feign.log.fallback.TimeCalcFallbackFeign;
import com.boot.pojo.TimeCalc;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 游政杰
 */
@Controller("PearMonitorController")
@RequestMapping(path = "/pear")
@Api("监控控制器")
public class MonitorController {

    @Autowired
    private TimeCalcFallbackFeign timeCalcFallbackFeign;

    @Operation("进入接口监控界面")
    @RequestMapping(path = "/monitorInterface")
    public String toMonitorInterface() {

        return "back/monitor_interface";
    }

    @ResponseBody
    @RequestMapping(path = "/monitorInterfaceData")
    public String monitorInterfaceData(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "limit", defaultValue = "10") int limit,
                                       @RequestParam(value = "uri", defaultValue = "") String uri) {

        page=limit*(page-1);

        if (StringUtils.isBlank(uri)) {

            layuiData<TimeCalc> timeCalclayuiData = new layuiData<>();

            List<TimeCalc> timeCalcs = timeCalcFallbackFeign.selectTimeCalcBylimit(page, limit);

            int count = timeCalcFallbackFeign.selectTimeCalcCount();

            timeCalclayuiData.setMsg("");
            timeCalclayuiData.setCode(0);
            timeCalclayuiData.setData(timeCalcs);
            timeCalclayuiData.setCount(count);

            return JSON.toJSONString(timeCalclayuiData);

        } else {

            layuiData<TimeCalc> timeCalclayuiData = new layuiData<>();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uri",uri);
            jsonObject.put("page",page);
            jsonObject.put("size",limit);

            List<TimeCalc> timeCalcs = timeCalcFallbackFeign.selectTimeCalcByUriLimit(jsonObject);
            int count = timeCalcFallbackFeign.selectTimeCalcCountBylimit(jsonObject);

            timeCalclayuiData.setMsg("");
            timeCalclayuiData.setCode(0);
            timeCalclayuiData.setCount(count);
            timeCalclayuiData.setData(timeCalcs);

            return JSON.toJSONString(timeCalclayuiData);
        }

    }


}
