package com.boot.service;

import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    void insertOrder(Order order);

    //查询指定用户的所有订单
    List<Order> selectAllOrdersByUserId(long userid);

    //查询订单状态
    OrderStatus selectOrderStatusById(long id);

    void orderBegin(String addressid, long id);

    //查询订单数
    int selectOrderCount();

    //查询某一天的交易额
    BigDecimal selectDealMoneyByCreated(String created);

    //查询今天的日期
    String selectNowDate();

    //查询近7天的日期
    List<String> selectDateBysevenDay();


    List<Order> selectAllOrderBylimit(int page,int limit);

    //查询指定订单
    Order selectOrderById(long id);

    void updateOrderStatus(long id, long statusid);

}
