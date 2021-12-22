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

    List<SeckillSuccess> selectSeckillSuccessByUseridAndLimit(@Param("userid") long userid,
                                                              @Param("page") int page,
                                                              @Param("size") int size);

    int selectSeckillSuccessCountByUserid(@Param("userid") long userid);


    int deleteSeckillSuccess(@Param("id") long id);



}
