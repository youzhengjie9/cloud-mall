package com.boot.dao;

import com.boot.pojo.Classify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游政杰
 */
@Mapper
@Repository
public interface ClassifyMapper {

    //查询所有分类
    List<Classify> selectAllClassify();

    @Select("select count(*) from classify")
    int selectClassifyCount();

    Classify selectClassifyByid(@Param("id") long id);

    @Select("select * from classify where text=#{text}")
    List<Classify> selectClassifiesByText(String text);

    @Select("select count(*) from classify where text=#{text}")
    int selectClassifiesCountByText(String text);

    //导航开启
    @Update("update classify set isNav=1 where id=#{id} and isNav=0")
    int navEnable(long id);

    //导航关闭
    @Update("update classify set isNav=0 where id=#{id} and isNav=1")
    int navDisable(long id);


    //添加分类
    void insertClassify(Classify classify);

    //修改分类
    void updateClassify(Classify classify);

    //删除分类
    void deleteClassify(@Param("id") long id);



}
