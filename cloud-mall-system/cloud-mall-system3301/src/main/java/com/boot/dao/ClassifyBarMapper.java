package com.boot.dao;

import com.boot.pojo.ClassifyBar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassifyBarMapper {


    List<ClassifyBar> selectAllClassifyBar();



}




