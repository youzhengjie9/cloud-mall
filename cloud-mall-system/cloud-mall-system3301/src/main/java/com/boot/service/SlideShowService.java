package com.boot.service;

import com.boot.pojo.SlideShow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlideShowService {


    List<SlideShow> selectSlideShow();


    //查找所有轮播图，并排序和分页
    List<SlideShow> selectAllSlideShowByLimit(int page, int limit);

    //查询轮播图数量
    int selectSlideShowCount();


    //修改排序
    void updateSort(long id,int sort);

    void deleteSlideShow(long id);

    void batchDeleteSlideShow(long[] ids);

    void addSlideShow(SlideShow slideShow);
}
