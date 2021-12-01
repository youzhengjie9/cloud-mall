package com.boot.feign.order.fallback;

import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.impl.OrderFallbackFeignImpl;
import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 游政杰
 */
@FeignClient(value = "cloud-mall-order",fallback = OrderFallbackFeignImpl.class)
@Component
public interface OrderFallbackFeign {

    // 查询指定用户的所有订单
    @ResponseBody
    @GetMapping(path = "/feign/order/selectAllOrdersByUserId/{userid}")
    public CommonResult<List<Order>> selectAllOrdersByUserId(@PathVariable("userid") long userid);

    //查询订单状态
    @ResponseBody
    @GetMapping(path = "/feign/order/selectOrderStatusById/{id}")
    public OrderStatus selectOrderStatusById(@PathVariable("id") long id);

    //查询订单数
    @ResponseBody
    @GetMapping(path = "/feign/order/selectOrderCount")
    public int selectOrderCount();

    //查询某一天的交易额
    @ResponseBody
    @GetMapping(path = "/feign/order/selectDealMoneyByCreated/{created}")
    public BigDecimal selectDealMoneyByCreated(@PathVariable("created") String created);

    //查询今天的日期
    @ResponseBody
    @GetMapping(path = "/feign/order/selectNowDate")
    public String selectNowDate();

    //查询近7天的日期
    @ResponseBody
    @GetMapping(path = "/feign/order/selectDateBysevenDay")
    public List<String> selectDateBysevenDay();


    @ResponseBody
    @GetMapping(path = "/feign/order/selectAllOrderBylimit/{page}/{limit}")
    public List<Order> selectAllOrderBylimit(@PathVariable("page") int page,
                                             @PathVariable("limit") int limit);

    @ResponseBody
    @GetMapping(path = "/feign/order/selectOrderById/{id}")
    public Order selectOrderById(@PathVariable("id") long id);


    @ResponseBody
    @GetMapping(path = "/feign/order/selectAllOrderBylimitAndId/{userid}/{page}/{limit}")
    public List<Order> selectAllOrderBylimitAndId(@PathVariable("userid") long userid,
                                                  @PathVariable("page") int page,
                                                  @PathVariable("limit") int limit);

    @ResponseBody
    @GetMapping(path = "/feign/order/selectOrderCountByid/{userid}")
    public int selectOrderCountByid(@PathVariable("userid") long userid);



    //查询所有有关退货的订单（包括退货完成的）
    @ResponseBody
    @GetMapping(path = "/feign/order/selectReturnGoods/{page}/{limit}")
    public List<Order> selectReturnGoods(@PathVariable("page") int page,
                                         @PathVariable("limit") int limit);

    //查询有关退货的数量（包括退货完成的）
    @ResponseBody
    @GetMapping(path = "/feign/order/selectReturnGoodsCount")
    public int selectReturnGoodsCount();


    @ResponseBody
    @GetMapping(path = "/feign/order/selectReturnGoodsById/{id}")
    public Order selectReturnGoodsById(@PathVariable("id") long id);

}
