package com.boot.feign.seckill.fallback.impl;

import com.boot.feign.seckill.fallback.SeckillFallbackFeign;
import com.boot.pojo.Seckill;
import com.boot.pojo.SeckillSuccess;
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

    @Override
    public List<SeckillSuccess> selectSeckillSuccessByUseridAndLimit(long userid, int page, int size) {
        log.error("selectSeckillSuccessByUseridAndLimit error");
        return null;
    }

    @Override
    public int selectSeckillSuccessCountByUserid(long userid) {
        log.error("selectSeckillSuccessCountByUserid error");
        return 0;
    }

    @Override
    public List<Seckill> selectAllSeckillByLimit(int page, int size) {
        log.error("selectAllSeckillByLimit error");
        return null;
    }

    @Override
    public Seckill selectSeckillByName(String seckillName) {
        log.error("selectSeckillByName error");
        return null;
    }

    @Override
    public int selectAllSeckillCount() {
        log.error("selectAllSeckillCount error");
        return 0;
    }

    @Override
    public int selectAllSeckillCountByName(String seckillName) {
        log.error("selectAllSeckillCountByName error");
        return 0;
    }

}
