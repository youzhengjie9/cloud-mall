package com.boot.service.impl;

import com.boot.dao.BrandMapper;
import com.boot.pojo.Brand;
import com.boot.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional // 开启事务
@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

  @Autowired private BrandMapper brandMapper;

  @Override
  public List<Brand> selectAllBrand() {
    return brandMapper.selectAllBrand();
  }

  @Override
  public long selectBrandIdNameByPid(long pid) {
    return brandMapper.selectBrandIdNameByPid(pid);
  }

  @Override
  public Brand selectBrandByid(long bid) {
    return brandMapper.selectBrandByid(bid);
  }

  @Override
  public int selectBrandCount() {
    return brandMapper.selectBrandCount();
  }

  @Override
  public List<Brand> selectBrandByName(String brandName) {
    return brandMapper.selectBrandByName(brandName);
  }

  @Override
  public int selectBrandCountByName(String brandName) {
    return brandMapper.selectBrandCountByName(brandName);
  }

  @Override
  public void insertBrand(Brand brand) {
    brandMapper.insertBrand(brand);
  }

  @Override
  public void updateBrandName(Brand brand) {

    brandMapper.updateBrandName(brand);
  }

  @Override
  public void deleteBrand(long id) {
    brandMapper.deleteBrand(id);
  }

  @Override
  public void batchDeleteBrand(long[] ids) {

    try{
      if (ids != null && ids.length > 0) {

        for (long id : ids) {
          brandMapper.deleteBrand(id);
        }
      }

    }catch (Exception e){
      throw new RuntimeException("本地事务回滚");
    }
  }
}
