package com.boot.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 游政杰
 * @date 2021/11/5 21:50
 */
@Controller
@Api("客户端首页 web api")
@Slf4j
@CrossOrigin
@RequestMapping(path = "/web")
public class IndexController {


    @RequestMapping(path = "/")
    public String index()
    {
        return "client/index";
    }

    @RequestMapping(path = "/view/homePage")
    public String homePage()
    {
        return "client/view/home";
    }


    @RequestMapping(path = "/view/homeMain")
    public String homeMain()
    {
        return "client/view/page/home.main";
    }
    @RequestMapping(path = "/view/homeHomeUserLogin")
    public String homeUserLogin()
    {
        return "client/view/page/home.userLogin";
    }

    @RequestMapping(path = "/view/homeProductDetail")
    public String homeProductDetail()
    {
        return "client/view/page/home.product.detail";
    }
    @RequestMapping(path = "/view/homeSearch")
    public String homeSearch()
    {
        return "client/view/page/home.search";
    }
    @RequestMapping(path = "/view/page404")
    public String page404()
    {
        return "client/view/404";
    }

    @RequestMapping(path = "/view/buyNow")
    public String buyNow()
    {
        return "client/view/buyNow";
    }


}
