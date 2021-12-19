package com.boot.service;

import com.boot.pojo.Seckill;
import com.boot.pojo.SeckillSuccess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeckillService {


    String seckill(long seckillId,long userid);

    List<Seckill> selectAllSeckill();

    //减秒杀商品库存
    void decrSeckillNumber(long seckillId);

    //插入秒杀成功记录
    void insertSeckillSuccess(SeckillSuccess seckillSuccess);

    void seckillbegin(SeckillSuccess seckillSuccess);
}
