package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.boot.data.layuiJSON;
import com.boot.enums.ResultConstant;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.feign.user.notFallback.UserDetailFeign;
import com.boot.pojo.User;
import com.boot.pojo.UserDetail;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.apache.lucene.search.uhighlight.LabelledCharArrayMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/web/center")
@Api("个人中心 web api")
public class CenterController {

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private UserDetailFeign userDetailFeign;
    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @RequestMapping(path = "/toBaseInfo")
    public String toBaseInfo(Model model, HttpSession session)
    {
        String currentUser = springSecurityUtil.currentUser(session);
        long userid = userFallbackFeign.selectUserIdByName(currentUser);
        User user = userFallbackFeign.selectUserInfoById(userid);

        model.addAttribute("user",user); //传递当前用户信息



        return "client/view/newpage/base_info";
    }

    @ResponseBody
    @PostMapping(path = "/updateSex")
    public String updateSex(String sex,HttpSession session)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            long userid = userFallbackFeign.selectUserIdByName(currentUser);

            UserDetail userDetail = new UserDetail();
             userDetail.setUserid(userid);
             userDetail.setSex(Integer.parseInt(sex));
             userDetailFeign.updateSex(userDetail);

            layuiJSON.setMsg("修改成功");
            layuiJSON.setSuccess(true);
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setMsg("修改失败");
            layuiJSON.setSuccess(false);
            return JSON.toJSONString(layuiJSON);
        }





    }


    @ResponseBody
    @PostMapping(path = "/updateSignature")
    public String updateSignature(String signature,HttpSession session)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            long userid = userFallbackFeign.selectUserIdByName(currentUser);
            UserDetail userDetail = new UserDetail();
            userDetail.setUserid(userid);
            userDetail.setSignature(signature);
            userDetailFeign.updateSignature(userDetail);

            layuiJSON.setMsg("修改成功");
            layuiJSON.setSuccess(true);
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setMsg("修改失败");
            layuiJSON.setSuccess(false);
            return JSON.toJSONString(layuiJSON);
        }

    }



    @ResponseBody
    @PostMapping(path = "/updateIcon")
    public String updateIcon(HttpSession session, MultipartFile file)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try{
            String currentUser = springSecurityUtil.currentUser(session);
            long userid = userFallbackFeign.selectUserIdByName(currentUser);


            layuiJSON.setMsg("修改成功");
            layuiJSON.setSuccess(true);
            return JSON.toJSONString(layuiJSON);
        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setMsg("修改失败");
            layuiJSON.setSuccess(false);
            return JSON.toJSONString(layuiJSON);
        }

    }

}
