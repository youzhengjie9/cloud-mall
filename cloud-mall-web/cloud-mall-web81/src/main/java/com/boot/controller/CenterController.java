package com.boot.controller;

import com.alibaba.fastjson.JSON;
import com.boot.data.layuiJSON;
import com.boot.enums.CouponsUseType;
import com.boot.enums.ResultConstant;
import com.boot.feign.system.fallback.CouponsRecordFallbackFeign;
import com.boot.feign.system.fallback.RechargeCardFallbackFeign;
import com.boot.feign.system.fallback.RechargeRecordFallbackFeign;
import com.boot.feign.user.fallback.UserDetailFallbackFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.feign.user.notFallback.UserDetailFeign;
import com.boot.feign.user.notFallback.UserFeign;
import com.boot.pojo.*;
import com.boot.utils.FileUtil;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.uhighlight.LabelledCharArrayMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(path = "/web/center")
@Api("个人中心 web api")
public class CenterController {

    private final String DEFAULT_TIME="1970-1-1";

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    @Autowired
    private RechargeCardFallbackFeign rechargeCardFallbackFeign;

    @Autowired
    private RechargeRecordFallbackFeign rechargeRecordFallbackFeign;

    @Autowired
    private UserDetailFallbackFeign userDetailFallbackFeign;

    @Autowired
    private CouponsRecordFallbackFeign couponsRecordFallbackFeign;

    @Autowired
    private UserDetailFeign userDetailFeign;
    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private UserFeign userFeign;


    //个人信息

