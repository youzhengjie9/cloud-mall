package com.boot.controller.pearAdmin;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.boot.annotation.Operation;
import com.boot.data.layuiData;
import com.boot.data.layuiJSON;
import com.boot.feign.system.fallback.CouponsActivityFallbackFeign;
import com.boot.feign.system.notfallback.CouponsActivityFeign;
import com.boot.pojo.Brand;
import com.boot.pojo.CouponsActivity;
import com.boot.utils.IpUtils;
import com.boot.utils.SnowId;
import com.boot.utils.SpringSecurityUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/pear")
@Api("优惠券后台控制器")
public class CouponsController {

    @Autowired
    private CouponsActivityFeign couponsActivityFeign;

    @Autowired
    private SpringSecurityUtil springSecurityUtil;

    @Autowired
    private CouponsActivityFallbackFeign couponsActivityFallbackFeign;

    @RequestMapping(path = "/toCoupons")
    public String toCoupons()
    {

        return "back/coupons_list";
    }

    @RequestMapping(path = "/toAddCouponsActivityPage")
    public String toAddCouponsActivityPage()
    {

        return "back/module/addCouponsActivity";
    }

    @ResponseBody
    @PostMapping(path = "/add/couponsActivity")
    public String addCouponsActivity(CouponsActivity couponsActivity)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try {

            couponsActivity.setId(SnowId.nextId());

            couponsActivityFeign.insertCouponsActivity(couponsActivity);

            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("添加优惠券活动成功");
            return JSON.toJSONString(layuiJSON);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("添加优惠券活动失败");
            return JSON.toJSONString(layuiJSON);
        }
    }

    @ResponseBody
    @RequestMapping(path = "/couponsActivityData")
    public String couponsActivityData(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "3") int limit) {

        page=limit*(page-1);

        layuiData<CouponsActivity> layuiData = new layuiData<>();

        List<CouponsActivity> couponsActivities = couponsActivityFallbackFeign.selectAllCouponsActivityByLimit(page, limit);

        int count = couponsActivityFallbackFeign.selectCouponsActivityCount();
        layuiData.setCode(0);
        layuiData.setMsg("");
        layuiData.setCount(count);
        layuiData.setData(couponsActivities);
        return JSON.toJSONString(layuiData);
    }

    @ResponseBody
    @GetMapping(path = "/couponsEnableValid")
    @SentinelResource("couponsEnableValid")
    public String couponsEnableValid(@RequestParam(value = "id",required = true) String id)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try {

            couponsActivityFeign.updateValid(Long.parseLong(id),1);

            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("已生效");
            return JSON.toJSONString(layuiJSON);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("生效失败");
            return JSON.toJSONString(layuiJSON);
        }

    }

    @ResponseBody
    @GetMapping(path = "/couponsDisableValid")
    @SentinelResource("couponsDisableValid")
    public String couponsDisableValid(@RequestParam(value = "id",required = true) String id)
    {
        layuiJSON layuiJSON = new layuiJSON();
        try {
            couponsActivityFeign.updateValid(Long.parseLong(id),0);
            layuiJSON.setSuccess(true);
            layuiJSON.setMsg("已失效");
            return JSON.toJSONString(layuiJSON);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("失效失败");
            return JSON.toJSONString(layuiJSON);
        }

    }


    @Operation("删除优惠券活动")
    @ResponseBody
    @RequestMapping(path = "/deleteCouponsActivity/{id}")
    public String deleteCouponsActivity(@PathVariable("id") String id, HttpSession session
            , HttpServletRequest request) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            long cid = Long.parseLong(id);

            couponsActivityFeign.deleteCouponsActivity(cid);

            String username = springSecurityUtil.currentUser(session);
            java.util.Date date = new java.util.Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(date);
            String ipAddr = IpUtils.getIpAddr(request);
            log.debug(time + "   用户名：" + username + "删除优惠券活动成功，删除的优惠券活动id为：" + id + ",ip为：" + ipAddr);
            layuiJSON.setMsg("删除优惠券活动成功");
            layuiJSON.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setMsg("删除优惠券活动失败");
            layuiJSON.setSuccess(false);
        }

        return JSON.toJSONString(layuiJSON);
    }

    @Operation("批量删除优惠券活动")
    @ResponseBody
    @RequestMapping(path = "/batchDeleteCouponsActivity/{checkIds}")
    public String batchDeleteCouponsActivity(@PathVariable("checkIds") String checkIds) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            String[] split = checkIds.split(",");
            long [] arr=new long[split.length];

            for (int i = 0; i < split.length; i++) {
                arr[i]=Long.parseLong(split[i]);
            }

            couponsActivityFeign.batchDeleteCouponsActivity(arr);
            layuiJSON.setMsg("批量删除优惠券活动成功");
            layuiJSON.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setSuccess(false);
            layuiJSON.setMsg("批量删除优惠券活动失败");
        }

        return JSON.toJSONString(layuiJSON);
    }

    @RequestMapping(path = "/toEditCouponsActivityPage")
    public String toEditCouponsActivityPage(@RequestParam(value = "id",required = true) String id, Model model)
    {
        long cid = Long.parseLong(id);

        CouponsActivity couponsActivity = couponsActivityFallbackFeign.selectCouponsActivityById(cid);

        model.addAttribute("couponsActivity",couponsActivity);

        return "back/module/editCouponsActivity";
    }



    @Operation("修改优惠券活动")
    @ResponseBody
    @PostMapping(path = "/edit/couponsActivity")
    public String editCouponsActivity(CouponsActivity couponsActivity, HttpSession session
            , HttpServletRequest request) {

        layuiJSON layuiJSON = new layuiJSON();
        try {
            couponsActivityFeign.updateCouponsActivity(couponsActivity);
            layuiJSON.setMsg("修改优惠券活动成功");
            layuiJSON.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            layuiJSON.setMsg("修改优惠券活动失败");
            layuiJSON.setSuccess(false);
        }

        return JSON.toJSONString(layuiJSON);
    }

}
