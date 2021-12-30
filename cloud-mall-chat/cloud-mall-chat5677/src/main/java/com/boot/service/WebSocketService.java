package com.boot.service;

import com.alibaba.fastjson.JSON;
import com.boot.config.MyEndpointConfigure;
import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.UserAuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 游政杰
 * 配置websocket
 */
@ServerEndpoint(value = "/websocket/{userId}",configurator= MyEndpointConfigure.class)
@Component
@Slf4j
public class WebSocketService {

  //用户session
  private static Map<String,Session> userSessionMap=new ConcurrentHashMap<>();
  //客服session
  public static Map<String, Session> kefuSessionMap = new ConcurrentHashMap<>();

  private static final String ONLINE_COUNT="online_count"; //在线客服人数key

  private static final String WEBSOCKET_IDS="websocket_ids"; //在线客服的id

  private static final String KEFU_KEY="客服-";

  private static final String CUR_KEFU="cur_kefu_"; //用户当前聊天的客服

  private static final String CUR_USER="cur_user_"; //客服当前聊天的用户

  private static final String KEFU_SET="kefu_set"; //客服集合

  @Autowired
  private UserAuthorityFallbackFeign userAuthorityFallbackFeign;

  @Autowired
  private RedisTemplate redisTemplate;

  private static volatile AtomicInteger usercount=new AtomicInteger(0); //用户数


  @PostConstruct
  public void init()
  {

    redisTemplate.opsForValue().set(ONLINE_COUNT,0);
    redisTemplate.opsForValue().set(WEBSOCKET_IDS,"");

  }

  @OnOpen
  public void onOpen(@PathParam("userId") String userId, Session session) {
    //查询用户权限
    CommonResult<Integer> integerCommonResult = userAuthorityFallbackFeign.selectAuthorityIdByUserId(Long.valueOf(userId));

    Integer authority = integerCommonResult.getObj();
    if(authority==1||authority==3){ //说明当前session为客服的session

      userId=KEFU_KEY+userId;
      log.info("WebSocketService onOpen: " + userId);
      Long online=null;
      if (kefuSessionMap == null) {
        kefuSessionMap = new ConcurrentHashMap<String, Session>();
      }
      if(!kefuSessionMap.containsKey(userId)){
        kefuSessionMap.put(userId, session);
        redisTemplate.opsForValue().set(WEBSOCKET_IDS, JSON.toJSONString(kefuSessionMap.keySet()));
        online = redisTemplate.opsForValue().increment(ONLINE_COUNT,1);
        redisTemplate.opsForSet().add(KEFU_SET,userId); //把客服放到redis集合里去
        log.info("当前客服数："+online);
      }

    }else { //说明为普通用户
      log.info("WebSocketService onOpen: " + userId);
      if(userSessionMap == null){
        userSessionMap=new ConcurrentHashMap<>();
      }
      if(!userSessionMap.containsKey(userId)){

        userSessionMap.put(userId,session);
        int usercount = WebSocketService.usercount.incrementAndGet();

        //随机抽取一个客服，并移除集合，防止下一个用户也和这个客服聊天
        String kefuid= (String) redisTemplate.opsForSet().pop(KEFU_SET);

        if(!StringUtils.isEmpty(kefuid)){
          redisTemplate.opsForValue().set(CUR_KEFU+userId,kefuid);
          redisTemplate.opsForValue().set(CUR_USER+kefuid,userId);
        }else {


        }
        log.info("当前用户数："+usercount);
      }
    }

  }

  @OnClose
  public void OnClose(@PathParam("userId") String userId) {
    //查询用户权限
    CommonResult<Integer> integerCommonResult = userAuthorityFallbackFeign.selectAuthorityIdByUserId(Long.valueOf(userId));

    Integer authority = integerCommonResult.getObj();
    if(authority==1||authority==3){

      userId=KEFU_KEY+userId;
      log.info("WebSocketService OnClose: " + userId);
      kefuSessionMap.remove(userId);

      redisTemplate.opsForValue().set(WEBSOCKET_IDS, JSON.toJSONString(kefuSessionMap.keySet()));
      Long online = redisTemplate.opsForValue().decrement(ONLINE_COUNT,1);

      Object uid = redisTemplate.opsForValue().get(CUR_USER + userId);//当前userid为客服id，获取用户id
      redisTemplate.delete(CUR_USER+userId);
      redisTemplate.delete(CUR_KEFU+uid);

      log.info("当前客服数："+online);

    }else {
      log.info("WebSocketService OnClose: " + userId);
      userSessionMap.remove(userId);

      Object kefuid = redisTemplate.opsForValue().get(CUR_KEFU + userId);
      redisTemplate.delete(CUR_USER+kefuid);
      redisTemplate.delete(CUR_KEFU+userId);
      int usercount = WebSocketService.usercount.decrementAndGet();

      log.info("当前用户数："+usercount);

    }

  }

  @OnMessage
  public void OnMessage(@PathParam("userId") String userId, Session session, String message) {
    log.info("WebSocketService OnMessage: " + message);

    //查询用户权限
    CommonResult<Integer> integerCommonResult = userAuthorityFallbackFeign.selectAuthorityIdByUserId(Long.valueOf(userId));

    Integer authority = integerCommonResult.getObj();
    if(authority==1||authority==3){ //如果当前用户是客服，那么就是对用户发信息
      userId=KEFU_KEY+userId;
      Object uid = redisTemplate.opsForValue().get(CUR_USER + userId);

      Session usersession = userSessionMap.get(uid);

      usersession.getAsyncRemote().sendText(message);

    }else {//反之就是对客服发信息

      String kefuid = (String) redisTemplate.opsForValue().get(CUR_KEFU + userId);
      Session kefusession = kefuSessionMap.get(kefuid);
      kefusession.getAsyncRemote().sendText(message);
    }

  }

  @OnError
  public void error(Session session, Throwable t) {

    t.printStackTrace();
  }


}

