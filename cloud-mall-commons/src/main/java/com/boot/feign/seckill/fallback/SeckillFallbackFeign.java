package com.boot.feign.seckill.fallback;

import com.boot.feign.seckill.fallback.impl.SeckillFallbackFeignImpl;
import com.boot.pojo.Seckill;
import com.boot.pojo.SeckillSuccess;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Component
@FeignClient(value = "cloud-mall-seckill",fallback = SeckillFallbackFeignImpl.class)
public interface SeckillFallbackFeign {


    @ResponseBody
    @GetMapping(path = "/feign/seckill/selectAllSeckill")
    public List<Seckill> selectAllSeckill();

    @ResponseBody
    @GetMapping(path = "/feign/seckill/selectSeckillSuccessByUseridAndLimit/{userid}/{page}/{size}")
    public List<SeckillSuccess> selectSeckillSuccessByUseridAndLimit(@PathVariable("userid") long userid,
                                                                     @PathVariable("page") int page,
                                                                     @PathVariable("size") int size);

    @ResponseBody
    @GetMapping(path = "/feign/seckill/selectSeckillSuccessCountByUserid/{userid}")
    public int selectSeckillSuccessCountByUserid(@PathVariable("userid") long userid);


    @ResponseBody
    @GetMapping(path = "/feign/seckill/selectAllSeckillByLimit/{page}/{size}")
    public List<Seckill> selectAllSeckillByLimit(@PathVariable("page") int page,
                                                 @PathVariable("size") int size);

    @ResponseBody
    @GetMapping(path = "/feign/seckill/selectSeckillByName/{seckillName}")
    public Seckill selectSeckillByName(@PathVariable("seckillName") String seckillName);

    @ResponseBody
    @GetMapping(path = "/feign/seckill/selectAllSeckillCount")
    public int selectAllSeckillCount();

    @ResponseBody
    @GetMapping(path = "/feign/seckill/selectAllSeckillCountByName/{seckillName}")
    public int selectAllSeckillCountByName(@PathVariable("seckillName") String seckillName);

}
