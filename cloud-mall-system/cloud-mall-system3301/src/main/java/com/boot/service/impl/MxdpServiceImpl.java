package com.boot.service.impl;

import com.boot.dao.MxdpMapper;
import com.boot.pojo.RecommandImg;
import com.boot.service.MxdpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("mxdpservice")
public class MxdpServiceImpl implements MxdpService {

    @Autowired
    private MxdpMapper mxdpMapper;

    @Override
    public List<RecommandImg> selectMxdp() {
        return mxdpMapper.selectMxdp();
    }
}
