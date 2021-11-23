package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.annotation.Visitor;
import com.boot.data.layuiJSON;
import com.boot.feign.order.fallback.OrderFallbackFeign;
import com.boot.feign.product.fallback.ProductFallbackFeign;
import com.boot.feign.system.fallback.ImgFallbackFeign;
import com.boot.feign.user.fallback.AuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserAuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.User;
import com.boot.utils.IpUtils;
import com.boot.utils.SpringSecurityUtil;
import com.boot.utils.TimeUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 游政杰
 */
@Api("后台系统控制器")
@Controller
@RequestMapping(path = "/pear")
@CrossOrigin //跨域
@Slf4j
public class PearController {

    //redis存储日期的key
    private final String ECHARTS_DAYS = "echarts_days";
    //redis存储对应的交易额的key
    private final String ECHARTS_COUNTS = "echarts_counts";

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private ProductFallbackFeign productFallbackFeign;

    @Autowired
    private OrderFallbackFeign orderFallbackFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ImgFallbackFeign imgFallbackFeign;

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private UserAuthorityFallbackFeign userAuthorityFallbackFeign;

    @Autowired
    private AuthorityFallbackFeign authorityFallbackFeign;

    @ResponseBody
    @RequestMapping(path = "/userInfoData")
    public String userInfoData(HttpSession session){
        layuiJSON json=new layuiJSON();
        HashMap<String, String> hashMap = new HashMap<>();
        String username = springSecurityUtil.currentUser(session);

        hashMap.put("icon","/static/pear-admin/images/avatar.jpg");

        hashMap.put("username",username);

        json.setMsg(JSON.toJSONString(hashMap));

        return JSON.toJSONString(json);
    }

    private void charts(Model model) {
        //从缓存中查有没有近7天的缓存

        Object var1 = redisTemplate.opsForValue().get(ECHARTS_DAYS);
        Object var2 = redisTemplate.opsForValue().get(ECHARTS_COUNTS);

        List<String> ds = JSON.parseArray((String) var1, String.class);
        List<BigDecimal> cs = JSON.parseArray((String) var2, BigDecimal.class);
        if (ds == null || ds.size() < 7 || cs == null || cs.size() < 7) { //这种情况就要重新查
            List<String> days = orderFallbackFeign.selectDateBysevenDay(); //维护日期
            List<BigDecimal> counts = new ArrayList<>(); //维护交易额
            for (String day : days) {
                BigDecimal count = orderFallbackFeign.selectDealMoneyByCreated(day);
                if(count==null){ //防止BigDecimal类型为null出现错误
                    counts.add(new BigDecimal("0"));
                }else {
                    counts.add(count);
                }

            }
            model.addAttribute("days", days);
            model.addAttribute("counts", counts);
            //让集合变成json放入redis
            String d = JSON.toJSONString(days);
            String c = JSON.toJSONString(counts);
            //让缓存在晚上12点整点就失效
            Long second = TimeUtil.getSecondByCurTimeTo12Point();
            redisTemplate.opsForValue().set(ECHARTS_DAYS, d, second, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(ECHARTS_COUNTS, c, second, TimeUnit.SECONDS);
        } else {
            //如果还有缓存（说明没有过一天），并且数据没被篡改过（都是7个数据），就执行这里的代码
            //这个时候我们只需要更新一下最后一天（也就是今天）的数据即可
            String s = ds.get(6); //获取今天的日期
            BigDecimal decimal = orderFallbackFeign.selectDealMoneyByCreated(s);
            if(decimal==null){
                cs.set(6, new BigDecimal("0"));
            }else {
                cs.set(6, decimal);
            }
            String list = JSON.toJSONString(cs); //记得转换成json
            model.addAttribute("days", ds);
            model.addAttribute("counts", cs);
            //重新放入redis
            Long second = TimeUtil.getSecondByCurTimeTo12Point();
            redisTemplate.opsForValue().set(ECHARTS_COUNTS, list, second, TimeUnit.SECONDS);

        }
    }


    //控制后台，非常重要，访问后台时，会内嵌这个url
    @Operation("进入控制后台界面")
    @RequestMapping(path = "/toconsole")
    public String toconsole(Model model, HttpSession session, HttpServletRequest request) {

        int usercount = userFallbackFeign.selectUserCount(); //用户总数
        model.addAttribute("usercount", usercount);

        int productCount= productFallbackFeign.selectProductCount();
        model.addAttribute("productCount",productCount);
        int ordercount = orderFallbackFeign.selectOrderCount();
        model.addAttribute("ordercount",ordercount);

        String nowDate = orderFallbackFeign.selectNowDate();

        BigDecimal nowDealMoney = orderFallbackFeign.selectDealMoneyByCreated(nowDate);
        model.addAttribute("nowDealMoney",nowDealMoney);


        List<String> days = orderFallbackFeign.selectDateBysevenDay();

        model.addAttribute("days",days);

        charts(model);

        String username = springSecurityUtil.currentUser(session);

        String ipAddr = IpUtils.getIpAddr(request);
        log.debug("ip:" + ipAddr + "访问了后台管理界面");

        java.util.Date date = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        log.debug(time + "   用户名：" + username + "进入admim后台");

        return "back/console";
    }


}





