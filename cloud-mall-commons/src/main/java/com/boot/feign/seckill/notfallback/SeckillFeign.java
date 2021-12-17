package com.boot.feign.seckill.notfallback;

import com.boot.feign.seckill.fallback.impl.SeckillFallbackFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@FeignClient(value = "cloud-mall-seckill")
public interface SeckillFeign {


    @ResponseBody
    @PostMapping(path = "/feign/seckill/doSeckill/{seckillId}/{userid}")
    public boolean doSeckill(@PathVariable("seckillId") long seckillId,
                             @PathVariable("userid") long userid)  throws InterruptedException;




}
