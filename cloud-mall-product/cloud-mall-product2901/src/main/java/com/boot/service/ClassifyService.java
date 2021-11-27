package com.boot.service;


import com.boot.pojo.Classify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassifyService {

    //查询所有分类
    List<Classify> selectAllClassify();

    Classify selectClassifyByid(long id);

    int selectClassifyCount();

    List<Classify> selectClassifiesByText(String text);

    int selectClassifiesCountByText(String text);

    int navEnable(long id);

    int navDisable(long id);


    //添加分类
    void insertClassify(Classify classify);

    //修改分类
    void updateClassify(Classify classify);

    //删除分类
    void deleteClassify(long id);

    //批量删除分类
    void batchDeleteClassify(long[] ids);




}
