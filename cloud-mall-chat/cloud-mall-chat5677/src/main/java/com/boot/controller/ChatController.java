package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.feign.user.fallback.UserAuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(path = "/chat")
@Api("人工客服服务api")
public class ChatController {

    private static final String ONLINE_COUNT="online_count"; //在线人数key

    private static final String WEBSOCKET_IDS="websocket_ids"; //在线客服的id

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private UserAuthorityFallbackFeign userAuthorityFallbackFeign;

    @RequestMapping(path = "/toChat")
    public String toChat(Model model, HttpSession session)
    {
        String currentUser = springSecurityUtil.currentUser(session);

        long userid = userFallbackFeign.selectUserIdByName(currentUser);

        model.addAttribute("userid",userid);
        if(!StringUtils.isEmpty(currentUser)){
            model.addAttribute("currentUser",currentUser);
        }
        String o = (String) redisTemplate.opsForValue().get(WEBSOCKET_IDS);
        List<String> names = JSON.parseArray(o, String.class);

        model.addAttribute("names",names);

        return "chat";
    }


    //获取刷新数据，并以JSON的形式传给前端
    @ResponseBody
    @GetMapping(path = "/refreshData",produces = "application/json; charset=utf-8")
    public String refreshData()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",(Integer)redisTemplate.opsForValue().get(ONLINE_COUNT));
        String o = (String) redisTemplate.opsForValue().get(WEBSOCKET_IDS);
        List<String> names = JSON.parseArray(o, String.class);
        jsonObject.put("list",names);
        return jsonObject.toJSONString();
    }

}
