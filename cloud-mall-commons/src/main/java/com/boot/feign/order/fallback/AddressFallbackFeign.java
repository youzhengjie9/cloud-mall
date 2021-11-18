package com.boot.feign.order.fallback;

import com.boot.feign.order.fallback.impl.AddressFallbackFeignImpl;
import com.boot.feign.order.fallback.impl.OrderFallbackFeignImpl;
import com.boot.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = "cloud-mall-order",fallback = AddressFallbackFeignImpl.class)
@Component
public interface AddressFallbackFeign {


    //用户可能有多个地址，所以下面是一个集合
    @ResponseBody
    @GetMapping(path = "/feign/address/selectAddressByUserId/{userid}")
    public List<Address> selectAddressByUserId(@PathVariable("userid") long userid);


}
