package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boot.data.layuiJSON;
import com.boot.feign.order.fallback.AddressFallbackFeign;
import com.boot.feign.search.fallback.SeckillSearchFallbackFeign;
import com.boot.feign.seckill.fallback.SeckillFallbackFeign;
import com.boot.feign.seckill.notfallback.SeckillFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.*;
import com.boot.utils.IpUtils;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(path = "/web/seckill")
@Api("秒杀服务 web api")
public class SeckillController {

  private final int SECKILL_CARTID = -9999; // 秒杀商品的购物车id

  @Autowired private SeckillSearchFallbackFeign seckillSearchFallbackFeign;

  @Autowired private SeckillFeign seckillFeign;

  @Autowired private SeckillFallbackFeign seckillFallbackFeign;

  @Autowired private SpringSecurityUtil springSecurityUtil;

  @Autowired private UserFallbackFeign userFallbackFeign;

  @Autowired private AddressFallbackFeign addressFallbackFeign;

  @Autowired private RedisTemplate redisTemplate;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  private final int from = 0; // 分页起始，从id=from+1 开始

  private final int size = 15; // 分页大小

  private final int from2 = 0; // 分页起始，从id=from+1 开始

  private final int size2 = 5; // 分页大小

  private final String PAGE_SECKILL_TEXT = "pageSeckillText_";

  private final String PAGE_SECKILL_COUNT = "pageSeckillCount_";

  private static final String SECKILL_SEARCH_TEXT="seckill_search_text_";//搜索文本

  private static com.google.common.util.concurrent.RateLimiter rateLimiter;

  static {
    rateLimiter = com.google.common.util.concurrent.RateLimiter.create(50.0);
  }

  @GetMapping(path = "/toSearchSeckillPage")
  public String toSearchSeckillPage(Model model, String text, HttpServletRequest request)
      throws IOException {

    if (text == null || text == "") {
      text = "^";
    }
    String ipAddr = IpUtils.getIpAddr(request);
    List<Seckill> seckills = seckillSearchFallbackFeign.searchAllSeckill(text, from, size, ipAddr);
    model.addAttribute("seckills", seckills);

    int pageProductCount =
        (int) redisTemplate.opsForValue().get(PAGE_SECKILL_COUNT + ipAddr); // 获取分页前查询的总数

    int x = size - from; // 计算出每一页数量的Max
    int pagecount =
        (pageProductCount % x == 0) ? pageProductCount / x : (pageProductCount / x) + 1; // 页的总数

    model.addAttribute("pagecount", pagecount);

    model.addAttribute("curPage", 1); // 默认第一页

    return "client/view/newpage/seckill_list";
  }

  /**
   * 秒杀接口：http://localhost:80/web/seckill/doSeckill/10086 测试：8000个线程+1次循环的最高qps为1008/s，平均qps为832.9/s
   *
   * @param seckillId
   * @param session
   * @return
   */
  @ResponseBody
  @GetMapping(path = "/doSeckill/{seckillId}", produces = "application/json; charset=utf-8")
  public String doSeckill(@PathVariable("seckillId") long seckillId, HttpSession session) {
    layuiJSON json = layuiJSON.getInstance();
    if (rateLimiter.tryAcquire()) {

      try {
        String currentUser = springSecurityUtil.currentUser(session);

        long userid = userFallbackFeign.selectUserIdByName(currentUser);

        String res = seckillFeign.doSeckill(seckillId, userid);

        json.setSuccess(true);
        json.setMsg(res);
        return JSON.toJSONString(json);
      } catch (Exception e) {
        e.printStackTrace();
        json.setSuccess(false);
        json.setMsg("秒杀失败");
        return JSON.toJSONString(json);
      }

    } else {
      json.setSuccess(false);
      json.setMsg("秒杀失败");
      return JSON.toJSONString(json);
    }
  }

