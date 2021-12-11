package com.boot.controller;

import com.boot.feign.system.fallback.CouponsActivityFallbackFeign;
import com.boot.pojo.CouponsActivity;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/web/couponsActivity")
@Api("优惠券中心 web api")
public class CouponsActivityController {

    @Autowired
    private CouponsActivityFallbackFeign couponsActivityFallbackFeign;

    @RequestMapping(path = "/toCouponsActivity")
    public String toCouponsActivity(Model model)
    {
        int page=0;
        int size=10;
        List<CouponsActivity> couponsActivities = couponsActivityFallbackFeign.selectAllCouponsActivityByLimitAndValid(page, size);

        model.addAttribute("couponsActivities",couponsActivities);

        return "client/view/newpage/coupons_activity";
    }






}
