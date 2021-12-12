package com.boot.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.boot.data.layuiJSON;
import com.boot.feign.system.fallback.CouponsActivityFallbackFeign;
import com.boot.feign.system.notfallback.CouponsRecordFeign;
import com.boot.feign.user.fallback.UserFallbackFeign;
import com.boot.pojo.CouponsActivity;
import com.boot.pojo.CouponsRecord;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/web/couponsRecord")
@Api("优惠券记录 web api")
public class CouponsRecordController {

    @Autowired
    private CouponsRecordFeign couponsRecordFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private UserFallbackFeign userFallbackFeign;

    /** 领取优惠券*/
    @ResponseBody
    //一定要加produces,不然ajax get会乱码
    @GetMapping(path = "/getCoupons",produces = "application/json; charset=utf-8")
    @SentinelResource(value = "getCoupons",blockHandler = "getCoupons_block")
    public String getCoupons(long couponsid, HttpSession session){

        String currentUser = springSecurityUtil.currentUser(session);
        long userid = userFallbackFeign.selectUserIdByName(currentUser);

        CouponsRecord couponsRecord = new CouponsRecord();
        couponsRecord.setId(SnowId.nextId());
        CouponsActivity couponsActivity = new CouponsActivity();
        couponsActivity.setId(couponsid);
        couponsRecord.setCouponsActivity(couponsActivity);
        couponsRecord.setUser_id(userid);
        Date d1 = new Date();
        java.sql.Date date = new java.sql.Date(d1.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(date);
        couponsRecord.setGetTime(dateString);
        couponsRecord.setUseType(0); //默认未使用
        couponsRecord.setUseTime("");
        String json = couponsRecordFeign.insertCouponsRecord(couponsRecord);
        return json;
    }
    public String getCoupons_block(long couponsid, HttpSession session, BlockException ex){

        return "该接口访问量过高,采取限流措施";
    }



}
