package com.boot.feign.user.notFallback;

import com.alibaba.fastjson.JSONObject;
import com.boot.data.CommonResult;
import com.boot.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-user")
public interface UserFeign {


    //减余额
    @ResponseBody
    @GetMapping(path = "/feign/user/decrMoneyByUserId/{userid}/{money}")
    public CommonResult<User> decrMoneyByUserId(@PathVariable("userid") long userid,
                                                @PathVariable("money") String money);

    //加余额
    @ResponseBody
    @GetMapping(path = "/feign/user/incrMoneyByUserId/{userid}/{money}")
    public CommonResult<User> incrMoneyByUserId(@PathVariable("userid") long userid,
                                                @PathVariable("money") String money);

    //修改是否生效
    @ResponseBody
    @GetMapping(path = "/feign/user/updateValid/{userid}/{valid}")
    public String updateValid(@PathVariable("userid") long userid,
                              @PathVariable("valid") int valid);

    //修改用户名和权限
    @ResponseBody
    @GetMapping(path = "/feign/user/updateUserName/{id}/{userName}/{authorityId}")
    public String modifyUserNameAndAuthority(@PathVariable("id") String id,
                                             @PathVariable("userName") String userName,
                                             @PathVariable("authorityId") String authorityId);

    @ResponseBody
    @GetMapping(path = "/feign/user/deleteUserById/{id}")
    public String deleteUserById(@PathVariable("id") long id);
}
