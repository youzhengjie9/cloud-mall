package com.boot.feign.system.fallback;

import com.boot.feign.system.fallback.impl.CouponsActivityFallbackFeignImpl;
import com.boot.pojo.CouponsActivity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "cloud-mall-system",fallback = CouponsActivityFallbackFeignImpl.class)
public interface CouponsActivityFallbackFeign {

    @ResponseBody
    @GetMapping(path = "/feign/couponsActivity/selectAllCouponsActivityByLimit/{page}/{size}")
    public List<CouponsActivity> selectAllCouponsActivityByLimit(@PathVariable("page") int page,
                                                                 @PathVariable("size") int size);

    @ResponseBody
    @GetMapping(path = "/feign/couponsActivity/selectCouponsActivityCount")
    public int selectCouponsActivityCount();

    @ResponseBody
    @GetMapping(path = "/feign/couponsActivity/selectCouponsActivityById/{id}")
    public CouponsActivity selectCouponsActivityById(@PathVariable("id") long id);

}
