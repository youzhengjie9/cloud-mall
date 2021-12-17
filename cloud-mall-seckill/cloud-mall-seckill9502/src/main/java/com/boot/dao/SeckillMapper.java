package com.boot.dao;

import com.boot.pojo.Seckill;
import com.boot.pojo.SeckillSuccess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SeckillMapper {

    //减秒杀商品库存
    void decrSeckillNumber(@Param("seckillId") long seckillId);

    //插入秒杀成功记录
    void insertSeckillSuccess(SeckillSuccess seckillSuccess);

    List<Seckill> selectAllSeckill();



}
