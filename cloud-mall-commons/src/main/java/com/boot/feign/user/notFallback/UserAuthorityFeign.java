package com.boot.feign.user.notFallback;

import com.boot.pojo.UserAuthority;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-user")
public interface UserAuthorityFeign {

    //修改用户权限
    @ResponseBody
    @PostMapping(path = "/feign/userAuthority/updateUserAuthority")
    public String updateUserAuthority(@RequestBody UserAuthority userAuthority);



}
