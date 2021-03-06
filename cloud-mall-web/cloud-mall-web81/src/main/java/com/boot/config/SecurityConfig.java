package com.boot.config;

import com.alibaba.fastjson.JSON;
import com.boot.constant.LoginType;
import com.boot.data.RememberJson;
import com.boot.data.layuiJSON;
import com.boot.feign.log.notFallback.LoginLogFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.filter.VerifyCodeFilter;
import com.boot.pojo.LoginLog;
import com.boot.utils.*;
import lombok.extern.slf4j.Slf4j;
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
 * @author ?????????
 */
@Configuration
@EnableWebSecurity // ??????SpringSecurity?????????
@EnableGlobalMethodSecurity(prePostEnabled = true) // ????????????????????????
@Slf4j
@Order(5)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private LoginLogFeign loginLogFeign;

  @Autowired
  private VerifyCodeFilter verifyCodeFilter;

  private final String key = "@%&^=*remember-cloudmall=@#&%"; // ???????????????????????????

  private final String REMEMBER_KEY = "REMEMBER_"; // ????????????Redis key??????

  private final String GATEWAY_URL="http://localhost:80"; //gateway????????????url

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
    // ???????????????
    http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
    http.formLogin()
        .usernameParameter("username")
        .passwordParameter("password")
        .loginPage(GATEWAY_URL + "/web/login/toLoginPage") // ???????????????
        .loginProcessingUrl("/web/login/login") // ????????????????????????????????????????????????????????????
        // ??????????????????
        .successHandler(
            new AuthenticationSuccessHandler() {
              @Override
              public void onAuthenticationSuccess(
                  HttpServletRequest request,
                  HttpServletResponse httpServletResponse,
                  Authentication authentication)
                  throws IOException, ServletException {
                String val = "";
                String ipAddr = IpUtils.getIpAddr(request); // ??????ip
                log.info("????????????????????????ip?????????" + ipAddr);

                // ??????????????????????????????????????????????????????????????????
                redisTemplate.delete(ipAddr + "_lg");

                UsernamePasswordAuthenticationToken s =
                    (UsernamePasswordAuthenticationToken) authentication;

                String name = s.getName(); // ?????????????????????

                // ?????????????????????
                String psd = userFallbackFeign.selectPasswordByuserName(name);

                log.debug("ip?????????" + ipAddr + "????????????");

                // ???????????????????????????????????????

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
                loginLog.setType(LoginType.NORMAL_LOGIN); // ???SecurityConfig?????????????????????????????????
                loginLogFeign.insertLoginLog(loginLog);

                // ??????cookie+Redis?????????????????????
                String rememberme = request.getParameter("remember");
                if (rememberme != null && rememberme.equals("true")) { // ?????????????????????
                  try {
                    setRememberme(name, psd, request, httpServletResponse); // ???????????????
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }

                // ajax??????
                layuiJSON layuiJSON = new layuiJSON();
                layuiJSON.setMsg("????????????");
                layuiJSON.setSuccess(true);
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().append(JSON.toJSONString(layuiJSON));
              }
            })
        //        .failureForwardUrl("/web/login/LoginfailPage")
        .failureHandler(
            new AuthenticationFailureHandler() {
              @Override
              public void onAuthenticationFailure(
                  HttpServletRequest httpServletRequest,
                  HttpServletResponse httpServletResponse,
                  AuthenticationException e)
                  throws IOException, ServletException {

                // ajax??????
                layuiJSON layuiJSON = new layuiJSON();
                layuiJSON.setMsg("????????????");
                layuiJSON.setSuccess(false);
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().append(JSON.toJSONString(layuiJSON));
              }
            })
        .and()
        // ?????????????????????druid??????sql???????????????????????????
        .csrf()
        .ignoringAntMatchers("/druid/**")
        .and()
        .logout()
        .logoutUrl("/web/logout/logout")
        .logoutSuccessUrl(GATEWAY_URL + "/web/index/")
        .logoutSuccessHandler(
            new LogoutSuccessHandler() {
              @Override
              public void onLogoutSuccess(
                  HttpServletRequest httpServletRequest,
                  HttpServletResponse httpServletResponse,
                  Authentication authentication)
                  throws IOException, ServletException {

                log.info("????????????");

                // ?????????Redis?????????????????????
                redisTemplate.delete(REMEMBER_KEY + IpUtils.getIpAddr(httpServletRequest));
                httpServletResponse.sendRedirect(GATEWAY_URL + "/web/index/");
              }
            })
        .and()
        .authorizeRequests()
        .antMatchers("/druid/**")
        .permitAll()
        // ???????????????????????????????????????spring??????????????????
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

        // ??????????????????????????????
        .antMatchers(
            "/myuser/**",
            "/img/**",
            "/web/cart/**",
            "/web/order/**",
            "/web/address/**",
            "/web/logout/logout",
            "/web/center/**",
            "/web/couponsActivity/**",
            "/web/couponsRecord/**",
            "/web/seckill/**",
            "/web/index/buyNowPage/**","/web/chat/**")
        .hasAnyRole("admin", "common", "seller")
        .antMatchers("/web/sliderCaptcha/**")
        .permitAll()

        // ???????????????????????????????????????????????????
        .anyRequest()
        .permitAll()
        .and()
        // ???????????????????????????iframe?????????Druid?????????????????????????????? X-Frame-Options ??????????????? Frame ?????????
        .headers()
        .frameOptions()
        .disable();
  }

  // ??????SpringSecurity??????????????????????????????????????????????????????????????????
  private void setRememberme(
          String name, String psd, HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    RememberJson rememberJSON = new RememberJson();
    rememberJSON.setUsername(name);
    rememberJSON.setPassword(psd);
    String jsonStr = JSON.toJSONString(rememberJSON);
    String token = AesUtil.aesEncrypt(jsonStr, key); // ????????????????????????

    String ipAddr = IpUtils.getIpAddr(request);

    // ?????????ip??????key
    redisTemplate.opsForValue().set(REMEMBER_KEY + ipAddr, token, 60 * 60 * 3, TimeUnit.SECONDS);
  }
}
