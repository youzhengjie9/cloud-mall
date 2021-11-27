package com.boot.service.impl;

import com.boot.dao.ClassifyMapper;
import com.boot.pojo.Classify;
import com.boot.service.ClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
public class ClassifyServiceImpl implements ClassifyService {

  @Autowired private ClassifyMapper classifyMapper;

  @Override
  public List<Classify> selectAllClassify() {
    return classifyMapper.selectAllClassify();
  }

  @Override
  public Classify selectClassifyByid(long id) {

    return classifyMapper.selectClassifyByid(id);
  }

  @Override
  public int selectClassifyCount() {
    return classifyMapper.selectClassifyCount();
  }

  @Override
  public List<Classify> selectClassifiesByText(String text) {
    return classifyMapper.selectClassifiesByText(text);
  }

  @Override
  public int selectClassifiesCountByText(String text) {
    return classifyMapper.selectClassifiesCountByText(text);
  }

  @Override
  public int navEnable(long id) {
    return classifyMapper.navEnable(id);
  }

  @Override
  public int navDisable(long id) {
    return classifyMapper.navDisable(id);
  }

  @Override
  public void insertClassify(Classify classify) {
    classifyMapper.insertClassify(classify);
  }

  @Override
  public void updateClassify(Classify classify) {
    classifyMapper.updateClassify(classify);
  }

  @Override
  public void deleteClassify(long id) {
    classifyMapper.deleteClassify(id);
  }

  @Override
  public void batchDeleteClassify(long[] ids) {

    try {
      if (ids != null && ids.length > 0) {
        for (long id : ids) {
          classifyMapper.deleteClassify(id);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("本地事务回滚");
    }
  }
}
