package com.boot.feign.user.notFallback;

import com.boot.pojo.UserAuthority;
import com.boot.pojo.UserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-user")
public interface UserDetailFeign {


    @ResponseBody
    @PostMapping(path = "/feign/userDetail/updateSex")
    public String updateSex(@RequestBody UserDetail userDetail);

    @ResponseBody
    @PostMapping(path = "/feign/userDetail/updateSignature")
    public String updateSignature(@RequestBody UserDetail userDetail);

}
