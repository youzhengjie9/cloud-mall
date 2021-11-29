package com.boot.service.impl;

import com.boot.dao.VisitorMapper;
import com.boot.pojo.Visitor;
import com.boot.service.VisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public void insertVisitor(Visitor visitor) {
        visitorMapper.insertVisitor(visitor);
    }

    @Override
    public List<Visitor> selectVisitorBylimit(int page, int size) {
        return visitorMapper.selectVisitorBylimit(page, size);
    }

    @Override
    public int selectVisitorCount() {
        return visitorMapper.selectVisitorCount();
    }
}
