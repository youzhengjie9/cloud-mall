package com.boot.interceptors;

import com.boot.annotation.Visitor;
import com.boot.config.ScanClassProperties;
import com.boot.feign.log.notFallback.VisitorFeign;
import com.boot.utils.SnowId;
import com.boot.utils.VisitorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/** @author 游政杰 */
@Component
public class VisitorInterceptor implements HandlerInterceptor {

  /** @Visitor注解的实现 */
  @Autowired private ScanClassProperties scanClassProperties;

  @Autowired private RedisTemplate redisTemplate;

  @Autowired private VisitorFeign visitorFeign;

  private final int type = 1;

  private void addVisitor(HttpServletRequest request, String desc) {
    // 添加访客信息
    com.boot.pojo.Visitor visitor = VisitorUtil.getVisitor(request, desc);

    //生成id
    visitor.setId(SnowId.nextId());

    String key = "visit_ip_" + visitor.getVisit_ip() + "_type_" + type;
    String s = (String) redisTemplate.opsForValue().get(key);
    if (StringUtils.isEmpty(s)) {
      visitorFeign.insertVisitor(visitor);
      // 由ip和type组成的key放入redis缓存,5分钟内访问过的不再添加访客
      redisTemplate.opsForValue().set(key, "1", 60 * 5, TimeUnit.SECONDS);
    }
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o)
      throws Exception {

    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView)
      throws Exception {

    // 获取控制器的名字（全类名）
    String controllerName = ((HandlerMethod) o).getBean().getClass().getName();
    // 获取方法名
    String methodName = ((HandlerMethod) o).getMethod().getName();

    Class<?> aClass = Class.forName(controllerName);

    Method[] methods = aClass.getMethods();

    for (Method method : methods) {
      if (method.getName().equals(methodName)) { // 指定方法名

        Visitor visitor = method.getAnnotation(Visitor.class);

        if (visitor != null) { // 如果有这个注解

          String desc = visitor.desc(); // 获取注解值

          this.addVisitor(request, desc);
        }
      }
    }
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {}
}
