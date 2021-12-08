package com.boot.feign.system.notfallback;

import com.boot.feign.system.fallback.impl.RechargeCardFallbackFeignImpl;
import com.boot.pojo.RechargeCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-system")
public interface RechargeCardFeign {


    //使用卡
    @ResponseBody
    @PostMapping(path = "/feign/rechargeCard/updateCardStatus")
    public String updateCardStatus(@RequestBody RechargeCard rechargeCard);


}
