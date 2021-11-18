package com.boot.feign.order.fallback.impl;

import com.boot.feign.order.fallback.AddressFallbackFeign;
import com.boot.pojo.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AddressFallbackFeignImpl implements AddressFallbackFeign {

    @Override
    public List<Address> selectAddressByUserId(long userid) {
        log.error("selectAddressByUserId error");
        return null;
    }
}
