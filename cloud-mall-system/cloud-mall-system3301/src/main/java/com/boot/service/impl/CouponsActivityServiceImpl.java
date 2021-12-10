package com.boot.service.impl;

import com.boot.dao.CouponsActivityMapper;
import com.boot.pojo.CouponsActivity;
import com.boot.service.CouponsActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
public class CouponsActivityServiceImpl implements CouponsActivityService {

    @Autowired
    private CouponsActivityMapper couponsActivityMapper;

    @Override
    public void insertCouponsActivity(CouponsActivity couponsActivity) {
        couponsActivityMapper.insertCouponsActivity(couponsActivity);
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
    }

    @Override
    public void batchDeleteCouponsActivity(long[] ids) {
        try{
            for (long id : ids) {
                couponsActivityMapper.deleteCouponsActivity(id);
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
    }
}
