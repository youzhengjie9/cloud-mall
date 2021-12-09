package com.boot.feign.system.fallback;

import com.boot.feign.system.fallback.impl.RechargeCardFallbackFeignImpl;
import com.boot.feign.system.fallback.impl.RechargeRecordFallbackFeignImpl;
import com.boot.pojo.RechargeCard;
import com.boot.pojo.RechargeRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-system",fallback = RechargeRecordFallbackFeignImpl.class)
public interface RechargeRecordFallbackFeign {


    //查询用户充值记录
    @ResponseBody
    @GetMapping(path = "/feign/rechargeRecord/selectUserRechargeRecord/{page}/{size}/{userid}")
    public List<RechargeRecord> selectUserRechargeRecord(@PathVariable("page") int page,
                                                         @PathVariable("size") int size,
                                                         @PathVariable("userid") long userid);


}
