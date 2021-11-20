package com.boot.dao;

import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {


    void insertOrder(Order order);

    //查询指定用户的所有订单
    List<Order> selectAllOrdersByUserId(@Param("userid") long userid);

    //查询订单状态
    OrderStatus selectOrderStatusById(@Param("id") long id);



}
