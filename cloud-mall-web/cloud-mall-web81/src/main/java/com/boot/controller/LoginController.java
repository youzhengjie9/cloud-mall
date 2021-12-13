package com.boot.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(path = "/web/login")
@Api("登录服务 web api")
public class LoginController {

    @GetMapping(path = "/toLoginPage")
    public String toLoginPage()
    {

        return "comm/login";
    }

    @RequestMapping(path = "/login")
    public void login(String username, String password, String code,String remember, HttpServletResponse response, HttpServletRequest request) throws IOException {


        response.sendRedirect("http://localhost:80/web/index/"); //重定向到首页
    }

    @RequestMapping(path = "/LoginfailPage")
    public String failPage(Model model) {

        return "comm/login";
    }

    @GetMapping(path = "/toemailTemplate")
    public String toemailTemplate()
    {

        return "comm/emailTemplate";
    }

}
