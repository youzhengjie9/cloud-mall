package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Cart;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/web/order")
@Api("订单服务 web api")
public class OrderController {

  private static final String CHECK_ORDER_KEY = "check_order_"; // 核对订单key的前缀

  @Autowired private RedisTemplate redisTemplate;

  @Autowired private SpringSecurityUtil springSecurityUtil;

  @Autowired private UserFallbackFeign userFallbackFeign;

  @GetMapping(path = "/getCheckOrderInfo")
  @ResponseBody
  public String getCheckOrderInfo(String js, HttpSession session) {
    // js就是我们需要核对订单的json串，放入redis让toCheckOrder方法获取

    String currentUser = springSecurityUtil.currentUser(session);

    long id = userFallbackFeign.selectUserIdByName(currentUser);

    redisTemplate.opsForValue().set(CHECK_ORDER_KEY+id,js);

    return js;
  }

  @GetMapping(path = "/toCheckOrder")
  public String toCheckOrder(HttpSession session,Model model) {

      String currentUser = springSecurityUtil.currentUser(session);
      long id = userFallbackFeign.selectUserIdByName(currentUser);
      String json = (String) redisTemplate.opsForValue().get(CHECK_ORDER_KEY + id);
      JSONArray jsonArray = JSONArray.parseArray(json);

      String jsonString = JSON.toJSONString(jsonArray.get(0));

      JSONObject jsonObject = JSONObject.parseObject(jsonString);
      System.out.println(jsonObject.get("imgUrl"));

      return "client/view/newpage/checkOrder";
  }
}
