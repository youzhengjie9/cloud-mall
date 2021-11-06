package com.boot.dao;

import com.boot.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    //查询所有商品
    List<Product> selectAllProduct();

    List<Product> selectmxdp();

    List<Product> selectwntj();




}
