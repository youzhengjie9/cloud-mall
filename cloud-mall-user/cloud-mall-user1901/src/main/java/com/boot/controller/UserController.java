package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.enums.ResultConstant;
import com.boot.pojo.User;
import com.boot.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
    @GetMapping(path = "/selectPasswordByuserName/{username}")
    public String selectPasswordByuserName(@PathVariable("username") String username){

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

    //查询用户数量
    @ResponseBody
    @GetMapping(path = "/selectUserCount")
    public int selectUserCount(){

        int count = userService.selectUserCount();
        return count;
    }



    //加余额
    @ResponseBody
    @GetMapping(path = "/incrMoneyByUserId/{userid}/{money}")
    public CommonResult<User> incrMoneyByUserId(@PathVariable("userid") long userid,
                                                @PathVariable("money") String money){

        CommonResult<User> commonResult = new CommonResult<>();
        commonResult.setCode(ResultCode.FAILURE);

        BigDecimal total= new BigDecimal(money);

        userService.incrMoneyByUserId(userid,total);

        commonResult.setCode(ResultCode.SUCCESS);

        return commonResult;
    }


    @ResponseBody
    @GetMapping(path = "/selectAllUserInfo/{page}/{limit}")
    public List<User> selectAllUserInfo(@PathVariable("page") int page,
                                        @PathVariable("limit") int limit){

    return userService.selectAllUserInfo(page, limit);

    }

    @ResponseBody
    @GetMapping(path = "/selectUserInfoById/{userid}")
    public User selectUserInfoById(@PathVariable("userid") long userid){

        return userService.selectUserInfoById(userid);
    }

    //修改是否生效
    @ResponseBody
    @GetMapping(path = "/updateValid/{userid}/{valid}")
    public String updateValid(@PathVariable("userid") long userid,
                              @PathVariable("valid") int valid){

        userService.updateValid(userid, valid);

        return ResultConstant.SUCCESS.getCodeStat();
    }


    //修改用户名和权限
    @ResponseBody
    @GetMapping(path = "/updateUserName/{id}/{userName}/{authorityId}")
    public String modifyUserNameAndAuthority(@PathVariable("id") String id,
                                             @PathVariable("userName") String userName,
                                             @PathVariable("authorityId") String authorityId){

        userService.modifyUserNameAndAuthority(id, userName, authorityId);

        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @GetMapping(path = "/deleteUserById/{id}")
    public String deleteUserById(@PathVariable("id") long id){


        userService.deleteUserById(id);

        return ResultConstant.SUCCESS.getCodeStat();
    }



}
