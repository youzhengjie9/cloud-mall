package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.annotation.Visitor;
import com.boot.data.layuiJSON;
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
@CrossOrigin
@Slf4j
public class PearController {

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

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


    //控制后台，非常重要，访问后台时，会内嵌这个url
    @Operation("进入控制后台界面")
    @RequestMapping(path = "/toconsole")
    public String toconsole(Model model, HttpSession session, HttpServletRequest request) {

//        int usercount = userFallbackFeign.userCount(); //用户总数
//        model.addAttribute("usercount", usercount);

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
