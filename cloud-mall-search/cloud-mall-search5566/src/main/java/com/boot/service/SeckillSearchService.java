package com.boot.service;

import com.boot.pojo.Product;
import com.boot.pojo.Seckill;

import java.io.IOException;
import java.util.List;

public interface SeckillSearchService {

    void initSeckillSearch() throws IOException, InterruptedException;


    List<Seckill> searchAllSeckill(String text, int from, int size,String ip) throws IOException;


}
