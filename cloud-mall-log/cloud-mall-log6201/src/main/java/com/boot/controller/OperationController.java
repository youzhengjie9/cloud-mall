package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.boot.data.layuiData;
import com.boot.pojo.OperationLog;
import com.boot.service.OperationService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/operationlog")
@Api("操作日志Api")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @ResponseBody
    @PostMapping(path = "/insertOperationLog")
    public String insertOperationLog(@RequestBody OperationLog operationLog){

        operationService.insertOperationLog(operationLog);

        return "success";
    }


    @ResponseBody
    @GetMapping(path = "/selectOperationLogByLimit")
    public List<OperationLog> selectOperationLogByLimit(@RequestParam("limit")int limit){

        List<OperationLog> operationLogs = operationService.selectOperationLogByLimit(limit);

        return operationLogs;
    }


    @ResponseBody
    @RequestMapping(path = "/operationLogData")
    public String operationLogData(@RequestParam(value = "page",defaultValue = "1") int page,
                                   @RequestParam(value = "limit",defaultValue = "10") int limit){

        layuiData<OperationLog> data = new layuiData<>();

        PageHelper.startPage(page, limit);
        List<OperationLog> OperationLogs = operationService.selectAllOperationLog();

        int count = operationService.selectOperationCount();

        data.setCode(0);
        data.setMsg("");
        data.setData(OperationLogs);
        data.setCount(count);

        return JSON.toJSONString(data);
    }

    @ResponseBody
    @GetMapping(path = "/selectAllOperationLog")
    public List<OperationLog> selectAllOperationLog(@RequestParam("page") int page,
                                                    @RequestParam("limit") int limit){

        PageHelper.startPage(page, limit);
        List<OperationLog> operationLogs = operationService.selectAllOperationLog();

        return operationLogs;
    }
    @ResponseBody
    @GetMapping(path = "/selectOperationCount")
    public int selectOperationCount(){


        int count = operationService.selectOperationCount();

        return count;
    }

}
