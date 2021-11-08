package com.boot.service.impl;

import com.boot.dao.ClassifyBarMapper;
import com.boot.pojo.ClassifyBar;
import com.boot.service.ClassifyBarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ClassifyBarServiceImpl implements ClassifyBarService {

    @Resource
    private ClassifyBarMapper classifyBarMapper;

    @Override
    public List<ClassifyBar> selectAllClassifyBar() {
        return classifyBarMapper.selectAllClassifyBar();
    }
}
