package com.boot.service.impl;

import com.boot.dao.AuthorityMapper;
import com.boot.pojo.Authority;
import com.boot.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public String selectAuthorityNameById(int id) {
        return authorityMapper.selectAuthorityNameById(id);
    }

    @Override
    public List<Authority> selectAuthorityExcludeCurAuthority(int id) {
        return authorityMapper.selectAuthorityExcludeCurAuthority(id);
    }
}
