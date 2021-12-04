package com.boot.service.impl;

import com.boot.dao.UserAuthorityMapper;
import com.boot.pojo.UserAuthority;
import com.boot.service.UserAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAuthorityServiceImpl implements UserAuthorityService {

    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Override
    public int selectAuthorityIdByUserId(long userid) {
        return userAuthorityMapper.selectAuthorityIdByUserId(userid);
    }

    @Override
    public void updateUserAuthority(UserAuthority userAuthority) {
        userAuthorityMapper.updateUserAuthority(userAuthority);
    }
}
