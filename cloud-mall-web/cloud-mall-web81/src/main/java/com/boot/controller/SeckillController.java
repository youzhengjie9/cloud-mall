package com.boot.controller;

import com.boot.annotation.RateLimiter;
import com.alibaba.fastjson.JSON;
import com.boot.data.layuiJSON;
import com.boot.feign.search.fallback.SeckillSearchFallbackFeign;
import com.boot.feign.seckill.notfallback.SeckillFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.Product;
import com.boot.pojo.Seckill;
import com.boot.pojo.VersionInfo;
import com.boot.utils.IpUtils;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/web/seckill")
@Api("秒杀服务 web api")
public class SeckillController {

    @Autowired
    private SeckillSearchFallbackFeign seckillSearchFallbackFeign;

    @Autowired
    private SeckillFeign seckillFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    private final int from=0; //分页起始，从id=from+1 开始

    private final int size=15;//分页大小

    private final String PAGE_SECKILL_TEXT = "pageSeckillText_";

    private final String PAGE_SECKILL_COUNT = "pageSeckillCount_";

    private static com.google.common.util.concurrent.RateLimiter rateLimiter;

    static {
        rateLimiter=com.google.common.util.concurrent.RateLimiter.create(50.0);
    }

    @GetMapping(path = "/toSearchSeckillPage")
    public String toSearchSeckillPage(Model model, String text, HttpServletRequest request) throws IOException {

        if(text==null||text=="")
        {
            text="^";
        }
        String ipAddr = IpUtils.getIpAddr(request);
        List<Seckill> seckills = seckillSearchFallbackFeign.searchAllSeckill(text, from, size,ipAddr);
        model.addAttribute("seckills",seckills);

        int pageProductCount = (int) redisTemplate.opsForValue().get(PAGE_SECKILL_COUNT+ipAddr);//获取分页前查询的总数

        int x=size-from; //计算出每一页数量的Max
        int pagecount=(pageProductCount%x==0)?pageProductCount/x:(pageProductCount/x)+1; //页的总数

        model.addAttribute("pagecount",pagecount);

        model.addAttribute("curPage",1); //默认第一页

        return "client/view/newpage/seckill_list";
    }

    /**
     * 秒杀接口：http://localhost:80/web/seckill/doSeckill/10086
     * 测试：8000个线程+1次循环的最高qps为1008/s，平均qps为832.9/s
     * @param seckillId
     * @param session
     * @return
     */
    @ResponseBody
    @GetMapping(path = "/doSeckill/{seckillId}",produces = "application/json; charset=utf-8")
    public String doSeckill(@PathVariable("seckillId") long seckillId, HttpSession session){
        layuiJSON json = layuiJSON.getInstance();
        if (rateLimiter.tryAcquire()) {

            try{
            String currentUser = springSecurityUtil.currentUser(session);

            long userid = userFallbackFeign.selectUserIdByName(currentUser);

                String res = seckillFeign.doSeckill(seckillId, userid);

                json.setSuccess(true);
                json.setMsg(res);
                return JSON.toJSONString(json);
            }catch (Exception e){
                e.printStackTrace();
                json.setSuccess(false);
                json.setMsg("秒杀失败");
                return JSON.toJSONString(json);
            }

        }else {
            json.setSuccess(false);
            json.setMsg("秒杀失败");
            return JSON.toJSONString(json);
        }

    }


//    @RateLimiter(permitsPerSecond = 1.0) //限流
    @GetMapping(path = "/seckillDetail/{seckillid}")
    public String seckillDetail(@PathVariable("seckillid") long seckillid,Model model) throws ParseException {
        Seckill seckill=null;
        try{
            seckill= seckillSearchFallbackFeign.searchSeckilltoDetailByseckillId(seckillid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = seckill.getStartTime();
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = simpleDateFormat.parse(s);
        if(now.before(start)){ //说明活动没开始
            model.addAttribute("state",0);
        }else {
            model.addAttribute("state",1); //说明活动开始了
        }


        model.addAttribute("seckill",seckill);
        return "client/view/newpage/seckill_detail";
    }




}
