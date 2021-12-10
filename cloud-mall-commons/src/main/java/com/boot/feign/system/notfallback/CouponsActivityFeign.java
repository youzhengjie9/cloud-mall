package com.boot.feign.system.notfallback;

import com.boot.pojo.CouponsActivity;
import com.boot.pojo.RechargeCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-system")
public interface CouponsActivityFeign {


    @ResponseBody
    @PostMapping(path = "/feign/couponsActivity/insertCouponsActivity")
    public String insertCouponsActivity(@RequestBody CouponsActivity couponsActivity);


    @ResponseBody
    @GetMapping(path = "/feign/couponsActivity/updateValid/{id}/{valid}")
    public String updateValid(@PathVariable("id") long id,
                              @PathVariable("valid") int valid);

    @ResponseBody
    @GetMapping(path = "/feign/couponsActivity/deleteCouponsActivity/{id}")
    public String deleteCouponsActivity(@PathVariable("id") long id);

    @ResponseBody
    @GetMapping(path = "/feign/couponsActivity/batchDeleteCouponsActivity")
    public String batchDeleteCouponsActivity(@RequestParam("ids")long[] ids);


    @ResponseBody
    @PostMapping(path = "/feign/couponsActivity/updateCouponsActivity")
    public String updateCouponsActivity(@RequestBody CouponsActivity couponsActivity);

}
