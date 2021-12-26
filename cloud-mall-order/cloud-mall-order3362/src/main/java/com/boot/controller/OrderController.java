package com.boot.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.enums.OrderStatusConstant;
import com.boot.enums.ResultConstant;
import com.boot.pojo.Address;
import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import com.boot.service.OrderService;
import com.google.common.util.concurrent.RateLimiter;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(path = "/feign/order")
@Api("订单服务api")
public class OrderController {

  @Autowired private OrderService orderService;

  @Autowired
  private RedissonClient redissonClient;

  private static RateLimiter rateLimiter;

  static {

    rateLimiter=RateLimiter.create(20.0);
  }

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
  @PostMapping(path = "/orderBegin/{addressid}/{id}/{couponsid}")
//  @SentinelResource(value = "orderBegin",blockHandler = "orderBeginExceptionHandle")  //sentinel对流量进行控制，有效防止高并发产生的问题

  public CommonResult<Order> orderBegin(@PathVariable("addressid") String addressid,@PathVariable("id") long id,
                                        @PathVariable("couponsid") String couponsid) throws InterruptedException, ParseException {
    CommonResult<Order> commonResult = new CommonResult<>();
    commonResult.setCode(ResultCode.FAILURE); //修改为默认为失败
    if(rateLimiter.tryAcquire()){

      String lockkey="lockorder_"+id; //锁订单，防止用户并发攻击提交订单，id为userid

      RLock lock = redissonClient.getLock(lockkey);

      try{

        if(!lock.tryLock(15,30, TimeUnit.SECONDS)) //如果被锁，则说明已经有线程提交订单，所以直接返回即可
        {
          Order order = new Order();
          order.setGoodsInfo("锁还没被释放,提交失败");
          commonResult.setObj(order);
          commonResult.setCode(ResultCode.FAILURE);
          return commonResult;
        }else {
          //如果没有被锁，说明没有线程提交订单，此时我们在加锁即可
          lock.lock(); //加锁
          long cid = Long.parseLong(couponsid);
          orderService.orderBegin(addressid,id,cid);
        }

      }finally{
        if(lock.isHeldByCurrentThread()){ //如果当前线程持有锁则解锁
          lock.unlock();
        }

      }
      //最后在修改回来为成功
      commonResult.setCode(ResultCode.SUCCESS);
      return commonResult;

    }else {

      return commonResult;
    }


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
  @ResponseBody
  @GetMapping(path = "/selectAllOrderBylimitAndId/{userid}/{page}/{limit}")
  public List<Order> selectAllOrderBylimitAndId(@PathVariable("userid") long userid,
                                                @PathVariable("page") int page,
                                                @PathVariable("limit") int limit){

    List<Order> orders = orderService.selectAllOrderBylimitAndId(userid, page, limit);

    return orders;
  }

  @ResponseBody
  @GetMapping(path = "/selectOrderCountByid/{userid}")
  public int selectOrderCountByid(@PathVariable("userid") long userid){


    return orderService.selectOrderCountByid(userid);
  }


  //查询所有有关退货的订单（包括退货完成的）
  @ResponseBody
  @GetMapping(path = "/selectReturnGoods/{page}/{limit}")
  public List<Order> selectReturnGoods(@PathVariable("page") int page,
                                @PathVariable("limit") int limit){

    return orderService.selectReturnGoods(page, limit);
  }

  //查询有关退货的数量（包括退货完成的）
  @ResponseBody
  @GetMapping(path = "/selectReturnGoodsCount")
  public int selectReturnGoodsCount(){

     return orderService.selectReturnGoodsCount();
  }


  //查询退货并且指定id
  @ResponseBody
  @GetMapping(path = "/selectReturnGoodsById/{id}")
  public Order selectReturnGoodsById(@PathVariable("id") long id){

    return orderService.selectReturnGoodsById(id);
  }


  //同意退货
  @ResponseBody
  @GetMapping(path = "/agreedReturnGoods/{userid}/{orderid}")
  public String agreedReturnGoods(@PathVariable("userid") long userid,
                                  @PathVariable("orderid") long orderid){


    orderService.agreedReturnGoods(userid, orderid);

    return ResultConstant.SUCCESS.getCodeStat();
  }

  //查询订单数
  @ResponseBody
  @GetMapping(path = "/selectOrderCountById/{userid}")
  public int selectOrderCountById(@PathVariable("userid") long userid){

    return orderService.selectOrderCountById(userid);
  }

  //秒杀订单支付
  @ResponseBody
  @GetMapping(path = "/seckillOrder/{addressid}/{seckillsuccessid}/{seckillid}/{userid}")
  public String seckillOrder(@PathVariable("addressid") long addressid ,
                             @PathVariable("seckillsuccessid") long seckillsuccessid,
                             @PathVariable("seckillid") long seckillid,
                             @PathVariable("userid") long userid) throws IOException{

    orderService.seckillOrder(addressid, seckillsuccessid, seckillid, userid);

    return ResultConstant.SUCCESS.getCodeStat();
  }

  @ResponseBody
  @GetMapping(path = "/selectSingleGoodsMoneyTop7")
  public List<BigDecimal> selectSingleGoodsMoneyTop7(){

    return orderService.selectSingleGoodsMoneyTop7();
  }

}
