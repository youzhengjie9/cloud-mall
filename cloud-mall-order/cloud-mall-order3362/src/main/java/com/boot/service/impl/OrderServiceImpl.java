package com.boot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boot.constant.ResultCode;
import com.boot.dao.OrderMapper;
import com.boot.data.CommonResult;
import com.boot.feign.order.fallback.AddressFallbackFeign;
import com.boot.feign.product.fallback.CartFallbackFeign;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.feign.product.notFallback.CartFeign;
import com.boot.feign.product.notFallback.ProductFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.feign.user.notFallback.UserFeign;
import com.boot.pojo.Address;
import com.boot.pojo.Cart;
import com.boot.pojo.Order;
import com.boot.pojo.OrderStatus;
import com.boot.service.OrderService;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static final String CHECK_ORDER_KEY = "check_order_"; // 核对订单key的前缀

    @Autowired
    private AddressFallbackFeign addressFallbackFeign;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private CartFallbackFeign cartFallbackFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void insertOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    @Override
    public List<Order> selectAllOrdersByUserId(long userid) {
        return orderMapper.selectAllOrdersByUserId(userid);
    }

    @Override
    public OrderStatus selectOrderStatusById(long id) {
        return orderMapper.selectOrderStatusById(id);
    }

    //分布式事务
    @GlobalTransactional(name = "seata_orderbegin",rollbackFor = Exception.class)
    @Override
    public void orderBegin(String addressid ,long id) {


        String json = (String) redisTemplate.opsForValue().get(CHECK_ORDER_KEY + id);
        JSONArray jsonArray = JSONArray.parseArray(json);

        int size = jsonArray.size(); // 获取这个json数组有多少个对象

        Address address = addressFallbackFeign.selectAddressByid(Long.valueOf(addressid));

        // 解析json数组
        for (int i = 0; i < size; i++) {
            String jsonString = JSON.toJSONString(jsonArray.get(i));
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            long cartid = Long.valueOf((String) jsonObject.get("id")); //商品id
            CommonResult<Cart> cartCommonResult = cartFallbackFeign.selectCartByCartId(cartid);
            Cart cart1 = cartCommonResult.getObj();
            long id1 = cart1.getProductid(); //商品id

            String imgUrl = (String) jsonObject.get("imgUrl");
            String goodsInfo = (String) jsonObject.get("goodsInfo");
            String goodsParams = (String) jsonObject.get("goodsParams");
            int goodsCount = Integer.valueOf((String) jsonObject.get("goodsCount"));
            BigDecimal singleGoodsMoney = new BigDecimal((String) jsonObject.get("singleGoodsMoney"));

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

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            java.sql.Date date1 = new java.sql.Date(date.getTime());
            String created = dateFormat.format(date1);
            order.setCreated(created);
            order.setUserid(id);
            order.setProductid(id1);
            order.setStatusid(1);

            orderMapper.insertOrder(order);

            //分布式事务
            //当下单成功就要减库存，和减用户余额

            BigDecimal userMoney = userFallbackFeign.selectUserMoneyByUserId(id);

            if (userMoney.compareTo(singleGoodsMoney)==-1) { //如果userMoney<singleGoodsMoney

                //此时购买不了，进行回滚
                throw new RuntimeException("余额不足，购买失败");
            }else { //反之可以购买

                //调用多个服务
                productFeign.decrNumberByPid(id1,goodsCount); //减库存
                userFeign.decrMoneyByUserId(id,singleGoodsMoney.toString()); //减余额
                cartFeign.deleteCartByCartId(cartid); //移除购物车

            }
        }



    }
}
