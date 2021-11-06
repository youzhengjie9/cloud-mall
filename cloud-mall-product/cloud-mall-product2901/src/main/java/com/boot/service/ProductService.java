package com.boot.service;

import com.boot.pojo.Product;

import java.util.List;

/**
 * @author 游政杰
 */
public interface ProductService {


    //查询所有商品
    List<Product> selectAllProduct();

    List<Product> selectmxdp();

    List<Product> selectwntj();

}
