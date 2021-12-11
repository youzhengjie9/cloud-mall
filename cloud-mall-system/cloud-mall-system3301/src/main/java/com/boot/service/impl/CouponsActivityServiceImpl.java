package com.boot.service.impl;

import com.boot.dao.CouponsActivityMapper;
import com.boot.pojo.CouponsActivity;
import com.boot.service.CouponsActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
public class CouponsActivityServiceImpl implements CouponsActivityService {

    @Autowired
    private CouponsActivityMapper couponsActivityMapper;
    private static final String COUPONS_ACTIVITY_KEY="coupons_activity_key_";

    @Autowired
    private RedisTemplate redisTemplate;

    private String link(String ...strs)
    {
        if(strs.length==0){
            return null;
        }else if(strs.length==1){
            return strs[0];
        }else {
            String res="";
            String cut=",";
            res+=strs[0];
            for (int i = 1; i < strs.length; i++) {
                res+=cut+strs[i];
            }
            return res;
        }
    }

    @Override
    public void insertCouponsActivity(CouponsActivity couponsActivity) {
        couponsActivityMapper.insertCouponsActivity(couponsActivity);
        redisTemplate.opsForValue().set(COUPONS_ACTIVITY_KEY+couponsActivity.getId(),
                link(String.valueOf(couponsActivity.getLimitCount()),couponsActivity.getMinPoint().toString()));
    }

    @Override
    public List<CouponsActivity> selectAllCouponsActivityByLimit(int page, int size) {
        return couponsActivityMapper.selectAllCouponsActivityByLimit(page, size);
    }

    @Override
    public int selectCouponsActivityCount() {
        return couponsActivityMapper.selectCouponsActivityCount();
    }

    @Override
    public void updateValid(long id, int valid) {
        couponsActivityMapper.updateValid(id, valid);
    }

    @Override
    public void deleteCouponsActivity(long id) {
        couponsActivityMapper.deleteCouponsActivity(id);
        redisTemplate.delete(COUPONS_ACTIVITY_KEY+id);
    }

    @Override
    public void batchDeleteCouponsActivity(long[] ids) {
        try{
            for (long id : ids) {
                couponsActivityMapper.deleteCouponsActivity(id);
            }
            for (long id : ids) {
                redisTemplate.delete(COUPONS_ACTIVITY_KEY+id);
            }
        }catch (Exception e)
        {
            throw new RuntimeException();
        }

    }

    @Override
    public CouponsActivity selectCouponsActivityById(long id) {
        return couponsActivityMapper.selectCouponsActivityById(id);
    }

    @Override
    public void updateCouponsActivity(CouponsActivity couponsActivity) {
        couponsActivityMapper.updateCouponsActivity(couponsActivity);
        redisTemplate.opsForValue().set(COUPONS_ACTIVITY_KEY+couponsActivity.getId(),
                link(String.valueOf(couponsActivity.getLimitCount()),couponsActivity.getMinPoint().toString()));
    }

    @Override
    public List<CouponsActivity> selectAllCouponsActivityByLimitAndValid(int page, int size) {
        return couponsActivityMapper.selectAllCouponsActivityByLimitAndValid(page, size);
    }

    @Override
    public int selectCouponsActivityCountByValid() {
        return couponsActivityMapper.selectCouponsActivityCountByValid();
    }

    @Override
    public List<CouponsActivity> selectAllCouponsActivityLimitAndPoint() {
        return couponsActivityMapper.selectAllCouponsActivityLimitAndPoint();
    }
}
