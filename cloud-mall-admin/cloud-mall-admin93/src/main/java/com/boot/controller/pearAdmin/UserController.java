package com.boot.controller.pearAdmin;

import com.alibaba.fastjson.JSON;
import com.boot.data.CommonResult;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.user.fallback.AuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserAuthorityFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.feign.user.notFallback.UserFeign;
import com.boot.pojo.Authority;
import com.boot.pojo.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("用户后台控制器")
public class UserController {

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private AuthorityFallbackFeign authorityFallbackFeign;

    @Autowired
    private UserAuthorityFallbackFeign userAuthorityFallbackFeign;

    @RequestMapping(path = "/toUserManager")
    public String toUserManager()
    {

        return "back/user_list";
    }


    @ResponseBody
    @RequestMapping(path = "/allUserData")
    public String allUserData(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "8") int limit,
            @RequestParam(value = "userid", defaultValue = "") String userid) {

        page=limit*(page-1);

        layuiData<User> layuiData = new layuiData();
        if(StringUtils.isBlank(userid)){

            List<User> users = userFallbackFeign.selectAllUserInfo(page, limit);
            int count = userFallbackFeign.selectUserCount();
            layuiData.setCode(0);
            layuiData.setMsg("");
            layuiData.setCount(count);
            layuiData.setData(users);
            return JSON.toJSONString(layuiData);
        }else {

            User user = userFallbackFeign.selectUserInfoById(Long.parseLong(userid));
            if(user==null){

                layuiData.setCount(0);
                layuiData.setData(null);
            }else {
                layuiData.setCount(1);
                ArrayList<User> list = new ArrayList<>();
                list.add(user);
                layuiData.setData(list);
            }
            layuiData.setCode(0);
            layuiData.setMsg("");

            return JSON.toJSONString(layuiData);

        }
    }


    @ResponseBody
    @GetMapping(path = "/enableValid")
    public String enableValid(@RequestParam(value = "userid",required = true) String userid)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try {
            userFeign.updateValid(Long.parseLong(userid),1);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("用户:"+userid+"已生效");
            return JSON.toJSONString(layuiJSON);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("生效失败");
            return JSON.toJSONString(layuiJSON);
        }

    }



    @ResponseBody
    @GetMapping(path = "/disableValid")
    public String disableValid(@RequestParam(value = "userid",required = true) String userid)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try {
            userFeign.updateValid(Long.parseLong(userid),0);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("用户:"+userid+"已失效");
            return JSON.toJSONString(layuiJSON);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("失效失败");
            return JSON.toJSONString(layuiJSON);
        }

    }



    @ResponseBody
    @GetMapping(path = "/deleteUser/{userid}")
    public String deleteUser(@PathVariable("userid") String userid)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try {
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("用户:"+userid+"删除成功");
            return JSON.toJSONString(layuiJSON);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("删除失败");
            return JSON.toJSONString(layuiJSON);
        }

    }

    @RequestMapping(path = "/modifyUserPage")
    public String modifyUserPage(@RequestParam(value = "id",required = true) String id, Model model)
    {
        CommonResult<Integer> integerCommonResult = userAuthorityFallbackFeign.selectAuthorityIdByUserId(Long.parseLong(id));

        Integer curAuthorityid = integerCommonResult.getObj();

        CommonResult<String> commonResult = authorityFallbackFeign.selectAuthorityNameById(curAuthorityid);
        String curAuthorityName = commonResult.getObj();

        Authority curAuthority = new Authority(); //当前权限对象
        curAuthority.setId(curAuthorityid);
        curAuthority.setAuthority(curAuthorityName);
        model.addAttribute("curAuthority",curAuthority);

        List<Authority> authorities = authorityFallbackFeign.selectAuthorityExcludeCurAuthority(curAuthorityid);
        model.addAttribute("authorities",authorities);

        model.addAttribute("id",id);
        return "back/module/editUser";
    }



}