  //    @RateLimiter(permitsPerSecond = 1.0) //限流
  @GetMapping(path = "/seckillDetail/{seckillid}")
  public String seckillDetail(@PathVariable("seckillid") long seckillid, Model model)
      throws ParseException {
    Seckill seckill = null;
    try {
      seckill = seckillSearchFallbackFeign.searchSeckilltoDetailByseckillId(seckillid);
      seckill.setSeckillId(seckillid);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String s = seckill.getStartTime();
    Date now = new Date(System.currentTimeMillis());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date start = simpleDateFormat.parse(s);
    if (now.before(start)) { // 说明活动没开始
      model.addAttribute("state", 0);
    } else {
      model.addAttribute("state", 1); // 说明活动开始了
    }

    model.addAttribute("seckill", seckill);
    return "client/view/newpage/seckill_detail";
  }

  // 秒杀成功页
  @GetMapping(path = "/toSeckillSuccessList")
  public String toSeckillSuccessList(HttpSession session, Model model) {

    String currentUser = springSecurityUtil.currentUser(session);
    long userid = userFallbackFeign.selectUserIdByName(currentUser);

    List<SeckillSuccess> seckillSuccesses =
        seckillFallbackFeign.selectSeckillSuccessByUseridAndLimit(userid, 0, 5);

    model.addAttribute("seckillSuccesses", seckillSuccesses);

    int pageProductCount =
        seckillFallbackFeign.selectSeckillSuccessCountByUserid(userid); // 获取分页前查询的总数

    int x = size2 - from2; // 计算出每一页数量的Max
    int pagecount =
        (pageProductCount % x == 0) ? pageProductCount / x : (pageProductCount / x) + 1; // 页的总数

    model.addAttribute("pagecount", pagecount);

    model.addAttribute("curPage", 1); // 默认第一页

    return "client/view/newpage/seckill_success_list";
  }

  @ResponseBody
  @GetMapping(path = "/seckillSuccessData", produces = "application/json; charset=utf-8")
  public String seckillSuccessData(
      @RequestParam(value = "from", defaultValue = "1") int from,
      @RequestParam(value = "size", defaultValue = "5") int size,
      HttpSession session) {
    int curPage = from; // 当前页
    from = size * (from - 1);

    String s = springSecurityUtil.currentUser(session);
    long userid = userFallbackFeign.selectUserIdByName(s);

    List<SeckillSuccess> seckillSuccesses =
        seckillFallbackFeign.selectSeckillSuccessByUseridAndLimit(userid, from, size);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("curPage", curPage); // 传入当前页

    jsonObject.put("seckillSuccesses", seckillSuccesses);

    int pageOrderCount =
        seckillFallbackFeign.selectSeckillSuccessCountByUserid(userid); // 获取分页前查询的总数

    int x = size; // 计算出每一页数量的Max
    int pagecount =
        (pageOrderCount % x == 0) ? pageOrderCount / x : (pageOrderCount / x) + 1; // 页的总数

    jsonObject.put("pagecount", pagecount);

    int curPageGroup = (curPage % 5 == 0) ? curPage / 5 : (curPage / 5) + 1; // 当前页属于第几组
    // 1-5为第一组导航 ,6-10为第二组以此类推
    int pageGroup = (pagecount % 5 == 0) ? pagecount / 5 : (pagecount / 5) + 1; // 能够分多少组导航

    jsonObject.put("curPageGroup", curPageGroup);
    jsonObject.put("pageGroup", pageGroup);

    // 比如总共15页,5个一组，15%5=0;此时租后一组就为5个
    int odd = (pagecount % 5 == 0) ? 5 : pagecount % 5; // 求最后一组有多少页
    jsonObject.put("odd", odd);

    return JSON.toJSONString(jsonObject);
  }

  // 去秒杀支付页
  @GetMapping(path = "/doPay/{seckillsuccessid}/{seckillId}")
  public String doPay(@PathVariable("seckillsuccessid") long seckillsuccessid,
                      @PathVariable("seckillId") long seckillId,HttpSession session, Model model)
      throws IOException {

    String currentUser = springSecurityUtil.currentUser(session);
    long id = userFallbackFeign.selectUserIdByName(currentUser);

    Seckill seckill = seckillSearchFallbackFeign.searchSeckilltoDetailByseckillId(seckillId);

    Cart cart = new Cart();

    long id1 = Long.valueOf(SECKILL_CARTID); // 购物车id

    String imgUrl = seckill.getImg();
    String goodsInfo = seckill.getSeckillName();
    String goodsParams = "该商品为秒杀商品";
    int goodsCount = 1;
    BigDecimal singleGoodsMoney = seckill.getPrice();

    cart.setId(id1);
    cart.setImgUrl(imgUrl);
    cart.setGoodsInfo(goodsInfo);
    cart.setGoodsParams(goodsParams);
    cart.setGoodsCount(goodsCount);
    cart.setSingleGoodsMoney(singleGoodsMoney);

    // 把解析到的list集合传入到前端
    model.addAttribute("cart", cart);

    // 查询收货地址
    List<Address> addresses = addressFallbackFeign.selectAddressByUserId(id);
    model.addAttribute("addresses", addresses);

    model.addAttribute("totalmoney",singleGoodsMoney);

    model.addAttribute("seckillId",seckillId);
    model.addAttribute("seckillsuccessid",seckillsuccessid);

    return "client/view/newpage/seckill_pay";
  }


  @ResponseBody
  @GetMapping(path = "/seckillOrder",produces = "application/json; charset=utf-8")
  public String seckillOrder(@RequestParam("addressid") long addressid ,
                             @RequestParam("seckillsuccessid") long seckillsuccessid,
                             @RequestParam("seckillid") long seckillid,
                             HttpSession session)
  {

    layuiJSON layuiJSON = new layuiJSON();

    try {
      String currentUser = springSecurityUtil.currentUser(session);
      long userid = userFallbackFeign.selectUserIdByName(currentUser);

      JSONObject jsonObject = new JSONObject();
      jsonObject.put("addressid",addressid);
      jsonObject.put("seckillsuccessid",seckillsuccessid);
      jsonObject.put("seckillid",seckillid);
      jsonObject.put("userid",userid);
      String json = jsonObject.toJSONString();

      //发送到秒杀订单消息队列中
      rabbitTemplate.convertAndSend("seckillOrder_exchange","seckillOrder_key",json);

      layuiJSON.setSuccess(true);
      return JSON.toJSONString(layuiJSON);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("22222222222");

      layuiJSON.setSuccess(false);
      return JSON.toJSONString(layuiJSON);
    }

  }


  @ResponseBody
  @GetMapping(path = "/searchSeckillByCondition",produces = "application/json; charset=utf-8")
  public String searchSeckillByCondition(@RequestParam(value = "from",defaultValue = "1") int from,
                                          @RequestParam(value = "size",defaultValue = "15") int size,
                                          HttpServletRequest request)
          throws IOException{

    String ip = IpUtils.getIpAddr(request);
    int curPage=from;  //当前页

    from=size*(from-1);

    JSONObject jsonObject = new JSONObject();

    jsonObject.put("curPage",curPage);//传入当前页

    String text=""; //搜索文本

    text = (String) redisTemplate.opsForValue().get(SECKILL_SEARCH_TEXT+ip);

    List<Seckill> seckills = seckillSearchFallbackFeign.searchAllSeckill(text, from, size, ip);



    jsonObject.put("seckills",seckills);

    int pageProductCount = (int) redisTemplate.opsForValue().get(PAGE_SECKILL_COUNT+ip);//获取分页前查询的总数

    int x=size; //计算出每一页数量的Max
    int pagecount=(pageProductCount%x==0)?pageProductCount/x:(pageProductCount/x)+1; //页的总数

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






}
