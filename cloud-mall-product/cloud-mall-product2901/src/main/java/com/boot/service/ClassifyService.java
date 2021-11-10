package com.boot.service;


import com.boot.pojo.Classify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassifyService {

    //查询所有分类
    List<Classify> selectAllClassify();

    Classify selectClassifyByid(long id);

}
