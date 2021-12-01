package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.order.fallback.OrderFallbackFeign;
import com.boot.feign.order.notFallback.OrderFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Order;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 游政杰
 */
@Controller
@RequestMapping(path = "/pear")
@Api("订单控制器")
public class OrderController {

    @Autowired
    private OrderFallbackFeign orderFallbackFeign;

    @Autowired
    private OrderFeign orderFeign;

    @RequestMapping(path = "/toOrderManager")
    public String toOrderManager()
    {

        return "back/order_list";
    }


    @ResponseBody
    @GetMapping(path = "/orderdata")
    public String orderdata(@RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "limit", defaultValue = "6") int limit,
                            @RequestParam(value = "orderid", defaultValue = "") String orderid){

        page=limit*(page-1);
        layuiData<Order> layuiData = new layuiData();
        if(StringUtils.isEmpty(orderid)){

            List<Order> orders = orderFallbackFeign.selectAllOrderBylimit(page, limit);
            int count = orderFallbackFeign.selectOrderCount();

            layuiData.setData(orders);
            layuiData.setCount(count);
            layuiData.setCode(0);
            layuiData.setMsg("");

            return JSON.toJSONString(layuiData);
        }else {
            CopyOnWriteArrayList<Order> list = new CopyOnWriteArrayList<>();
            Order order = orderFallbackFeign.selectOrderById(Long.parseLong(orderid));
            list.add(order);
            layuiData.setData(list);
            layuiData.setCount(1);
            layuiData.setCode(0);
            layuiData.setMsg("");

            return JSON.toJSONString(layuiData);
        }

    }


    @RequestMapping(path = "/toOrderInfo/{orderid}")
    public String toOrderInfo(@PathVariable("orderid") String orderid, Model model)
    {
        Order order = orderFallbackFeign.selectOrderById(Long.parseLong(orderid));
        model.addAttribute("order",order);
        return "back/module/order_info";
    }

    //发货
    @ResponseBody
    @GetMapping(path = "/sendGoods/{orderid}")
    public String sendGoods(@PathVariable("orderid") String orderid)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try {
            orderFeign.updateOrderStatus(Long.parseLong(orderid),2);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("修改发货成功");
            return JSON.toJSONString(layuiJSON);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("修改发货失败");
            return JSON.toJSONString(layuiJSON);
        }

    }

    //取消订单
    @ResponseBody
    @GetMapping(path = "/cancelOrder/{orderid}")
    public String cancelOrder(@PathVariable("orderid") String orderid)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try {
            orderFeign.updateOrderStatus(Long.parseLong(orderid),4);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("取消成功");
            return JSON.toJSONString(layuiJSON);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("取消失败");
            return JSON.toJSONString(layuiJSON);
        }

    }


    @RequestMapping(path = "/toReturnGoods")
    public String toReturnGoods()
    {

        return "back/returnGoods_list";
    }


    //查询所有有关退货的订单（包括退货完成的）
    @ResponseBody
    @GetMapping(path = "/returnGoodsData")
    public String returnGoodsData(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "6") int limit,
                                  @RequestParam(value = "orderid", defaultValue = "") String orderid){

        page=limit*(page-1);
        layuiData<Order> layuiData = new layuiData();

        if(StringUtils.isEmpty(orderid)){ //查询全部

            List<Order> orders = orderFallbackFeign.selectReturnGoods(page, limit);

            int count = orderFallbackFeign.selectReturnGoodsCount();

            layuiData.setData(orders);
            layuiData.setCount(count);
            layuiData.setCode(0);
            layuiData.setMsg("");

            return JSON.toJSONString(layuiData);
        }else {

            ArrayList<Order> list = new ArrayList<>();
            Order order = orderFallbackFeign.selectReturnGoodsById(Long.parseLong(orderid));


            if(order==null){ //防止数据差不到
                list=null;
                layuiData.setCount(0);
            }else {
                list.add(order);
                layuiData.setCount(1);
            }
            layuiData.setData(list);
            layuiData.setCode(0);
            layuiData.setMsg("");

            return JSON.toJSONString(layuiData);
        }
    }






}
