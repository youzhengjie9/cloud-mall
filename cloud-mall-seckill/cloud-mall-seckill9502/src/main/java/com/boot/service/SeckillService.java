package com.boot.service;

import com.boot.pojo.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeckillService {


    boolean seckill(long seckillId,long userid);

    List<Seckill> selectAllSeckill();

}
