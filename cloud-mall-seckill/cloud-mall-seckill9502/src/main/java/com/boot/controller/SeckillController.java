package com.boot.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.boot.enums.ResultConstant;
import com.boot.pojo.SeckillSuccess;
import com.boot.service.SeckillService;
import com.google.common.util.concurrent.RateLimiter;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Controller
@RequestMapping(path = "/feign/seckill")
@Api("秒杀服务api")
public class SeckillController {

    private final String SECKILL_LOCK="seckill_lock_"; //秒杀商品锁key

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedissonClient redissonClient;

    private static final AtomicInteger count=new AtomicInteger(); //监控秒杀限流次数

    private static RateLimiter rateLimiter;

    static {

        rateLimiter=RateLimiter.create(20.0);

    }


    /**
     * sentinel无效，此时采用google的ratelimiter令牌桶限流
     * 并发压测5000线程+1次循环 ,只启动async和seckill模块的情况下测试
     *  1：没做ratelimiter限流和没做消息队列异步处理秒杀，qps=113/s
     *  2. 做ratelimiter限“”20“”个qps和没做消息队列异步处理秒杀,qps=396/s
     *  3. 做ratelimiter限“”20“”个qps和做消息队列异步处理秒杀,qps=
     * @param seckillId
     * @param userid
     * @return
     * @throws InterruptedException
     */

    @ResponseBody
    @GetMapping(path = "/doSeckill/{seckillId}/{userid}")
    @SentinelResource(value = "doSeckill",blockHandler = "doSeckill_block")
    public boolean doSeckill(@PathVariable("seckillId") long seckillId,
                            @PathVariable("userid") long userid) throws InterruptedException {

        if(rateLimiter.tryAcquire())
        {
            RLock lock = redissonClient.getLock(SECKILL_LOCK + seckillId);
            try{

                if(lock.tryLock(3,5, TimeUnit.SECONDS)){

                boolean seckill = seckillService.seckill(seckillId, userid);

                    return true;
                }else {
                    log.warn("当前锁被占用。。。");
                    return false;
                }
            }finally{
                lock.unlock();
            }
        }else {
            log.info("秒杀限流次数："+count.incrementAndGet());
           return false;
        }
    }
    public boolean doSeckill_block(long seckillId, long userid, BlockException ex){

        log.info("秒杀限流次数："+count.incrementAndGet());
        throw new RuntimeException("秒杀限流次数："+count.incrementAndGet());

    }

    //减秒杀商品库存
    @ResponseBody
    @GetMapping(path = "/decrSeckillNumber/{seckillId}")
    public String decrSeckillNumber(@PathVariable("seckillId") long seckillId){

        seckillService.decrSeckillNumber(seckillId);

        return ResultConstant.SUCCESS.getCodeStat();
    }

    //插入秒杀成功记录
    @ResponseBody
    @PostMapping(path = "/insertSeckillSuccess")
    public String insertSeckillSuccess(@RequestBody SeckillSuccess seckillSuccess){

        seckillService.insertSeckillSuccess(seckillSuccess);

        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @PostMapping(path = "/seckillbegin")
    public String seckillbegin(@RequestBody SeckillSuccess seckillSuccess){

        seckillService.seckillbegin(seckillSuccess);
        return ResultConstant.SUCCESS.getCodeStat();
    }

}