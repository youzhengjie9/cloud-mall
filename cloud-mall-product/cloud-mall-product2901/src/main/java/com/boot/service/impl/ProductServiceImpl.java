package com.boot.service.impl;

import com.boot.dao.ProductMapper;
import com.boot.pojo.Classify;
import com.boot.pojo.Product;
import com.boot.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** @author 游政杰 */
@Transactional
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  @Autowired private ProductMapper productsMapper;

  @Override
  public List<Product> selectAllProduct() {
    return productsMapper.selectAllProduct();
  }

  @Override
  public List<Product> selectmxdp() {
    return productsMapper.selectmxdp();
  }

  @Override
  public List<Product> selectwntj() {
    return productsMapper.selectwntj();
  }

  @Override
  public String selectIntroduceByPid(long productId) {
    return productsMapper.selectIntroduceByPid(productId);
  }

  @Override
  public void insertProduct(Product product) {
    productsMapper.insertProduct(product);
  }

  @Override
  public Product selectProductByPid(long productId) {
    return productsMapper.selectProductByPid(productId);
  }

  @Override
  public void decrNumberByPid(long productId, int number) {
    productsMapper.decrNumberByPid(productId, number);
  }

  @Override
  public int selectProductCount() {
    return productsMapper.selectProductCount();
  }

  @Override
  public void updateProduct(Product product) {
    productsMapper.updateProduct(product);
  }

  @Override
  public void deleteProduct(long productId) {
    productsMapper.deleteProduct(productId);
  }

  @Override
  public void batchDeleteProducts(long[] ids) {

    try {
      for (long id : ids) {
          productsMapper.deleteProduct(id);
      }

    } catch (Exception e) {
      throw new RuntimeException("batchDeleteProducts exception");
    }
  }
}
