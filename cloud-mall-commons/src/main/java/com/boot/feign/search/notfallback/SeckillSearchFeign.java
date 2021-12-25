package com.boot.feign.search.notfallback;

import com.boot.feign.search.fallback.impl.SeckillSearchFallbackFeignImpl;
import com.boot.pojo.Seckill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@FeignClient(value = "cloud-mall-search")
@Component
public interface SeckillSearchFeign {


    @ResponseBody
    @PostMapping(path = "/feign/search/addSeckillToElasticSearchAndRedis")
    public String addSeckillToElasticSearchAndRedis(@RequestBody Seckill seckill) throws IOException;


    @ResponseBody
    @PostMapping(path = "/feign/search/updateSeckill")
    public String updateSeckill(@RequestBody Seckill seckill) throws IOException;

    @ResponseBody
    @GetMapping(path = "/feign/search/deleteSeckill/{seckillId}")
    public String deleteSeckill(@PathVariable("seckillId") long seckillId) throws IOException;

    @ResponseBody
    @GetMapping(path = "/feign/search/batchDeleteSeckill")
    public String batchDeleteSeckill(@RequestParam("ids")long[] ids) throws IOException;

}
