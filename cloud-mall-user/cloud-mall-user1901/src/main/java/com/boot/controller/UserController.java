package com.boot.controller;

import com.boot.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/feign/user")
@Api("用户服务api")
public class UserController {

    @Autowired
    private UserService userService;

    //通过用户名查询密码
    @ResponseBody
    @GetMapping(path = "/selectPasswordByuserName")
    public String selectPasswordByuserName(String username){

        return userService.selectPasswordByuserName(username);
    }
    @ResponseBody
    @GetMapping(path = "/selectUserIdByName/{username}")
    public long selectUserIdByName(@PathVariable("username") String username){

        long id = userService.selectUserIdByName(username);
        return id;
    }

}
