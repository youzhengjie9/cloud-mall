package com.boot.service.impl;

import com.boot.feign.seckill.notfallback.SeckillFeign;
import com.boot.pojo.Seckill;
import com.boot.pojo.SeckillSuccess;
import com.boot.service.SeckillService;
import com.boot.utils.SnowId;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillFeign seckillFeign;


    @Override
    public void doSeckill(long seckillId,long userid) {

        try{

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String createtime = simpleDateFormat.format(date);
            SeckillSuccess seckillSuccess = new SeckillSuccess();
            seckillSuccess.setId(SnowId.nextId())
                    .setSecKill(new Seckill().setSeckillId(seckillId))
                    .setState(0)
                    .setUserId(userid)
                    .setCreateTime(createtime);

            seckillFeign.seckillbegin(seckillSuccess);

        }catch (Exception e){
            throw new RuntimeException();
        }

    }
}
