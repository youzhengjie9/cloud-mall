package com.boot.feign.user.fallback;


import com.boot.feign.system.fallback.impl.ImgFallbackFeignImpl;
import com.boot.feign.user.fallback.impl.UserFallbackFeignImpl;
import com.boot.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author 游政杰
 */
@Component
@FeignClient(value = "cloud-mall-user",fallback = UserFallbackFeignImpl.class)
public interface UserFallbackFeign {

    //通过用户名查询密码
    @ResponseBody
    @GetMapping(path = "/feign/user/selectPasswordByuserName")
    public String selectPasswordByuserName(String username);


    @ResponseBody
    @GetMapping(path = "/feign/user/selectUserIdByName/{username}")
    public long selectUserIdByName(@PathVariable("username") String username);

    //根据用户id查询余额
    @ResponseBody
    @GetMapping(path = "/feign/user/selectUserMoneyByUserId/{userid}")
    public BigDecimal selectUserMoneyByUserId(@PathVariable("userid") long userid);

}
