package com.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.pojo.Address;
import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import com.boot.service.OrderService;
import io.swagger.annotations.Api;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(path = "/feign/order")
@Api("订单服务api")
public class OrderController {

  @Autowired private OrderService orderService;

  @Autowired
  private RedissonClient redissonClient;

  @ResponseBody
  @PostMapping(path = "/insertOrder")
  public CommonResult<Order> insertOrder(@RequestBody Order order) {

    CommonResult<Order> commonResult = new CommonResult<>();
    try {
      orderService.insertOrder(order);
      return commonResult;
    } catch (Exception e) {
      e.printStackTrace();
      commonResult.setCode(ResultCode.FAILURE);
      return commonResult;
    }
  }
  // 查询指定用户的所有订单
  @ResponseBody
  @GetMapping(path = "/selectAllOrdersByUserId/{userid}")
  public CommonResult<List<Order>> selectAllOrdersByUserId(@PathVariable("userid") long userid) {

    CommonResult<List<Order>> commonResult = new CommonResult<>();

    try {
      List<Order> orders = orderService.selectAllOrdersByUserId(userid);
      commonResult.setObj(orders);
      return commonResult;
    } catch (Exception e) {
      e.printStackTrace();
      commonResult.setCode(ResultCode.FAILURE);
      return commonResult;
    }
  }


  //查询订单状态
  @ResponseBody
  @GetMapping(path = "/selectOrderStatusById/{id}")
  public OrderStatus selectOrderStatusById(@PathVariable("id") long id){

    OrderStatus orderStatus = orderService.selectOrderStatusById(id);


    return orderStatus;

  }
  @ResponseBody
  @PostMapping(path = "/orderBegin/{addressid}/{id}")
  public CommonResult<Order> orderBegin(@PathVariable("addressid") String addressid,@PathVariable("id") long id){

    CommonResult<Order> commonResult = new CommonResult<>();
    commonResult.setCode(ResultCode.FAILURE); //修改为默认为失败
    String lockkey="lockproduct_1";
    RLock lock = redissonClient.getLock(lockkey);

    try{

      lock.lock();
      orderService.orderBegin(addressid,id);
    }finally{
      lock.unlock();
    }



    //最后在修改回来为成功
    commonResult.setCode(ResultCode.SUCCESS);
    return commonResult;
  }

  //查询订单数
  @ResponseBody
  @GetMapping(path = "/selectOrderCount")
  public int selectOrderCount(){

    return orderService.selectOrderCount();
  }

  //查询某一天的交易额
  @ResponseBody
  @GetMapping(path = "/selectDealMoneyByCreated/{created}")
  public BigDecimal selectDealMoneyByCreated(@PathVariable("created") String created){

    return orderService.selectDealMoneyByCreated(created);
  }

  //查询今天的日期
  @ResponseBody
  @GetMapping(path = "/selectNowDate")
  public String selectNowDate(){

    return orderService.selectNowDate();
  }

  //查询近7天的日期
  @ResponseBody
  @GetMapping(path = "/selectDateBysevenDay")
  public List<String> selectDateBysevenDay(){


    return orderService.selectDateBysevenDay();
  }


}
