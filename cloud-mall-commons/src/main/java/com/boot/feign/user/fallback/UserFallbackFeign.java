package com.boot.feign.user.fallback;


import com.alibaba.fastjson.JSONObject;
import com.boot.data.CommonResult;
import com.boot.feign.system.fallback.impl.ImgFallbackFeignImpl;
import com.boot.feign.user.fallback.impl.UserFallbackFeignImpl;
import com.boot.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 游政杰
 */
@Component
@FeignClient(value = "cloud-mall-user",fallback = UserFallbackFeignImpl.class)
public interface UserFallbackFeign {

    //通过用户名查询密码
    @ResponseBody
    @GetMapping(path = "/feign/user/selectPasswordByuserName/{username}")
    public String selectPasswordByuserName(@PathVariable("username") String username);


    @ResponseBody
    @GetMapping(path = "/feign/user/selectUserIdByName/{username}")
    public long selectUserIdByName(@PathVariable("username") String username);

    //根据用户id查询余额
    @ResponseBody
    @GetMapping(path = "/feign/user/selectUserMoneyByUserId/{userid}")
    public BigDecimal selectUserMoneyByUserId(@PathVariable("userid") long userid);

    @ResponseBody
    @PostMapping(path = "/feign/user/register")
    public CommonResult<User> registerUser(@RequestBody User user);

    //查询用户数量
    @ResponseBody
    @GetMapping(path = "/feign/user/selectUserCount")
    public int selectUserCount();

    @ResponseBody
    @GetMapping(path = "/feign/user/selectAllUserInfo/{page}/{limit}")
    public List<User> selectAllUserInfo(@PathVariable("page") int page,
                                        @PathVariable("limit") int limit);

    @ResponseBody
    @GetMapping(path = "/feign/user/selectUserInfoById/{userid}")
    public User selectUserInfoById(@PathVariable("userid") long userid);
}
