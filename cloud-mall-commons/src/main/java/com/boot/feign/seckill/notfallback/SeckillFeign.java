package com.boot.feign.seckill.notfallback;

import com.boot.feign.seckill.fallback.impl.SeckillFallbackFeignImpl;
import com.boot.pojo.Seckill;
import com.boot.pojo.SeckillSuccess;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "cloud-mall-seckill")
public interface SeckillFeign {


    @ResponseBody
    @GetMapping(path = "/feign/seckill/doSeckill/{seckillId}/{userid}")
    public String doSeckill(@PathVariable("seckillId") long seckillId,
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

    @ResponseBody
    @GetMapping(path = "/feign/seckill/deleteSeckillSuccess/{id}")
    public int deleteSeckillSuccess(@PathVariable("id") long id);

    @ResponseBody
    @PostMapping(path = "/feign/seckill/insertSeckill")
    public int insertSeckill(@RequestBody Seckill seckill);

    @ResponseBody
    @PostMapping(path = "/feign/seckill/updateSeckill")
    public int updateSeckill(@RequestBody Seckill seckill);

    @ResponseBody
    @GetMapping(path = "/feign/seckill/deleteSeckill/{seckillId}")
    public int deleteSeckill(@PathVariable("seckillId") long seckillId);

    @ResponseBody
    @GetMapping(path = "/feign/seckill/batchDeleteSeckill")
    public String batchDeleteSeckill(@RequestParam("ids")long[] ids);

}
