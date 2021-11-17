package com.boot.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 游政杰
 */
@Controller
@Api("注销 web api")
@RequestMapping(path = "/web/logout")
public class LogoutController {


    @RequestMapping(path = "/logout")
    public void logout(HttpServletResponse response) throws IOException {

        response.sendRedirect("http://localhost:80/web/index/");
    }


}
