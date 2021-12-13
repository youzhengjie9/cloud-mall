package com.boot.controller.config;

import com.alibaba.fastjson.JSON;
import com.boot.constant.LoginType;
import com.boot.data.RememberJson;
import com.boot.data.layuiJSON;
import com.boot.feign.log.notFallback.LoginLogFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.filter.VerifyCodeFilter;
import com.boot.pojo.LoginLog;
import com.boot.utils.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 游政杰
 * 后台安全配置
 */
@Configuration
@EnableWebSecurity // 开启SpringSecurity的功能
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启注解控制权限
@Slf4j
@Order(4)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private LoginLogFeign loginLogFeign;


  @Autowired
  private VerifyCodeFilter verifyCodeFilter;

  private final String key = "@%&^=*remember-cloudmall=@#&%"; // 密钥，切勿泄露出去

  private final String REMEMBER_KEY = "REMEMBER_"; // 记住我的Redis key前缀

  private final String GATEWAY_URL="http://localhost:80"; //gateway网关前缀url

  @Autowired
  private UserFallbackFeign userFallbackFeign;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String sql = "select u.username,a.authority from t_user u,t_authority a,"
            + "t_user_authority ua where ua.user_id=u.id "
            + "and ua.authority_id=a.id and u.username =?";
    auth.jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(encoder)
        .usersByUsernameQuery("select username,password,valid from t_user where username = ?")
        .authoritiesByUsernameQuery(sql);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 配置过滤器
    http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
    http.formLogin()
        .usernameParameter("username")
        .passwordParameter("password")
        .loginPage(GATEWAY_URL + "/adminLogin/toLoginPage") // 登录页接口
        .loginProcessingUrl("/adminLogin/login") // 登录过程接口（也就是登录表单提交的接口）
        // 登录成功处理
        .successHandler(
            new AuthenticationSuccessHandler() {
              @SneakyThrows
              @Override
              public void onAuthenticationSuccess(
                  HttpServletRequest request,
                  HttpServletResponse httpServletResponse,
                  Authentication authentication)
                  throws IOException, ServletException {

                String val = "";
                String ipAddr = IpUtils.getIpAddr(request); // 获取ip
                log.info("后台登录成功：访问者ip地址：" + ipAddr);

                // 登入成功之后要把登入验证码的缓存标记给删除掉
                redisTemplate.delete(ipAddr + "_lg");

                UsernamePasswordAuthenticationToken s =
                    (UsernamePasswordAuthenticationToken) authentication;

                String name = s.getName(); // 获取登录用户名

                // 查询数据库密码
                String psd = userFallbackFeign.selectPasswordByuserName(name);

                log.debug("ip地址：" + ipAddr + "后台登录成功");

                // 封装登录日志信息放到数据库

                LoginLog loginLog = new LoginLog();
                loginLog.setId(SnowId.nextId());
                loginLog.setIp(ipAddr);
                loginLog.setAddress(IpToAddressUtil.getCityInfo(ipAddr));
                loginLog.setBrowser(BrowserOS.getBrowserName(request));
                loginLog.setOs(BrowserOS.getOsName(request));
                loginLog.setUsername(name);
                Date d = new Date();
                java.sql.Date date = new java.sql.Date(d.getTime());
                SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String t = fm.format(date);
                loginLog.setTime(t);
                loginLog.setType(LoginType.NORMAL_LOGIN); // 走SecurityConfig类的登录都是正常的登录
                loginLogFeign.insertLoginLog(loginLog);

                // 使用cookie+Redis实现记住我功能
                String rememberme = request.getParameter("remember");
                if (rememberme != null && rememberme.equals("on")) { // 此时激活记住我
                  try {
                    setRememberme(name, psd, request, httpServletResponse); // 记住我实现
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }

                // ajax回调
                layuiJSON layuiJSON = new layuiJSON();
                layuiJSON.setMsg("后台登录成功");
                layuiJSON.setSuccess(true);
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().append(JSON.toJSONString(layuiJSON));
              }
            })
        .failureForwardUrl("/adminLogin/LoginfailPage")
        .failureHandler(
            new AuthenticationFailureHandler() {
              @Override
              public void onAuthenticationFailure(
                  HttpServletRequest httpServletRequest,
                  HttpServletResponse httpServletResponse,
                  AuthenticationException e)
                  throws IOException, ServletException {

                // ajax回调
                layuiJSON layuiJSON = new layuiJSON();
                layuiJSON.setMsg("后台登录失败");
                layuiJSON.setSuccess(false);
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().append(JSON.toJSONString(layuiJSON));
              }
            })
        .and()
        // 不写这段代码，druid监控sql将失效（原因未明）
        .csrf()
        .ignoringAntMatchers("/druid/**")
        .and()
        .logout()
        .logoutUrl("/web/logout/logout")
        .logoutSuccessUrl(GATEWAY_URL + "/adminLogin/toLoginPage")
        .logoutSuccessHandler(
            new LogoutSuccessHandler() {
              @Override
              public void onLogoutSuccess(
                  HttpServletRequest httpServletRequest,
                  HttpServletResponse httpServletResponse,
                  Authentication authentication)
                  throws IOException, ServletException {

                log.info("退出成功");

                // 退出从Redis删除记住我记录
                redisTemplate.delete(REMEMBER_KEY + IpUtils.getIpAddr(httpServletRequest));
                httpServletResponse.sendRedirect(GATEWAY_URL + "/adminLogin/toLoginPage");
              }
            })
        .and()
        .authorizeRequests()
        .antMatchers("/web/index/**")
        .permitAll()
        .antMatchers("/druid/**")
        .permitAll()
        // 这句代码一定要加，为了防止spring过滤静态资源
        .antMatchers(
            "/static/user/**",
            "/static/email/**",
            "/static/plugins/**",
            "/static/user_img/**",
            "/static/article_img/**",
            "/static/assets/**",
            "/static/back/**",
            "/static/user/**",
            "/static/pear-admin/**",
            "/static/component/**",
            "/static/static/**",
            "/static/pear/captcha",
            "/static/config/**",
            "/static/favicon.ico")
        .permitAll()
        // 只有admin权限才能访问(后台管理)
        .antMatchers("/admin/**", "/pear/**")
        .hasRole("admin")
        // 只有登录以后才能访问
        .antMatchers("/myuser/**", "/img/**", "/web/cart/**", "/web/order/**", "/web/address/**")
        .hasAnyRole("admin", "common")
        .antMatchers("/web/sliderCaptcha/**", "/web/logout/logout")
        .permitAll()
        // 其他的任何请求登录不登录都可以访问
        .anyRequest()
        .permitAll()
        .and()
        // 如果不加这段代码，iframe嵌入的Druid监控界面会出现（使用 X-Frame-Options 拒绝网页被 Frame 嵌入）
        .headers()
        .frameOptions()
        .disable();
  }

  // 因为SpringSecurity记住我失效问题没有解决，暂且用手动实现记住我
  private void setRememberme(
          String name, String psd, HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    RememberJson rememberJSON = new RememberJson();
    rememberJSON.setUsername(name);
    rememberJSON.setPassword(psd);
    String jsonStr = JSON.toJSONString(rememberJSON);
    String token = AesUtil.aesEncrypt(jsonStr, key); // 对字符串进行加密

    String ipAddr = IpUtils.getIpAddr(request);

    // 暂且用ip作为key
    redisTemplate.opsForValue().set(REMEMBER_KEY + ipAddr, token, 60 * 60 * 3, TimeUnit.SECONDS);
  }
}
