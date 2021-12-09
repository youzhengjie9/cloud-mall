package com.boot.dao;

import com.boot.pojo.SlideShow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SlideShowMapper {

    //查找5张轮播图，并排序
    List<SlideShow> selectSlideShow();

    //查找所有轮播图，并排序和分页
    List<SlideShow> selectAllSlideShowByLimit(@Param("page") int page,
                                              @Param("limit") int limit);

    //查询轮播图数量
    int selectSlideShowCount();


    //修改排序
    void updateSort(@Param("id") long id,@Param("sort") int sort);


    void deleteSlideShow(@Param("id") long id);


    void addSlideShow(SlideShow slideShow);

}
