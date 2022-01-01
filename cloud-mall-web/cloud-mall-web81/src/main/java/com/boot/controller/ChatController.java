package com.boot.controller;

import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.UserAuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/web/chat")
@Api("人工客服 web api")
public class ChatController {

  private static final String KEFU_SET = "kefu_set"; // 客服集合

  @Autowired private RedisTemplate redisTemplate;

  @Autowired
  private SpringSecurityUtil springSecurityUtil;

  @Autowired
  private UserAuthorityFallbackFeign userAuthorityFallbackFeign;

  @Autowired
  private UserFallbackFeign userFallbackFeign;

  //能否进入人工客服
  @ResponseBody
  @GetMapping(path = "/HasGotoKefu")
  public boolean HasGotoKefu(HttpSession session) {

    String currentUser = springSecurityUtil.currentUser(session);
    long userid = userFallbackFeign.selectUserIdByName(currentUser);
    CommonResult<Integer> integerCommonResult = userAuthorityFallbackFeign.selectAuthorityIdByUserId(userid);

    Integer authority = integerCommonResult.getObj();

    if(authority==1||authority==3){ //如果是客服，直接可以进入
      return true;
    }else { //如果是普通用户需要查看当前是否有客服可以连接
      Long size = redisTemplate.opsForSet().size(KEFU_SET);
      if(size>0){ //说明有客服可以连接
        return true;
      }else {
       return false;
      }
    }

  }



}