    @RequestMapping(path = "/toBaseInfo")
    public String toBaseInfo(Model model, HttpSession session)
    {
        String currentUser = springSecurityUtil.currentUser(session);
        long userid = userFallbackFeign.selectUserIdByName(currentUser);
        User user = userFallbackFeign.selectUserInfoById(userid);

        model.addAttribute("user",user); //传递当前用户信息

        model.addAttribute("username",currentUser);

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

            if(file.getSize()==0) //如果没传入头像
            {
                layuiJSON.setMsg("请传入头像");
                layuiJSON.setSuccess(false);
                return JSON.toJSONString(layuiJSON);
            }else {
                UserDetail oldUserDetail = userDetailFallbackFeign.selectUserDetail(userid);
                String oldIcon = oldUserDetail.getIcon(); //旧头像
                String oldPath = FileUtil.getStaticPathByRedis();
                oldPath+=oldIcon;
                //避免删除到默认头像
                String defaultPath = FileUtil.getStaticPathByRedis();
                defaultPath+="/static/img/user-icon/default-icon.jpg";
                if(!oldPath.equals(defaultPath)){
                    File oldFile = new File(oldPath);
                    oldFile.delete(); //删除旧头像
                }

                //写入新头像
                String newIconPath = FileUtil.writeIcon(file.getOriginalFilename(), file.getBytes());

                UserDetail newUserDetail = new UserDetail();
                newUserDetail.setUserid(userid);
                newUserDetail.setIcon(newIconPath);
                userDetailFeign.updateIcon(newUserDetail);

            }

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


    //修改密码

    @RequestMapping(path = "/toChangePassword")
    public String toChangePassword(Model model, HttpSession session)
    {
        String currentUser = springSecurityUtil.currentUser(session);
        long userid = userFallbackFeign.selectUserIdByName(currentUser);

        model.addAttribute("userid",userid);

        model.addAttribute("username",currentUser);


        return "client/view/newpage/change_password";
    }

    @ResponseBody
    @PostMapping(path = "/updatePassword")
    public String updatePassword(String userid,String oldPassword,
                                 String newPassword1,String newPassword2,
                                 HttpSession session)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try{
            long uid = Long.parseLong(userid);
            if(StringUtils.isBlank(oldPassword)
               || StringUtils.isBlank(newPassword1)
               || StringUtils.isBlank(newPassword2)){

                layuiJSON.setMsg("请确保选项都已输入！");
                layuiJSON.setSuccess(false);
                return JSON.toJSONString(layuiJSON);
            }else {

                boolean check=newPassword1.equals(newPassword2)?true:false;

                if(check) //说明第一次新密码和再次输入密码一致，可以修改
                {
                    //BCrypt加密器
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    String odp = userFallbackFeign.selectPassword(uid);//数据库旧密码
                    boolean matches = bCryptPasswordEncoder.matches(oldPassword, odp);

                    if(matches)
                    {
                        //对新密码进行加密
                        String encodePassword = bCryptPasswordEncoder.encode(newPassword1);

                        User user1 = new User();
                        user1.setId(uid);
                        user1.setPassword(encodePassword);

                        userFeign.updatePassword(user1);
                        layuiJSON.setMsg("修改密码成功");
                        layuiJSON.setSuccess(true);
                        return JSON.toJSONString(layuiJSON);
                    }else {

                        layuiJSON.setMsg("旧密码和数据库保存的密码不一致，请重新输入！");
                        layuiJSON.setSuccess(false);
                        return JSON.toJSONString(layuiJSON);
                    }

                }else {

                    layuiJSON.setMsg("再次输入的密码和新密码不一致，请重新输入！");
                    layuiJSON.setSuccess(false);
                    return JSON.toJSONString(layuiJSON);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setMsg("修改密码失败");
            layuiJSON.setSuccess(false);
            return JSON.toJSONString(layuiJSON);
        }

    }


    //充值

    @RequestMapping(path = "/toWallet")
    public String toWallet(Model model, HttpSession session)
    {
        int page=0;
        int size=10;

        String currentUser = springSecurityUtil.currentUser(session);
        long userid = userFallbackFeign.selectUserIdByName(currentUser);
        BigDecimal userMoney = userFallbackFeign.selectUserMoneyByUserId(userid);

        model.addAttribute("userid",userid);
        model.addAttribute("userMoney",userMoney);

        model.addAttribute("username",currentUser);

        List<RechargeRecord> rechargeRecords = rechargeRecordFallbackFeign.selectUserRechargeRecord(page, size, userid);

        model.addAttribute("rechargeRecords",rechargeRecords);

        return "client/view/newpage/wallet";
    }



    @ResponseBody
    @PostMapping(path = "/recharge")
    public String recharge(String cardNumber,String password,HttpSession session)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try{

            if(StringUtils.isBlank(cardNumber)||StringUtils.isBlank(password))
            {
                layuiJSON.setMsg("请输入卡号和密码，充值失败");
                layuiJSON.setSuccess(false);
                return JSON.toJSONString(layuiJSON);
            }else {

                String currentUser = springSecurityUtil.currentUser(session);
                long userid = userFallbackFeign.selectUserIdByName(currentUser);

                RechargeCard rechargeCard1 = new RechargeCard();

                rechargeCard1.setId(userid);//这里的属性为userid。。。。。。。。。。。仅此这里为userid而已
                rechargeCard1.setCardNumber(cardNumber);
                rechargeCard1.setPassword(Long.parseLong(password));

                String recharge = rechargeCardFallbackFeign.recharge(rechargeCard1);
                return recharge;
            }

        }catch (Exception e){
            e.printStackTrace();
            layuiJSON.setMsg("充值失败");
            layuiJSON.setSuccess(false);
            return JSON.toJSONString(layuiJSON);
        }

    }


    @RequestMapping(path = "/toCouponsRecord")
    public String toCouponsRecord(Model model,HttpSession session)
    {
        int page=0;
        int size=8;
        int usetype= CouponsUseType.all.getUsetype();

        String currentUser = springSecurityUtil.currentUser(session);
        long userid = userFallbackFeign.selectUserIdByName(currentUser);

        List<CouponsRecord> couponsRecords = couponsRecordFallbackFeign.selectCouponsRecordByUserIdAndLimit(page, size, userid, usetype,DEFAULT_TIME);

        model.addAttribute("username",currentUser);
        model.addAttribute("couponsRecords",couponsRecords);

        return "client/view/newpage/coupons_record";
    }




}
