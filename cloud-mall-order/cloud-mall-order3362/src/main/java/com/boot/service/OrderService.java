package com.boot.service;

import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @author 游政杰
 */
public interface OrderService {

    void insertOrder(Order order);

    //查询指定用户的所有订单
    List<Order> selectAllOrdersByUserId(long userid);

    //查询订单状态
    OrderStatus selectOrderStatusById(long id);

    void orderBegin(String addressid, long id,long couponsid) throws ParseException;

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

    List<Order> selectAllOrderBylimitAndId(long userid,int page,int limit);

    int selectOrderCountByid(long userid);

    //查询所有有关退货的订单（包括退货完成的）
    List<Order> selectReturnGoods(int page,int limit);

    //查询有关退货的数量（包括退货完成的）
    int selectReturnGoodsCount();

    //查询退货并且指定id
    Order selectReturnGoodsById(long id);


    //同意退货
    void agreedReturnGoods(long userid,long orderid);

    //查询订单数
    int selectOrderCountById(long userid);

    //秒杀订单支付
    public void seckillOrder(long addressid ,long seckillsuccessid,long seckillid,long userid) throws IOException;


}
