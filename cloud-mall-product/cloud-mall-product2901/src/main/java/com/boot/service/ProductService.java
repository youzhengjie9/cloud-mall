package com.boot.service;

import com.boot.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 游政杰
 */
public interface ProductService {


    //查询所有商品
    List<Product> selectAllProduct();

    List<Product> selectmxdp();

    List<Product> selectwntj();

    //查询指定商品的介绍图片
    String selectIntroduceByPid(long productId);

    void insertProduct(Product product);

    //通过productId查询product对象
    Product selectProductByPid(long productId);

    //减库存
    void decrNumberByPid(long productId,int number);


    //查询所有商品数量
    int selectProductCount();

    void updateProduct(Product product);

    //删除商品
    void deleteProduct(long productId);

    void batchDeleteProducts(long[] ids);
}
