package com.boot.feign.user.fallback;

import com.boot.feign.user.fallback.impl.UserDetailFallbackFeignImpl;
import com.boot.pojo.UserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-user",fallback = UserDetailFallbackFeignImpl.class)
public interface UserDetailFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/userDetail/selectUserDetail/{userid}")
    public UserDetail selectUserDetail(@PathVariable("userid") long userid);


}
