package com.boot.service.impl;

import com.alibaba.fastjson.JSON;
import com.boot.dao.CouponsRecordMapper;
import com.boot.data.layuiJSON;
import com.boot.pojo.CouponsRecord;
import com.boot.service.CouponsRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
public class CouponsRecordServiceImpl implements CouponsRecordService {

    private static final String COUPONS_ACTIVITY_KEY="coupons_activity_key_";
    @Autowired
    private CouponsRecordMapper couponsRecordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String insertCouponsRecord(CouponsRecord couponsRecord) {
        layuiJSON layuiJSON = new layuiJSON();
        try{
            //判断是否可以领取优惠券
            String links = (String) redisTemplate.opsForValue().get(COUPONS_ACTIVITY_KEY + couponsRecord.getCouponsActivity().getId());
            String[] split = links.split(",");
            int limitcount = Integer.parseInt(split[0]);
            int usercount = couponsRecordMapper.selectCouponsCountByUserIdAndCouponsId(couponsRecord.getUser_id(), couponsRecord.getCouponsActivity().getId());

            if(usercount<limitcount){//此时可以领取
                couponsRecordMapper.insertCouponsRecord(couponsRecord);
                layuiJSON.setSuccess(true);
                layuiJSON.setMsg("领取成功");
                return JSON.toJSONString(layuiJSON);
            }else {
                layuiJSON.setSuccess(false);
                layuiJSON.setMsg("您领取该优惠券已经达到上限,领取失败");
                return JSON.toJSONString(layuiJSON);
            }

        }catch (Exception e){
            throw new RuntimeException("insertCouponsRecord添加优惠券失败");
        }

    }

    @Override
    public int selectCouponsCountByUserIdAndCouponsId(long userid, long couponsid) {
        return couponsRecordMapper.selectCouponsCountByUserIdAndCouponsId(userid, couponsid);
    }
}
