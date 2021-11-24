package com.boot.filter;

import com.alibaba.fastjson.JSONObject;
import com.boot.constant.LoginType;
import com.boot.data.CommonResult;
import com.boot.feign.log.notFallback.LoginLogFeign;
import com.boot.feign.user.fallback.AuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserAuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.LoginLog;
import com.boot.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 游政杰
 */
@Slf4j
@Component
@Order(9)
@WebFilter(urlPatterns = {"/web/**","/admin/**","/pear/**"},filterName = "rememberFilter")
public class RememberFilter extends OncePerRequestFilter {

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    private final String key = "@%&^=*remember-cloudmall=@#&%"; // 密钥，切勿泄露出去

    private final String REMEMBER_KEY = "REMEMBER_"; // 记住我的Redis key前缀

    @Autowired
    @Lazy //解决Spring循环依赖
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    @Lazy
    private UserAuthorityFallbackFeign userAuthorityFallbackFeign;

    @Autowired
    @Lazy
    private AuthorityFallbackFeign authorityFallbackFeign;

    @Autowired
    @Lazy
    private LoginLogFeign loginLogFeign;

    /**
     * 记住我拦截器类
     *
     * @param session
     * @param request
     */
    private void autoLoginByRemember(HttpSession session, HttpServletRequest request) {

        try { // 判断当前ip的用户是否登录
            String s = springSecurityUtil.currentUser(session);

        } catch (Exception e) {
            // 异常了就是没有登录，没有登录就要检查redis中有没有该ip的记住我记录
            Object token =
                    (String) redisTemplate.opsForValue().get(REMEMBER_KEY + IpUtils.getIpAddr(request));

            // 解析token
            if (token == null || token.equals("")) { // 被删除了或者过期了

                log.info("请重新认证");
            } else {
                try {
                    String json = AesUtil.aesDecrypt((String) token, key);
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    String username = (String) jsonObject.get("username");
                    String password = (String) jsonObject.get("password");

                    if (userFallbackFeign.selectPasswordByuserName(username).equals(password)) { // 验证成功

                        /** 逆向破解SpringSecurity验证,进行直接放行，绕过springSecurity验证 */
                        long userid = userFallbackFeign.selectUserIdByName(username);
                        CommonResult<Integer> integerCommonResult = userAuthorityFallbackFeign.selectAuthorityIdByUserId(userid);
                        int authorityid = integerCommonResult.getObj();
                        // 查询出来权限
                        CommonResult<String> stringCommonResult = authorityFallbackFeign.selectAuthorityNameById(authorityid);
                        String authority = stringCommonResult.getObj();
                        SecurityContextImpl securityContext = new SecurityContextImpl();
                        User user = new User(username, password, AuthorityUtils.createAuthorityList(authority));
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        user, password, AuthorityUtils.createAuthorityList(authority));
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
                        // 存放authentication到SecurityContextHolder
                        SecurityContextHolder.getContext()
                                .setAuthentication(usernamePasswordAuthenticationToken);
                        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
                        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

                        // 登录日志，走拦截器的登录类型都是记住我类型,此处可以用spring aop
                        String ipAddr = IpUtils.getIpAddr(request);
                        LoginLog loginLog = new LoginLog();
                        loginLog.setId(SnowId.nextId());
                        loginLog.setIp(ipAddr);
                        loginLog.setAddress(IpToAddressUtil.getCityInfo(ipAddr));
                        loginLog.setBrowser(BrowserOS.getBrowserName(request));
                        loginLog.setOs(BrowserOS.getOsName(request));
                        loginLog.setUsername(username);
                        Date d = new Date();
                        java.sql.Date date = new java.sql.Date(d.getTime());
                        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String t = fm.format(date);
                        loginLog.setTime(t);
                        loginLog.setType(LoginType.REMEMBER_LOGIN); // 登录类型为2
                        loginLogFeign.insertLoginLog(loginLog);

                    } else {
                        log.info("请重新认证");
                    }
                } catch (Exception ex) {
                    log.info("请重新认证");
                }
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession();
        this.autoLoginByRemember(session, request);

        filterChain.doFilter(request,response);
    }

}
