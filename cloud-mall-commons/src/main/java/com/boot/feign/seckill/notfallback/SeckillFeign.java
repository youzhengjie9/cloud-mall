package com.boot.feign.seckill.notfallback;

import com.boot.feign.seckill.fallback.impl.SeckillFallbackFeignImpl;
import com.boot.pojo.SeckillSuccess;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-seckill")
public interface SeckillFeign {


    @ResponseBody
    @GetMapping(path = "/feign/seckill/doSeckill/{seckillId}/{userid}")
    public boolean doSeckill(@PathVariable("seckillId") long seckillId,
                             @PathVariable("userid") long userid)  throws InterruptedException;


    //减秒杀商品库存
    @ResponseBody
    @GetMapping(path = "/feign/seckill/decrSeckillNumber/{seckillId}")
    public String decrSeckillNumber(@PathVariable("seckillId") long seckillId);

    //插入秒杀成功记录
    @ResponseBody
    @PostMapping(path = "/feign/seckill/insertSeckillSuccess")
    public String insertSeckillSuccess(@RequestBody SeckillSuccess seckillSuccess);

    @ResponseBody
    @PostMapping(path = "/feign/seckill/seckillbegin")
    public String seckillbegin(@RequestBody SeckillSuccess seckillSuccess);

}
