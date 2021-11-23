package com.boot.controller;


import com.boot.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/feign/menu")
@Api("菜单Api")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @GetMapping(path = "/selectMenuDataByAuthority")
    public String selectMenuDataByAuthority(@RequestParam("authority") int authority){

        String menuJson = menuService.selectMenuDataByAuthority(authority);

        return menuJson;
    }


}
