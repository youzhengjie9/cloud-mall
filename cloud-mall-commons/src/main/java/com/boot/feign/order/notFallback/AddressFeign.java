package com.boot.feign.order.notFallback;

import com.boot.data.CommonResult;
import com.boot.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "cloud-mall-order")
@Component
public interface AddressFeign {


    @ResponseBody
    @PostMapping(path = "/feign/address/addAddress")
    public CommonResult<Address> addAddress(@RequestBody Address address);

    @ResponseBody
    @GetMapping(path = "/feign/address/delAddressById/{id}")
    public CommonResult<Address> delAddressById(@PathVariable("id") long id);
}
