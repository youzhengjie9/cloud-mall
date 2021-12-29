package com.boot.service;

import com.alibaba.fastjson.JSON;
import com.boot.config.MyEndpointConfigure;
import lombok.extern.slf4j.Slf4j;
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
 * 配置websocket
 */
@ServerEndpoint(value = "/websocket/{userId}",configurator= MyEndpointConfigure.class)
@Component
@Slf4j
public class WebSocketService {

    //key为userid  value为user的session
  public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

  private static final String ONLINE_COUNT="online_count"; //在线人数key

  private static final String WEBSOCKET_IDS="websocket_ids"; //在线客服的id

  private static final String KEFU_KEY="客服-";

  @Autowired
  private RedisTemplate redisTemplate;


  @PostConstruct
  public void init()
  {

    redisTemplate.opsForValue().set(ONLINE_COUNT,0);
    redisTemplate.opsForValue().set(WEBSOCKET_IDS,"");

  }

  @OnOpen
  public void onOpen(@PathParam("userId") String userId, Session session) {
    userId=KEFU_KEY+userId;
    log.info("WebSocketService onOpen: " + userId);
    Long online=null;
    if (sessionMap == null) {
      sessionMap = new ConcurrentHashMap<String, Session>();
    }
    if(!sessionMap.containsKey(userId)){
      sessionMap.put(userId, session);
      redisTemplate.opsForValue().set(WEBSOCKET_IDS, JSON.toJSONString(sessionMap.keySet()));

       online = redisTemplate.opsForValue().increment(ONLINE_COUNT,1);
    }


    log.info("当前在线人数："+online+"个");
  }

  @OnClose
  public void OnClose(@PathParam("userId") String userId) {
    userId=KEFU_KEY+userId;
    log.info("WebSocketService OnClose: " + userId);
    sessionMap.remove(userId);

    redisTemplate.opsForValue().set(WEBSOCKET_IDS, JSON.toJSONString(sessionMap.keySet()));
    Long online = redisTemplate.opsForValue().decrement(ONLINE_COUNT,1);

    log.info("当前在线人数："+online+"个");
  }

  @OnMessage
  public void OnMessage(@PathParam("userId") String userId, Session session, String message) {
    log.info("WebSocketService OnMessage: " + message);

    for (Session session_ : sessionMap.values()) {
      session_.getAsyncRemote().sendText(message);
    }
  }

  @OnError
  public void error(Session session, Throwable t) {

    t.printStackTrace();
  }



}

