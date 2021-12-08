package com.boot.feign.system.fallback;

import com.boot.feign.system.fallback.impl.ClassifyBarFallbackFeignImpl;
import com.boot.feign.system.fallback.impl.RechargeCardFallbackFeignImpl;
import com.boot.pojo.ClassifyBar;
import com.boot.pojo.RechargeCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-system",fallback = RechargeCardFallbackFeignImpl.class)
public interface RechargeCardFallbackFeign {


    //根据卡号和密码查询卡
    @ResponseBody
    @GetMapping(path = "/feign/rechargeCard/selectOneRechargeCard/{cardNumber}/{password}")
    public RechargeCard selectOneRechargeCard(@PathVariable("cardNumber") String cardNumber,
                                              @PathVariable("password") long password);


    //充值的核心方法
    @ResponseBody
    @PostMapping(path = "/feign/rechargeCard/recharge")
    public String recharge(@RequestBody RechargeCard rechargeCard);

}
