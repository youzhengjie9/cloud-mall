package com.boot.service.impl;

import com.boot.dao.UserDetailMapper;
import com.boot.pojo.UserDetail;
import com.boot.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public void insertUserDetail(UserDetail userDetail) {

        if(StringUtils.isBlank(userDetail.getSignature())){
            userDetail.setSignature("默认的个性签名");
        }
        userDetailMapper.insertUserDetail(userDetail);

    }

    @Override
    public void updateSex(long userid, int sex) {
        userDetailMapper.updateSex(userid, sex);
    }

    @Override
    public void updateSignature(long userid, String signature) {
        userDetailMapper.updateSignature(userid, signature);
    }
}
