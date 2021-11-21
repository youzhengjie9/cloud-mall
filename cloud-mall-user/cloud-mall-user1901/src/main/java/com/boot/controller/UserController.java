package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.pojo.User;
import com.boot.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    //根据用户id查询余额
    @ResponseBody
    @GetMapping(path = "/selectUserMoneyByUserId/{userid}")
    public BigDecimal selectUserMoneyByUserId(@PathVariable("userid") long userid){

        BigDecimal bigDecimal = userService.selectUserMoneyByUserId(userid);

        return bigDecimal;
    }

    //减余额
    @ResponseBody
    @GetMapping(path = "/decrMoneyByUserId/{userid}/{money}")
    public CommonResult<User> decrMoneyByUserId(@PathVariable("userid") long userid,
                                                @PathVariable("money") String money){

        CommonResult<User> commonResult = new CommonResult<>();
        commonResult.setCode(ResultCode.FAILURE);

//        Long userid = (Long) jsonObject.get("userid");
        BigDecimal total= new BigDecimal(money);

        userService.decrMoneyByUserId(userid,total);

        commonResult.setCode(ResultCode.SUCCESS);

        return commonResult;
    }


    @ResponseBody
    @PostMapping(path = "/register")
    public CommonResult<User> registerUser(@RequestBody User user){
        CommonResult<User> commonResult = new CommonResult<>();
        try {
            userService.register(user);
            return commonResult;
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(ResultCode.FAILURE);
            return commonResult;
        }
    }
}
