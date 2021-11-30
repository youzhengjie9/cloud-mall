package com.boot.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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

  /**
   * sentinel文档
   * https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81
   *
   * 目前最高压测结果为490qps
   */
  @ResponseBody
  @PostMapping(path = "/orderBegin/{addressid}/{id}")
  @SentinelResource(value = "orderBegin",blockHandler = "orderBeginExceptionHandle")  //sentinel对流量进行控制，有效防止高并发产生的问题
  public CommonResult<Order> orderBegin(@PathVariable("addressid") String addressid,@PathVariable("id") long id){

    CommonResult<Order> commonResult = new CommonResult<>();
    commonResult.setCode(ResultCode.FAILURE); //修改为默认为失败
    String lockkey="lockorder_"+id; //锁订单，防止用户并发攻击提交订单，id为userid

    RLock lock = redissonClient.getLock(lockkey);

    try{

      if(lock.isLocked()) //如果被锁，则说明已经有线程提交订单，所以直接返回即可
      {
        Order order = new Order();
        order.setGoodsInfo("锁还没被释放,提交失败");
        commonResult.setObj(order);
        commonResult.setCode(ResultCode.FAILURE);
        return commonResult;
      }else {
        //如果没有被锁，说明没有线程提交订单，此时我们在加锁即可
        lock.lock(); //加锁
        orderService.orderBegin(addressid,id);
      }

    }finally{
      if(lock.isHeldByCurrentThread()){ //如果当前线程持有锁则解锁
        lock.unlock();
      }

    }

    //最后在修改回来为成功
    commonResult.setCode(ResultCode.SUCCESS);
    return commonResult;
  }

  public CommonResult<Order> orderBeginExceptionHandle(String addressid, long id, BlockException ex){

    CommonResult<Order> commonResult = new CommonResult<>();
    Order order = new Order();
    order.setGoodsInfo("当前提交订单接口并发数过高,暂时限流中,请稍后再进行操作");
    commonResult.setObj(order);
    commonResult.setCode(ResultCode.FAILURE);
    System.out.println("限流："+order);
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

  @ResponseBody
  @GetMapping(path = "/selectAllOrderBylimit/{page}/{limit}")
  public List<Order> selectAllOrderBylimit(@PathVariable("page") int page,
                                           @PathVariable("limit") int limit){

    List<Order> orders = orderService.selectAllOrderBylimit(page, limit);
    return orders;
  }

  //查询指定订单
  @ResponseBody
  @GetMapping(path = "/selectOrderById/{id}")
  public Order selectOrderById(@PathVariable("id") long id){

    Order order = orderService.selectOrderById(id);
    return order;
  }
  //修改订单状态
  @ResponseBody
  @GetMapping(path = "/updateOrderStatus/{id}/{statusid}")
  public String updateOrderStatus(@PathVariable("id") long id,
                                  @PathVariable("statusid") long statusid){

    orderService.updateOrderStatus(id, statusid);

    return "success";
  }


}
