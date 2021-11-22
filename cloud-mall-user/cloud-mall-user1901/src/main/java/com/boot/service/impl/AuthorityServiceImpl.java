package com.boot.service.impl;

import com.boot.dao.AuthorityMapper;
import com.boot.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public String selectAuthorityNameById(int id) {
        return authorityMapper.selectAuthorityNameById(id);
    }
}
