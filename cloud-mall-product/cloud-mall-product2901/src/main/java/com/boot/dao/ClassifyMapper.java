package com.boot.dao;

import com.boot.pojo.Classify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassifyMapper {

    //查询所有分类
    List<Classify> selectAllClassify();

    Classify selectClassifyByid(@Param("id") long id);



}
