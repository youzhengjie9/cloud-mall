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


    List<Product> searchProductByHit(SearchHit[] searchHits);


    //查询所有数据并分页
    List<Product> searchAllProductByLimit(int from,int size) throws IOException;

    //from size 为分页
    List<Product> searchProductsByCondition(String text,long brandid,long classifyid,int from,int size) throws IOException;





}
