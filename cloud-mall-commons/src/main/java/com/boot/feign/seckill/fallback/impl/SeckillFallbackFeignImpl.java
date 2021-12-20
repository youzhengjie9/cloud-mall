package com.boot.feign.seckill.fallback.impl;

import com.boot.feign.seckill.fallback.SeckillFallbackFeign;
import com.boot.pojo.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class SeckillFallbackFeignImpl implements SeckillFallbackFeign {


    @Override
    public List<Seckill> selectAllSeckill() {
        log.error("selectAllSeckill error");
        return null;
    }

}
