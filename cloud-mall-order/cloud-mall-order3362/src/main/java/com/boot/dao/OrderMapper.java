package com.boot.dao;

import com.boot.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper {


    void insertOrder(Order order);



}
