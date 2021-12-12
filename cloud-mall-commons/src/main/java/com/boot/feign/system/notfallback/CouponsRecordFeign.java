package com.boot.feign.system.notfallback;

import com.boot.pojo.CouponsRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-system")
public interface CouponsRecordFeign {

    @ResponseBody
    @PostMapping(path = "/feign/couponsRecord/insertCouponsRecord")
    public String insertCouponsRecord(@RequestBody CouponsRecord couponsRecord);

}
