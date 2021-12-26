package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.feign.log.fallback.LoginLogFallbackFeign;
import com.boot.feign.order.fallback.OrderFallbackFeign;
import com.boot.utils.TimeUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("数据图表后台控制器")
public class ChartController {

    //redis存储日期的key
    private final String ECHARTS_DAYS = "echarts_days";
    //redis存储对应的交易额的key
    private final String ECHARTS_COUNTS = "echarts_counts";


    private final String ECHARTS_LoginLog_DAYS = "echarts_LoginLog_days";
    private final String ECHARTS_LoginLog_COUNTS = "echarts_LoginLog_counts";

    @Autowired
    private OrderFallbackFeign orderFallbackFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LoginLogFallbackFeign loginLogFallbackFeign;


    private void charts1(Model model) {

        List<BigDecimal> singles = orderFallbackFeign.selectSingleGoodsMoneyTop7();

        model.addAttribute("singles",singles);

    }

    private void charts2(Model model)
    {
        CopyOnWriteArrayList<Object> browserCounts = new CopyOnWriteArrayList<>();
        List<String> browsers = loginLogFallbackFeign.selectLoginUserBrowser();
        browsers.forEach((e)->{

            int count = loginLogFallbackFeign.selectLoginCountByBrowser(e);

            browserCounts.add(count);
        });
        model.addAttribute("browserCounts",browserCounts);
        model.addAttribute("browsers",browsers);

    }

    private void charts3(Model model) {
        //从缓存中查有没有近7天的缓存

        Object var1 = redisTemplate.opsForValue().get(ECHARTS_LoginLog_DAYS);
        Object var2 = redisTemplate.opsForValue().get(ECHARTS_LoginLog_COUNTS);

        List<String> ds = JSON.parseArray((String) var1, String.class);
        List<Integer> cs = JSON.parseArray((String) var2, Integer.class);
        if (ds == null || ds.size() < 7 || cs == null || cs.size() < 7) { //这种情况就要重新查
            List<String> days = orderFallbackFeign.selectDateBysevenDay(); //维护日期
            List<Integer> counts = new ArrayList<>(); //维护登录次数
            days.forEach((day)->{

                int count = loginLogFallbackFeign.selectLoginCountByTime(day);
                counts.add(count);
            });
            model.addAttribute("LoginLogdays", days);
            model.addAttribute("LoginLogcounts", counts);
            //让集合变成json放入redis
            String d = JSON.toJSONString(days);
            String c = JSON.toJSONString(counts);
            //让缓存在晚上12点整点就失效
            Long second = TimeUtil.getSecondByCurTimeTo12Point();
            redisTemplate.opsForValue().set(ECHARTS_LoginLog_DAYS, d, second, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(ECHARTS_LoginLog_COUNTS, c, second, TimeUnit.SECONDS);
        } else {
            String s = ds.get(6); //获取今天的日期
            int count = loginLogFallbackFeign.selectLoginCountByTime(s);
            cs.set(6, count);
            String list = JSON.toJSONString(cs); //记得转换成json
            model.addAttribute("LoginLogdays", ds);
            model.addAttribute("LoginLogcounts", cs);
            //重新放入redis
            Long second = TimeUtil.getSecondByCurTimeTo12Point();
            redisTemplate.opsForValue().set(ECHARTS_LoginLog_COUNTS, list, second, TimeUnit.SECONDS);

        }
    }

    private void charts4(Model model) {
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

    @RequestMapping(path = "/toCharts")
    public String toCharts(Model model)
    {

        charts1(model);
        charts2(model);
        charts3(model);
        charts4(model);


        return "back/charts";
    }


}
