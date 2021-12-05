package com.boot.service;

import com.boot.config.MyEndpointConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置websocket
 */
@ServerEndpoint(value = "/websocket/{userId}",configurator= MyEndpointConfigure.class)
@Component
@Slf4j
public class WebSocketService {

    //key为userid  value为user的session
  public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

  @OnOpen
  public void onOpen(@PathParam("userId") String userId, Session session) {
    log.info("WebSocketService onOpen: " + userId);
    if (sessionMap == null) {
      sessionMap = new ConcurrentHashMap<String, Session>();
    }
    sessionMap.put(userId, session);
  }

  @OnClose
  public void OnClose(@PathParam("userId") String userId) {
    log.info("WebSocketService OnClose: " + userId);
    sessionMap.remove(userId);
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

