package com.boot.service;

import com.boot.pojo.Product;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.List;

/**
 * @author 游政杰
 */
public interface SearchService {

    void initSearch() throws IOException;


    SearchHit[] searchProductHitByName(String text) throws IOException;


    List<Product> searchProductByHit(String text,SearchHit[] searchHits) throws IOException;


    //查询所有数据并分页
    List<Product> searchAllProductByLimit(int from,int size) throws IOException;

    //from size 为分页
    List<Product> searchProductsByCondition(String text,long brandid,long classifyid,int from,int size) throws IOException;


    long searchAllProductsCount() throws IOException;

    //根据商品名搜索商品集合并且分页
    List<Product> searchProductsByNameAndLimit(int from , int size ,String text) throws IOException;

    //根据商品名搜索商品的目标数
    long searchProductCountByName(String text) throws IOException;


    //修改商品
    void updateProduct(Product product) throws IOException;

    //删除商品
    void deleteProduct(long productid) throws IOException;

    //批量删除商品
    void batchDeleteProcts(long[] ids) throws IOException;

}
