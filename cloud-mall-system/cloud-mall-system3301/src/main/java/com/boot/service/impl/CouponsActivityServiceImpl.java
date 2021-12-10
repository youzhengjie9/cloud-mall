package com.boot.service.impl;

import com.boot.dao.CouponsActivityMapper;
import com.boot.pojo.CouponsActivity;
import com.boot.service.CouponsActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CouponsActivityServiceImpl implements CouponsActivityService {

    @Autowired
    private CouponsActivityMapper couponsActivityMapper;

    @Override
    public void insertCouponsActivity(CouponsActivity couponsActivity) {
        couponsActivityMapper.insertCouponsActivity(couponsActivity);
    }
}
