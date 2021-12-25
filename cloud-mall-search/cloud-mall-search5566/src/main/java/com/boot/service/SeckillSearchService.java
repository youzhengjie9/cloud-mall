package com.boot.service;

import com.boot.pojo.Product;
import com.boot.pojo.Seckill;

import java.io.IOException;
import java.util.List;

public interface SeckillSearchService {

    void initSeckillSearch() throws IOException, InterruptedException;


    List<Seckill> searchAllSeckill(String text, int from, int size,String ip) throws IOException;


    //专门把数据查询给秒杀详情，所以只查询必须要的数据
    Seckill searchSeckilltoDetailByseckillId(long seckillId) throws IOException;

    void addSeckillToElasticSearchAndRedis(Seckill seckill) throws IOException;

    void updateSeckill(Seckill seckill) throws IOException;


    void deleteSeckill(long seckillId) throws IOException;


    void batchDeleteSeckill(long[] ids) throws IOException;


}
