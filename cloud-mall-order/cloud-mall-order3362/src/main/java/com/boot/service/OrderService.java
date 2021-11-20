package com.boot.service;

import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {

    void insertOrder(Order order);

    //查询指定用户的所有订单
    List<Order> selectAllOrdersByUserId(long userid);

    //查询订单状态
    OrderStatus selectOrderStatusById(long id);

    void orderBegin(String addressid, long id);

}
