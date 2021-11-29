package com.boot.service;


import com.boot.pojo.Visitor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisitorService {


    void insertVisitor(Visitor visitor);

    List<Visitor> selectVisitorBylimit(int page,int size);

    int selectVisitorCount();



}
