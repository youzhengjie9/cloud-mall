package com.boot.service;

import com.boot.pojo.Brand;
import java.util.List;

public interface BrandService {

    //查询所有品牌
    List<Brand> selectAllBrand();

    //根据productId查询对应的品牌id
    long selectBrandIdNameByPid(long pid);

    //通过品牌id查询品牌对象
    Brand selectBrandByid(long bid);

    int selectBrandCount();

    List<Brand> selectBrandByName(String brandName);

    int selectBrandCountByName(String brandName);
}
