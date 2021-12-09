package com.boot.service.impl;

import com.boot.dao.RechargeRecordMapper;
import com.boot.pojo.RechargeRecord;
import com.boot.service.RechargeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public void insertRechargeRecord(RechargeRecord rechargeRecord) {

        rechargeRecordMapper.insertRechargeRecord(rechargeRecord);

    }

    @Override
    public List<RechargeRecord> selectUserRechargeRecord(int page, int size, long userid) {
        return rechargeRecordMapper.selectUserRechargeRecord(page, size, userid);
    }
}
