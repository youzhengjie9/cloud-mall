package com.boot.service.impl;

import com.boot.dao.RechargeRecordMapper;
import com.boot.pojo.RechargeRecord;
import com.boot.service.RechargeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public void insertRechargeRecord(RechargeRecord rechargeRecord) {

        rechargeRecordMapper.insertRechargeRecord(rechargeRecord);

    }
}
