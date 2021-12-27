package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.data.CommonResult;
import com.boot.data.layuiJSON;
import com.boot.feign.order.fallback.AddressFallbackFeign;
import com.boot.feign.order.fallback.OrderFallbackFeign;
import com.boot.feign.order.notFallback.OrderFeign;
import com.boot.feign.product.fallback.CartFallbackFeign;
import com.boot.feign.system.fallback.CouponsRecordFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Address;
import com.boot.pojo.Cart;
import com.boot.pojo.CouponsRecord;
import com.boot.pojo.Order;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** @author 游政杰 */
@Controller
@RequestMapping(path = "/web/order")
@Api("订单服务 web api")
public class OrderController {

  private static final String CHECK_ORDER_KEY = "check_order_"; // 核对订单key的前缀

  private static final String TOTAL_MONEY="totalMoney";

  private static final String TOTAL_COUNT="totalCount";

  private final int from=0; //分页起始，从id=from+1 开始

  private final int size=5;//分页大小

  @Autowired private RedisTemplate redisTemplate;

  @Autowired private AddressFallbackFeign addressFallbackFeign;

  @Autowired
  private CouponsRecordFallbackFeign couponsRecordFallbackFeign;

  @Autowired
  private CartFallbackFeign cartFallbackFeign;

  @Autowired
  private OrderFallbackFeign orderFallbackFeign;

  @Autowired
  private OrderFeign orderFeign;

  @Autowired private SpringSecurityUtil springSecurityUtil;

  @Autowired private UserFallbackFeign userFallbackFeign;


  @RequestMapping(path = "/toOrderPage")
  public String toOrderPage(Model model,HttpSession session)
  {
    String currentUser = springSecurityUtil.currentUser(session);

    long userid = userFallbackFeign.selectUserIdByName(currentUser);

    List<Order> orderList = orderFallbackFeign.selectAllOrderBylimitAndId(userid, 0, 5);

    model.addAttribute("orderList",orderList);

    int pageProductCount = orderFallbackFeign.selectOrderCountById(userid);//获取分页前查询的总数

    int x=size-from; //计算出每一页数量的Max
    int pagecount=(pageProductCount%x==0)?pageProductCount/x:(pageProductCount/x)+1; //页的总数

    model.addAttribute("pagecount",pagecount);

    model.addAttribute("curPage",1); //默认第一页

    model.addAttribute("username",currentUser);

    return "client/view/newpage/order";
  }

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

    model.addAttribute("username",currentUser);

