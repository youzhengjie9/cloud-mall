package com.boot.feign.user.fallback;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 游政杰
 */
public interface UserFallbackFeign {



    //通过用户名查询密码
    @ResponseBody
    @GetMapping(path = "/feign/user/selectPasswordByuserName")
    public String selectPasswordByuserName(String username);










}
