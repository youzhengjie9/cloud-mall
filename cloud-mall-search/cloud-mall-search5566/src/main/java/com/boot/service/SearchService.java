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



}
