package com.boot.feign.user.fallback;

import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.impl.UserAuthorityFallbackFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-user",fallback = UserAuthorityFallbackFeignImpl.class)
public interface UserAuthorityFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/userAuthority/selectAuthorityIdByUserId/{userid}")
    public CommonResult<Integer> selectAuthorityIdByUserId(@PathVariable("userid") long userid);


}
