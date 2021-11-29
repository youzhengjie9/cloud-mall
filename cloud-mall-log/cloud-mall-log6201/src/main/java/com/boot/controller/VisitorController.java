package com.boot.controller;


import com.boot.pojo.Visitor;
import com.boot.service.VisitorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(path = "/feign/visitor")
@Api("访问接口日志Api")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @ResponseBody
    @PostMapping(path = "/insertVisitor")
    public String insertVisitor(@RequestBody Visitor visitor){

        visitorService.insertVisitor(visitor);

        return "success";
    }

    @ResponseBody
    @GetMapping(path = "/selectVisitorBylimit/{page}/{size}")
    public List<Visitor> selectVisitorBylimit(@PathVariable("page") int page,
                                              @PathVariable("size") int size){

        List<Visitor> visitors = visitorService.selectVisitorBylimit(page, size);

        return visitors;
    }

    @ResponseBody
    @GetMapping(path = "/selectVisitorCount")
    public int selectVisitorCount(){

        int count = visitorService.selectVisitorCount();

        return count;
    }


}
