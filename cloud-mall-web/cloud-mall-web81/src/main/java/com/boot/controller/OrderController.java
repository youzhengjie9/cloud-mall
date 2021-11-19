package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.AddressFallbackFeign;
import com.boot.feign.order.fallback.OrderFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Address;
import com.boot.pojo.Cart;
import com.boot.pojo.Order;
import com.boot.utils.SnowId;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** @author 游政杰 */
@Controller
@RequestMapping(path = "/web/order")
@Api("订单服务 web api")
public class OrderController {

  private static final String CHECK_ORDER_KEY = "check_order_"; // 核对订单key的前缀

  private static final String TOTAL_MONEY="totalMoney";

  private static final String TOTAL_COUNT="totalCount";

  @Autowired private RedisTemplate redisTemplate;

  @Autowired private AddressFallbackFeign addressFallbackFeign;

  @Autowired
  private OrderFallbackFeign orderFallbackFeign;

  @Autowired private SpringSecurityUtil springSecurityUtil;

  @Autowired private UserFallbackFeign userFallbackFeign;

  @GetMapping(path = "/getCheckOrderInfo")
  @ResponseBody
  public String getCheckOrderInfo(String js,String totalMoney,String totalCount, HttpSession session) {
    // js就是我们需要核对订单的json串，放入redis让toCheckOrder方法获取



    String currentUser = springSecurityUtil.currentUser(session);

    long id = userFallbackFeign.selectUserIdByName(currentUser);

    redisTemplate.opsForValue().set(CHECK_ORDER_KEY + id, js);

    redisTemplate.opsForValue().set(TOTAL_MONEY+ id,totalMoney);

    redisTemplate.opsForValue().set(TOTAL_COUNT+id,totalCount);

    return js;
  }

  @GetMapping(path = "/toCheckOrder")
  public String toCheckOrder(HttpSession session, Model model) {

    String currentUser = springSecurityUtil.currentUser(session);
    long id = userFallbackFeign.selectUserIdByName(currentUser);
    String json = (String) redisTemplate.opsForValue().get(CHECK_ORDER_KEY + id);
    JSONArray jsonArray = JSONArray.parseArray(json);

    int size = jsonArray.size(); // 获取这个json数组有多少个对象

    List<Cart> carts = new ArrayList<>();
    // 解析json数组
    for (int i = 0; i < size; i++) {
      Cart cart = new Cart();
      String jsonString = JSON.toJSONString(jsonArray.get(i));
      JSONObject jsonObject = JSONObject.parseObject(jsonString);
      long id1 = Long.valueOf((String) jsonObject.get("id"));
      String imgUrl = (String) jsonObject.get("imgUrl");
      String goodsInfo = (String) jsonObject.get("goodsInfo");
      String goodsParams = (String) jsonObject.get("goodsParams");
      int goodsCount = Integer.valueOf((String) jsonObject.get("goodsCount"));
      BigDecimal singleGoodsMoney = new BigDecimal((String) jsonObject.get("singleGoodsMoney"));

      cart.setId(id1);
      cart.setImgUrl(imgUrl);
      cart.setGoodsInfo(goodsInfo);
      cart.setGoodsParams(goodsParams);
      cart.setGoodsCount(goodsCount);
      cart.setSingleGoodsMoney(singleGoodsMoney);

      carts.add(cart);
    }

    // 把解析到的list集合传入到前端
    model.addAttribute("carts", carts);

    String totalmoney = (String) redisTemplate.opsForValue().get(TOTAL_MONEY + id);

    String totalCount = (String) redisTemplate.opsForValue().get(TOTAL_COUNT + id);

    model.addAttribute("totalmoney",totalmoney);
    model.addAttribute("totalCount",totalCount);

    // 查询收货地址
    List<Address> addresses = addressFallbackFeign.selectAddressByUserId(id);
    model.addAttribute("addresses", addresses);


    return "client/view/newpage/checkOrder";
  }

  //下订单主要逻辑
  @GetMapping(path = "/orderbegin")
  @ResponseBody
  public CommonResult<Address> orderbegin(HttpSession session,String addressid)
  {
    CommonResult<Address> commonResult = new CommonResult<>();
    commonResult.setCode(ResultCode.FAILURE); //修改为默认为失败

    String currentUser = springSecurityUtil.currentUser(session);
    long id = userFallbackFeign.selectUserIdByName(currentUser);
    String json = (String) redisTemplate.opsForValue().get(CHECK_ORDER_KEY + id);
    JSONArray jsonArray = JSONArray.parseArray(json);

    int size = jsonArray.size(); // 获取这个json数组有多少个对象

    Address address = addressFallbackFeign.selectAddressByid(Long.valueOf(addressid));

    // 解析json数组
    for (int i = 0; i < size; i++) {
      Cart cart = new Cart();
      String jsonString = JSON.toJSONString(jsonArray.get(i));
      JSONObject jsonObject = JSONObject.parseObject(jsonString);
      long id1 = Long.valueOf((String) jsonObject.get("id")); //商品id
      String imgUrl = (String) jsonObject.get("imgUrl");
      String goodsInfo = (String) jsonObject.get("goodsInfo");
      String goodsParams = (String) jsonObject.get("goodsParams");
      int goodsCount = Integer.valueOf((String) jsonObject.get("goodsCount"));
      BigDecimal singleGoodsMoney = new BigDecimal((String) jsonObject.get("singleGoodsMoney"));
      cart.setId(id1);
      cart.setImgUrl(imgUrl);
      cart.setGoodsInfo(goodsInfo);
      cart.setGoodsParams(goodsParams);
      cart.setGoodsCount(goodsCount);
      cart.setSingleGoodsMoney(singleGoodsMoney);


      Order order = new Order();
      order.setId(SnowId.nextId());
      order.setImgUrl(imgUrl);
      order.setGoodsInfo(goodsInfo);
      order.setGoodsParams(goodsParams);
      order.setGoodsCount(goodsCount);
      order.setSingleGoodsMoney(singleGoodsMoney);
      order.setRealname(address.getRealname());
      order.setPhone(address.getPhone());
      //拼接具体地址
      String ads=address.getProvince()+address.getCity()+address.getArea()+address.getAddress();
      order.setAddress(ads);
      order.setUserid(id);
      order.setProductid(id1);
      order.setStatusid(1);
      orderFallbackFeign.insertOrder(order);

      //分布式事务
      //当下单成功就要减库存，和减用户余额




    }



    //最后在修改回来为成功
    commonResult.setCode(ResultCode.SUCCESS);

    return commonResult;
  }





}
