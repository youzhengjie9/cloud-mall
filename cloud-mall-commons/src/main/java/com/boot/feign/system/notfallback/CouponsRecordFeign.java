package com.boot.feign.system.notfallback;

import com.boot.pojo.CouponsRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-system")
public interface CouponsRecordFeign {

    @ResponseBody
    @PostMapping(path = "/feign/couponsRecord/insertCouponsRecord")
    public String insertCouponsRecord(@RequestBody CouponsRecord couponsRecord);

    @ResponseBody
    @GetMapping(path = "/feign/couponsRecord/updateCouponsRecordUsetype/{couponsid}/{usetype}/{usetime}")
    public String updateCouponsRecordUsetype(@PathVariable("couponsid") long couponsid,
                                             @PathVariable("usetype") int usetype,
                                             @PathVariable("usetime") String usetime);
}
