package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.data.layuiData;
import com.boot.feign.log.fallback.LoginLogFallbackFeign;
import com.boot.feign.log.fallback.OperationLogFallbackFeign;
import com.boot.pojo.LoginLog;
import com.boot.pojo.OperationLog;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/pear")
@Api("日志控制器")
public class LogController {

    @Autowired
    private LoginLogFallbackFeign loginLogFallbackFeign;

    @Autowired
    private OperationLogFallbackFeign operationLogFallbackFeign;


    @RequestMapping(path = "/toLog")
    public String toLog()
    {

        return "back/log_list";
    }

    @ResponseBody
    @RequestMapping(path = "/log/loginlog")
    public String loginLogData(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "limit", defaultValue = "10") int limit){

        //手动分页
        page=limit*(page-1);

        layuiData<LoginLog> data = new layuiData<>();

        List<LoginLog> loginLogs = loginLogFallbackFeign.selectLoginLogBylimit(page, limit);

        int count = loginLogFallbackFeign.selectLoginLogCount();
        data.setCode(0);
        data.setMsg("");
        data.setData(loginLogs);
        data.setCount(count);
        return JSON.toJSONString(data);
    }

    @ResponseBody
    @RequestMapping(path = "/log/operationLog")
    public String operationLogData(@RequestParam(value = "page",defaultValue = "1") int page,
                                   @RequestParam(value = "limit",defaultValue = "10") int limit){

        //手动分页
        page=limit*(page-1);

        layuiData<OperationLog> data = new layuiData<>();

        List<OperationLog> operationLogs = operationLogFallbackFeign.selectAllOperationLog(page, limit);

        int count = operationLogFallbackFeign.selectOperationCount();

        data.setCode(0);
        data.setMsg("");
        data.setData(operationLogs);
        data.setCount(count);

        return JSON.toJSONString(data);
    }




}