    List<Cart> carts = new ArrayList<>();
    // 解析json数组
    if(size>3)
    {
      for (int i = 0; i < 3; i++) {
        Cart cart = new Cart();
        String jsonString = JSON.toJSONString(jsonArray.get(i));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        long id1 = Long.valueOf((String) jsonObject.get("id")); //购物车id

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

    }else if (size>=1&&size<=3){

      for (int i = 0; i < size; i++) {
        Cart cart = new Cart();
        String jsonString = JSON.toJSONString(jsonArray.get(i));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        long id1 = Long.valueOf((String) jsonObject.get("id")); //购物车id

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

    }


    int x=3;
    int pagecount=(size%x==0)?size/x:(size/x)+1; //页的总数

    model.addAttribute("pagecount",pagecount);

    model.addAttribute("curPage",1); //默认第一页


    // 把解析到的list集合传入到前端
    model.addAttribute("carts", carts);

    String totalmoney = (String) redisTemplate.opsForValue().get(TOTAL_MONEY + id);

    String totalCount = (String) redisTemplate.opsForValue().get(TOTAL_COUNT + id);

    model.addAttribute("totalmoney",totalmoney);
    model.addAttribute("totalCount",totalCount);

    // 查询收货地址
    List<Address> addresses = addressFallbackFeign.selectAddressByUserId(id);
    model.addAttribute("addresses", addresses);


    //查询优惠券
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String nowtime = simpleDateFormat.format(new Date(System.currentTimeMillis()));
    List<CouponsRecord> couponsRecords = couponsRecordFallbackFeign.selectCouponsRecordByUserIdAndLimit(0, 3, id, 0,nowtime);

    model.addAttribute("couponsRecords",couponsRecords);

    return "client/view/newpage/checkOrder";
  }


  @ResponseBody
  @GetMapping(path = "/checkOrderData",produces = "application/json; charset=utf-8")
  public String checkOrderData(@RequestParam(value = "from",defaultValue = "1") int from,
                               HttpSession session){
    int curPage=from;  //当前页
    int pagesize=3;
    String currentUser = springSecurityUtil.currentUser(session);
    long id = userFallbackFeign.selectUserIdByName(currentUser);
    String json = (String) redisTemplate.opsForValue().get(CHECK_ORDER_KEY + id);
    JSONArray jsonArray = JSONArray.parseArray(json);

    int size = jsonArray.size(); // 获取这个json数组有多少个对象

    from=pagesize*(from-1);

    List<Cart> carts = new ArrayList<>();
    // 解析json数组
    //0 1 2 3 4
    if(from+pagesize-1<=size-1){ //说明下一组能够3个为一组

      for (int i = from; i < from+3; i++) {
        Cart cart = new Cart();
        String jsonString = JSON.toJSONString(jsonArray.get(i));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        long id1 = Long.valueOf((String) jsonObject.get("id")); //购物车id

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


    }else{ //说明下一组不够3个为一组

      int n=size-from;
      for (int i = from; i < from+n; i++) {
        Cart cart = new Cart();
        String jsonString = JSON.toJSONString(jsonArray.get(i));
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        long id1 = Long.valueOf((String) jsonObject.get("id")); //购物车id

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
    }


    String s = springSecurityUtil.currentUser(session);
    long userid = userFallbackFeign.selectUserIdByName(s);


    JSONObject jsonObject = new JSONObject();

    jsonObject.put("curPage",curPage);//传入当前页

    jsonObject.put("carts",carts);


    int pageOrderCount = jsonArray.size();

    int pagecount=(pageOrderCount%pagesize==0)?pageOrderCount/pagesize:(pageOrderCount/pagesize)+1; //页的总数

    jsonObject.put("pagecount",pagecount);

    int curPageGroup=(curPage%5==0)?curPage/5:(curPage/5)+1; //当前页属于第几组
    //1-5为第一组导航 ,6-10为第二组以此类推
    int pageGroup=(pagecount%5==0)?pagecount/5:(pagecount/5)+1; //能够分多少组导航

    jsonObject.put("curPageGroup",curPageGroup);
    jsonObject.put("pageGroup",pageGroup);

    //比如总共15页,5个一组，15%5=0;此时租后一组就为5个
    int odd=(pagecount%5==0)?5:pagecount%5; //求最后一组有多少页
    jsonObject.put("odd",odd);

    return JSON.toJSONString(jsonObject);

  }


  //下订单主要逻辑
  @GetMapping(path = "/orderbegin")
  @ResponseBody
  public CommonResult<Address> orderbegin(String addressid,String couponsid,HttpSession session)
  {
    CommonResult<Address> commonResult = new CommonResult<>();
    String currentUser = springSecurityUtil.currentUser(session);
    long id = userFallbackFeign.selectUserIdByName(currentUser);
    try {
      orderFeign.orderBegin(addressid,id,couponsid);
      return commonResult;
    } catch (Exception e) {
      e.printStackTrace();
      commonResult.setCode(ResultCode.FAILURE);
      return commonResult;
    }
  }

  @ResponseBody
  @GetMapping(path = "/orderData",produces = "application/json; charset=utf-8")
  public String orderData(@RequestParam(value = "from",defaultValue = "1") int from,
                          @RequestParam(value = "size",defaultValue = "5") int size,
                          HttpSession session)
  {
    int curPage=from;  //当前页
    from=size*(from-1);

    String s = springSecurityUtil.currentUser(session);
    long userid = userFallbackFeign.selectUserIdByName(s);

    List<Order> orders = orderFallbackFeign.selectAllOrderBylimitAndId(userid, from, size);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("curPage",curPage);//传入当前页

    jsonObject.put("orders",orders);



    int pageOrderCount = orderFallbackFeign.selectOrderCountByid(userid);//获取分页前查询的总数

    int x=size; //计算出每一页数量的Max
    int pagecount=(pageOrderCount%x==0)?pageOrderCount/x:(pageOrderCount/x)+1; //页的总数

    jsonObject.put("pagecount",pagecount);

    int curPageGroup=(curPage%5==0)?curPage/5:(curPage/5)+1; //当前页属于第几组
    //1-5为第一组导航 ,6-10为第二组以此类推
    int pageGroup=(pagecount%5==0)?pagecount/5:(pagecount/5)+1; //能够分多少组导航

    jsonObject.put("curPageGroup",curPageGroup);
    jsonObject.put("pageGroup",pageGroup);

    //比如总共15页,5个一组，15%5=0;此时租后一组就为5个
    int odd=(pagecount%5==0)?5:pagecount%5; //求最后一组有多少页
    jsonObject.put("odd",odd);

    return JSON.toJSONString(jsonObject);

  }


  @ResponseBody
  @GetMapping(path = "/returnGoods/{id}")
  public String returnGoods(@PathVariable("id") long id)
  {


    layuiJSON layuiJSON = new layuiJSON();


    try {
      orderFeign.updateOrderStatus(id,5);
      layuiJSON.setMsg("申请退货成功");
      layuiJSON.setSuccess(true);
      return JSON.toJSONString(layuiJSON);
    } catch (Exception e) {
      e.printStackTrace();
      layuiJSON.setMsg("申请退货失败");
      layuiJSON.setSuccess(false);
      return JSON.toJSONString(layuiJSON);
    }

  }



  @ResponseBody
  @GetMapping(path = "/cancelGoods/{id}")
  public String cancelGoods(@PathVariable("id") long id)
  {


    layuiJSON layuiJSON = new layuiJSON();


    try {
      orderFeign.updateOrderStatus(id,1); //默认变成待发货状态
      layuiJSON.setMsg("撤销退货成功");
      layuiJSON.setSuccess(true);
      return JSON.toJSONString(layuiJSON);
    } catch (Exception e) {
      e.printStackTrace();
      layuiJSON.setMsg("撤销退货失败");
      layuiJSON.setSuccess(false);
      return JSON.toJSONString(layuiJSON);
    }

  }





}
