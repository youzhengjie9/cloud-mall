package com.boot.feign.seckill.fallback;

import com.boot.feign.seckill.fallback.impl.SeckillFallbackFeignImpl;
import com.boot.pojo.Seckill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Component
@FeignClient(value = "cloud-mall-seckill",fallback = SeckillFallbackFeignImpl.class)
public interface SeckillFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/seckill/selectAllSeckill")
    public List<Seckill> selectAllSeckill();

}
