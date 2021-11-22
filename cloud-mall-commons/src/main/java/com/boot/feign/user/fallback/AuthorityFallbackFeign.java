package com.boot.feign.user.fallback;

import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.impl.AuthorityFallbackFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-user",fallback = AuthorityFallbackFeignImpl.class)
public interface AuthorityFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/authority/selectAuthorityNameById/{id}")
    public CommonResult<String> selectAuthorityNameById(@PathVariable("id") int id);



}
