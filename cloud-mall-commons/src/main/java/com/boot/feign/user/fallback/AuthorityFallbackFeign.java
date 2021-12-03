package com.boot.feign.user.fallback;

import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.impl.AuthorityFallbackFeignImpl;
import com.boot.pojo.Authority;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-user",fallback = AuthorityFallbackFeignImpl.class)
public interface AuthorityFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/authority/selectAuthorityNameById/{id}")
    public CommonResult<String> selectAuthorityNameById(@PathVariable("id") int id);

    //查找除了当前权限之外的权限
    @ResponseBody
    @GetMapping(path = "/feign/authority/selectAuthorityExcludeCurAuthority/{id}")
    public List<Authority> selectAuthorityExcludeCurAuthority(@PathVariable("id") int id);

}
