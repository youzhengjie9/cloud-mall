package com.boot.service.impl;

import com.boot.dao.WntjMapper;
import com.boot.pojo.RecommandImg;
import com.boot.service.WntjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("wntjservice")
public class WntjServiceImpl implements WntjService {

    @Autowired
    private WntjMapper wntjMapper;

    @Override
    public List<RecommandImg> selectWntj() {
        return wntjMapper.selectWntj();
    }
}
