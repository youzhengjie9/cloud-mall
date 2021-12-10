package com.boot.feign.system.notfallback;

import com.boot.pojo.CouponsActivity;
import com.boot.pojo.RechargeCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-system")
public interface CouponsActivityFeign {


    @ResponseBody
    @PostMapping(path = "/feign/couponsActivity/insertCouponsActivity")
    public String insertCouponsActivity(@RequestBody CouponsActivity couponsActivity);



}
