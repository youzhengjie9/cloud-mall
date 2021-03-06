package com.boot.feign.system.fallback;

import com.boot.feign.system.fallback.impl.CouponsRecordFallbackFeignImpl;
import com.boot.pojo.CouponsRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-system",fallback = CouponsRecordFallbackFeignImpl.class)
public interface CouponsRecordFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/couponsRecord/selectCouponsRecordByUserIdAndLimit/{page}/{size}/{userid}/{usetype}/{nowtime}")
    public List<CouponsRecord> selectCouponsRecordByUserIdAndLimit(@PathVariable("page") int page,
                                                                   @PathVariable("size") int size,
                                                                   @PathVariable("userid") long userid,
                                                                   @PathVariable("usetype") int usetype,
                                                                   @PathVariable("nowtime") String nowtime);

    @ResponseBody
    @GetMapping(path = "/feign/couponsRecord/selectCouponsRecord/{couponsid}/{userid}/{usetype}")
    public CouponsRecord selectCouponsRecord(@PathVariable("couponsid") long couponsid,
                                             @PathVariable("userid") long userid,
                                             @PathVariable("usetype") int usetype);


}
