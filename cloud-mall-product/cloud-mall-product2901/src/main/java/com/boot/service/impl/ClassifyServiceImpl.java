package com.boot.service.impl;

import com.boot.dao.ClassifyMapper;
import com.boot.pojo.Classify;
import com.boot.service.ClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyMapper classifyMapper;

    @Override
    public List<Classify> selectAllClassify() {
        return classifyMapper.selectAllClassify();
    }

    @Override
    public Classify selectClassifyByid(long id){

        return classifyMapper.selectClassifyByid(id);

    }
}
