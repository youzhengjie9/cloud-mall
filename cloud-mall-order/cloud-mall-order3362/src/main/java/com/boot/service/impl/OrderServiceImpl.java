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
import com.boot.feign.search.fallback.SeckillSearchFallbackFeign;
import com.boot.feign.seckill.notfallback.SeckillFeign;
import com.boot.feign.system.fallback.CouponsRecordFallbackFeign;
import com.boot.feign.system.notfallback.CouponsRecordFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.feign.user.notFallback.UserFeign;
import com.boot.pojo.*;
import com.boot.service.OrderService;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 游政杰
 */
@Transactional
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static final String CHECK_ORDER_KEY = "check_order_"; // 核对订单key的前缀

    @Autowired
    @Lazy
    private AddressFallbackFeign addressFallbackFeign;

    @Autowired
    private ProductFeign productFeign;

    @Autowired
    private ProductFallbackFeign productFallbackFeign;

    @Autowired
    @Lazy
    private UserFeign userFeign;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private CartFallbackFeign cartFallbackFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private CouponsRecordFallbackFeign couponsRecordFallbackFeign;

    @Autowired
    private CouponsRecordFeign couponsRecordFeign;

    @Autowired
    @Lazy
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SeckillFeign seckillFeign;

    @Autowired
    private SeckillSearchFallbackFeign seckillSearchFallbackFeign;

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
    public void orderBegin(String addressid ,long id,long couponsid) throws ParseException {


        try{

            String json = (String) redisTemplate.opsForValue().get(CHECK_ORDER_KEY + id);
            JSONArray jsonArray = JSONArray.parseArray(json);

            int size = jsonArray.size(); // 获取这个json数组有多少个对象

            Address address = addressFallbackFeign.selectAddressByid(Long.valueOf(addressid));
            CouponsRecord couponsRecord=null;
            boolean usecoupons=false;
            if(couponsid!=-1){
                couponsRecord=couponsRecordFallbackFeign.selectCouponsRecord(couponsid, id, 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date start = simpleDateFormat.parse(couponsRecord.getCouponsActivity().getStartTime());
                Date now = new Date(System.currentTimeMillis());
                Date end = simpleDateFormat.parse(couponsRecord.getCouponsActivity().getEndTime());
                Calendar cal = Calendar.getInstance();
                cal.setTime(end);
                cal.add(Calendar.DATE,1);
                end =cal.getTime(); //结束时间加一天
                if(now.after(start)&&now.before(end)) //校验优惠券的使用时间
                {
                    usecoupons=true;
                }
            }

            // 解析json数组
            for (int i = 0; i < size; i++) {
                String jsonString = JSON.toJSONString(jsonArray.get(i));
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                long cartid = Long.valueOf((String) jsonObject.get("id")); //
                CommonResult<Cart> cartCommonResult = cartFallbackFeign.selectCartByCartId(cartid);
                Cart cart1 = cartCommonResult.getObj();
                long id1 = cart1.getProductid(); //商品id

                String imgUrl = (String) jsonObject.get("imgUrl");
                String goodsInfo = (String) jsonObject.get("goodsInfo");
                String goodsParams = (String) jsonObject.get("goodsParams");
                int goodsCount = Integer.valueOf((String) jsonObject.get("goodsCount"));
                BigDecimal singleGoodsMoney = new BigDecimal((String) jsonObject.get("singleGoodsMoney"));
                if(i==0&&usecoupons==true){ //给第一个商品进行使用优惠券&&可以使用优惠券
                    BigDecimal amount = couponsRecord.getCouponsActivity().getAmount();
                    singleGoodsMoney=singleGoodsMoney.subtract(amount);
                }
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
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setId(1);
                order.setOrderStatus(orderStatus);

                orderMapper.insertOrder(order);

                //分布式事务
                //当下单成功就要减库存，和减用户余额

                BigDecimal userMoney = userFallbackFeign.selectUserMoneyByUserId(id);
                Product product = productFallbackFeign.selectProductByPid(id1);
                int number = product.getNumber();

                if (userMoney.compareTo(singleGoodsMoney)==-1||number<goodsCount) { //如果userMoney<singleGoodsMoney

                    //此时购买不了，进行回滚
                    throw new RuntimeException("购买失败");
                }else { //反之可以购买

                    //调用多个服务
                    productFeign.decrNumberByPid(id1,goodsCount); //减库存
                    userFeign.decrMoneyByUserId(id,singleGoodsMoney.toString()); //减余额
                    cartFeign.deleteCartByCartId(cartid); //移除购物车
                    if(i==0&&usecoupons==true)
                    {
                        couponsRecordFeign.updateCouponsRecordUsetype(couponsid,1,created);
                    }
                }
            }

        }catch (Exception e){
            throw new RuntimeException();
        }

    }

    @Override
    public int selectOrderCount() {
        return orderMapper.selectOrderCount();
    }

    @Override
    public BigDecimal selectDealMoneyByCreated(String created) {
        return orderMapper.selectDealMoneyByCreated(created);
    }

    @Override
    public String selectNowDate() {
        return orderMapper.selectNowDate();
    }

    @Override
    public List<String> selectDateBysevenDay() {
        return orderMapper.selectDateBysevenDay();
    }

    @Override
    public List<Order> selectAllOrderBylimit(int page, int limit) {
        return orderMapper.selectAllOrderBylimit(page, limit);
    }

    @Override
    public Order selectOrderById(long id) {
        return orderMapper.selectOrderById(id);
    }

    @Override
    public void updateOrderStatus(long id, long statusid) {
        orderMapper.updateOrderStatus(id, statusid);
    }

    @Override
    public List<Order> selectAllOrderBylimitAndId(long userid, int page, int limit) {
        return orderMapper.selectAllOrderBylimitAndId(userid, page, limit);
    }

    @Override
    public int selectOrderCountByid(long userid) {
        return orderMapper.selectOrderCountByid(userid);
    }

    @Override
    public List<Order> selectReturnGoods(int page, int limit) {
        return orderMapper.selectReturnGoods(page, limit);
    }

    @Override
    public int selectReturnGoodsCount() {
        return orderMapper.selectReturnGoodsCount();
    }

    @Override
    public Order selectReturnGoodsById(long id) {
        return orderMapper.selectReturnGoodsById(id);
    }

    //全局分布式事务
    @GlobalTransactional(name = "seata_agreedReturnGoods",rollbackFor = Exception.class)
    @Override
    public void agreedReturnGoods(long userid, long orderid) {


        //通过orderid查询订单
        Order order = orderMapper.selectOrderById(orderid);

        //获得该订单总价值singleGoodsMoney
        BigDecimal singleGoodsMoney = order.getSingleGoodsMoney();

        //修改订单状态为退款完成
        orderMapper.updateOrderStatus(orderid,6);

        //调用user服务，所以这里要受到全局事务的保证
        userFeign.incrMoneyByUserId(userid,singleGoodsMoney.toString());

    }

    @Override
    public int selectOrderCountById(long userid) {
        return orderMapper.selectOrderCountById(userid);
    }


    @GlobalTransactional(name = "seata_seckillOrder",rollbackFor = Exception.class)
    @Override
    public void seckillOrder(long addressid ,long seckillsuccessid,long seckillid,long userid) throws IOException {


        try{
            Address address = addressFallbackFeign.selectAddressByid(addressid);
            Seckill seckill = seckillSearchFallbackFeign.searchSeckilltoDetailByseckillId(seckillid);
            BigDecimal price = seckill.getPrice();
            Order order = new Order();
            order.setId(SnowId.nextId());
            order.setImgUrl(seckill.getImg());
            order.setGoodsInfo(seckill.getSeckillName());
            order.setGoodsParams("该商品为秒杀商品");
            order.setGoodsCount(1);
            order.setSingleGoodsMoney(price);
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
            order.setUserid(userid);
            order.setProductid(seckillid);
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(1);
            order.setOrderStatus(orderStatus);

            orderMapper.insertOrder(order);

            //分布式事务
            //当下单成功就要减库存，和减用户余额
            BigDecimal userMoney = userFallbackFeign.selectUserMoneyByUserId(userid);

            if (userMoney.compareTo(price)==-1) { //如果userMoney<singleGoodsMoney

                //此时购买不了，进行回滚
                throw new RuntimeException("支付失败");
            }else { //反之可以购买

                //调用多个服务

                //删除秒杀成功记录
                int res = seckillFeign.deleteSeckillSuccess(seckillsuccessid);
                if(res==0){
                    throw new RuntimeException();
                }
                //减余额
                userFeign.decrMoneyByUserId(userid,price.toString());

            }
        }catch (Exception e){
            throw new RuntimeException();
        }

    }

    @Override
    public List<BigDecimal> selectSingleGoodsMoneyTop7() {
        return orderMapper.selectSingleGoodsMoneyTop7();
    }

}
