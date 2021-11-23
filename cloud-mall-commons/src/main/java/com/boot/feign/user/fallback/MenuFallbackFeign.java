package com.boot.feign.user.fallback;

import com.boot.feign.user.fallback.impl.MenuFallbackFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-user",fallback = MenuFallbackFeignImpl.class)
public interface MenuFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/menu/selectMenuDataByAuthority")
    public String selectMenuDataByAuthority(@RequestParam("authority") int authority);




}
