package com.boot.dao;

import com.boot.pojo.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VisitorMapper {


    void insertVisitor(Visitor visitor);

    List<Visitor> selectVisitorBylimit(@Param("page") int page,@Param("size") int size);

    int selectVisitorCount();

}
