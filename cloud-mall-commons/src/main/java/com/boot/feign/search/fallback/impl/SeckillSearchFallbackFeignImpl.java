package com.boot.feign.search.fallback.impl;

import com.boot.feign.search.fallback.SeckillSearchFallbackFeign;
import com.boot.pojo.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class SeckillSearchFallbackFeignImpl implements SeckillSearchFallbackFeign {


    @Override
    public List<Seckill> searchAllSeckill(String text, int from, int size,String ip) throws IOException {
        log.error("searchAllSeckill error");
        return null;
    }

    @Override
    public Seckill searchSeckilltoDetailByseckillId(long seckillId) throws IOException {
        log.error("searchSeckilltoDetailByseckillId error");
        return null;
    }
}
