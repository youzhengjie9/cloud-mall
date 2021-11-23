package com.boot.service.impl;

import com.boot.dao.MenuMapper;
import com.boot.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public String selectMenuDataByAuthority(int authority) {
        return menuMapper.selectMenuDataByAuthority(authority);
    }
}
