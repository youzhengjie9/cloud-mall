package com.boot.dao;

import com.boot.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游政杰
 */
@Mapper
@Repository
public interface ProductMapper {

    //查询所有商品
    List<Product> selectAllProduct();

    List<Product> selectmxdp();

    List<Product> selectwntj();

    //查询指定商品的介绍图片
    String selectIntroduceByPid(@Param("productId")long productId);

    void insertProduct(Product product);

    //通过productId查询product对象
    Product selectProductByPid(@Param("productId")long productId);


}
