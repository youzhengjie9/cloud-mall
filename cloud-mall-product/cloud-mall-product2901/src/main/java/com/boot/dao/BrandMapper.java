package com.boot.dao;

import com.boot.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游政杰
 */
@Mapper
@Repository
public interface BrandMapper {

    //查询所有品牌
    List<Brand> selectAllBrand();

    //根据productId查询对应的品牌id
    long selectBrandIdNameByPid(@Param("pid") long pid);

    //通过品牌id查询品牌对象
    Brand selectBrandByid(@Param("bid") long bid);





}
