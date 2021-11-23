package com.boot.controller.pearAdmin;


import com.alibaba.fastjson.JSON;
import com.boot.data.CommonResult;
import com.boot.feign.user.fallback.MenuFallbackFeign;
import com.boot.feign.user.fallback.UserAuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 游政杰
 */
@RestController
@RequestMapping(path = "/pear")
@Api("动态生成后台管理系统菜单的接口")
public class MenuController {

    @Autowired
    private MenuFallbackFeign menuFallbackFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserAuthorityFallbackFeign userAuthorityFallbackFeign;

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    //生成菜单JSON串
    //这里一定要指定produces为application/json，因为默认转发json串是text/plain（不可以用这种）
    @RequestMapping(path = "/menu/json", produces = "application/json")
    @ApiOperation("生成菜单JSON串")
    public String menujson() {

        String username = springSecurityUtil.currentUser(session);
        long userid = userFallbackFeign.selectUserIdByName(username);

        CommonResult<Integer> integerCommonResult = userAuthorityFallbackFeign.selectAuthorityIdByUserId(userid);

        String menuData = menuFallbackFeign.selectMenuDataByAuthority(integerCommonResult.getObj());

        String jsonData = JSON.toJSONString(menuData);
        String s = jsonData.replaceAll("\\\\", "");
         s= s.substring(1,s.length()-1);
        return s;
    }


}
