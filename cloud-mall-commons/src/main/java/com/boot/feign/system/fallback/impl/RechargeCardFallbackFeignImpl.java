package com.boot.feign.system.fallback.impl;

import com.boot.feign.system.fallback.RechargeCardFallbackFeign;
import com.boot.pojo.RechargeCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RechargeCardFallbackFeignImpl implements RechargeCardFallbackFeign {


    @Override
    public RechargeCard selectOneRechargeCard(String cardNumber, long password) {
        log.error("selectOneRechargeCard error");
        return null;
    }

    @Override
    public String recharge(RechargeCard rechargeCard) {
        log.error("recharge error");
        return null;
    }
}
