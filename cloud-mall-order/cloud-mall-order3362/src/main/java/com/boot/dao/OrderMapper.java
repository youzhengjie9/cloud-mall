package com.boot.dao;

import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 游政杰
 */
@Mapper
@Repository
public interface OrderMapper {


    void insertOrder(Order order);

    //查询指定用户的所有订单
    List<Order> selectAllOrdersByUserId(@Param("userid") long userid);

    //查询订单状态
    OrderStatus selectOrderStatusById(@Param("id") long id);

    //查询订单数
    int selectOrderCount();

    //查询某一天的交易额
    BigDecimal selectDealMoneyByCreated(@Param("created") String created);

    //查询今天的日期
    String selectNowDate();

    //查询近7天的日期
    List<String> selectDateBysevenDay();


    List<Order> selectAllOrderBylimit(@Param("page") int page,@Param("limit") int limit);

    //查询指定订单
    Order selectOrderById(@Param("id") long id);

    void updateOrderStatus(@Param("id") long id,@Param("statusid") long statusid);

    List<Order> selectAllOrderBylimitAndId(@Param("userid") long userid,@Param("page") int page,@Param("limit") int limit);

    //查询用户总订单数
    int selectOrderCountByid(@Param("userid") long userid);


    //查询所有有关退货的订单（包括退货完成的）
    List<Order> selectReturnGoods(@Param("page") int page,@Param("limit") int limit);

    //查询有关退货的数量（包括退货完成的）
    int selectReturnGoodsCount();

    Order selectReturnGoodsById(@Param("id") long id);


}
