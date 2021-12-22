package com.boot.feign.order.fallback.impl;

import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.OrderFallbackFeign;
import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class OrderFallbackFeignImpl implements OrderFallbackFeign {

    @Override
    public CommonResult<List<Order>> selectAllOrdersByUserId(long userid) {
        log.error("selectAllOrdersByUserId error");
        return null;
    }

    @Override
    public OrderStatus selectOrderStatusById(long id) {
        log.error("selectOrderStatusById error");
        return null;
    }

    @Override
    public int selectOrderCount() {
        log.error("selectOrderCount error");
        return 0;
    }

    @Override
    public BigDecimal selectDealMoneyByCreated(String created) {
        log.error("selectDealMoneyByCreated error");
        return null;
    }

    @Override
    public String selectNowDate() {
        log.error("selectNowDate error");
        return null;
    }

    @Override
    public List<String> selectDateBysevenDay() {
        log.error("selectDateBysevenDay error");
        return null;
    }

    @Override
    public List<Order> selectAllOrderBylimit(int page, int limit) {
        log.error("selectAllOrderBylimit error");
        return null;
    }

    @Override
    public Order selectOrderById(long id) {
        log.error("selectOrderById error");
        return null;
    }

    @Override
    public List<Order> selectAllOrderBylimitAndId(long userid, int page, int limit) {
        log.error("selectAllOrderBylimitAndId error");
        return null;
    }

    @Override
    public int selectOrderCountByid(long userid) {
        log.error("selectOrderCountByid error");
        return 0;
    }

    @Override
    public List<Order> selectReturnGoods(int page, int limit) {
        log.error("selectReturnGoods error");
        return null;
    }

    @Override
    public int selectReturnGoodsCount() {
        log.error("selectReturnGoodsCount error");
        return 0;
    }

    @Override
    public Order selectReturnGoodsById(long id) {
        log.error("selectReturnGoodsById error");
        return null;
    }

    @Override
    public int selectOrderCountById(long userid) {
        log.error("selectOrderCountById error");
        return 0;
    }

}
