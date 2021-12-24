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

    List<SeckillSuccess> selectSeckillSuccessByUseridAndLimit(long userid, int page, int size);

    int selectSeckillSuccessCountByUserid(@Param("userid") long userid);

    int deleteSeckillSuccess(long id);

    List<Seckill> selectAllSeckillByLimit(int page, int size);

    int insertSeckill(Seckill seckill);

    int updateSeckill(Seckill seckill);

    int deleteSeckill(long seckillId);

    void batchDeleteSeckill(long[] seckillIds);

    Seckill selectSeckillByName(String seckillName);

    int selectAllSeckillCount();

    int selectAllSeckillCountByName(String seckillName);
}
