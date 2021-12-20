package com.boot.feign.search.fallback;

import com.boot.feign.search.fallback.impl.SearchFallbackFeignImpl;
import com.boot.feign.search.fallback.impl.SeckillSearchFallbackFeignImpl;
import com.boot.pojo.Seckill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@FeignClient(value = "cloud-mall-search",fallback = SeckillSearchFallbackFeignImpl.class)
@Component
public interface SeckillSearchFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/search/searchAllSeckill")
    public List<Seckill> searchAllSeckill(@RequestParam("text") String text,
                                          @RequestParam("from")int from,
                                          @RequestParam("size")int size,
                                          @RequestParam("ip") String ip) throws IOException;


    //专门把数据查询给秒杀详情，所以只查询必须要的数据
    @ResponseBody
    @GetMapping(path = "/feign/search/searchSeckilltoDetailByseckillId/{seckillId}")
    public Seckill searchSeckilltoDetailByseckillId(@PathVariable("seckillId") long seckillId) throws IOException;

}
