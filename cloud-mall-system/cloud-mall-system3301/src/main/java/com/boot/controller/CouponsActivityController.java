package com.boot.controller;

import com.boot.enums.ResultConstant;
import com.boot.pojo.CouponsActivity;
import com.boot.service.CouponsActivityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/couponsActivity")
@Api("优惠券活动服务api")
public class CouponsActivityController {

    @Autowired
    private CouponsActivityService couponsActivityService;

    @ResponseBody
    @PostMapping(path = "/insertCouponsActivity")
    public String insertCouponsActivity(@RequestBody CouponsActivity couponsActivity){

        couponsActivityService.insertCouponsActivity(couponsActivity);
        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @GetMapping(path = "/selectAllCouponsActivityByLimit/{page}/{size}")
    public List<CouponsActivity> selectAllCouponsActivityByLimit(@PathVariable("page") int page,
                                                                 @PathVariable("size") int size){

        return couponsActivityService.selectAllCouponsActivityByLimit(page, size);
    }
    @ResponseBody
    @GetMapping(path = "/selectCouponsActivityCount")
    public int selectCouponsActivityCount(){

        return couponsActivityService.selectCouponsActivityCount();
    }

    @ResponseBody
    @GetMapping(path = "/updateValid/{id}/{valid}")
    public String updateValid(@PathVariable("id") long id,
                              @PathVariable("valid") int valid){

        couponsActivityService.updateValid(id, valid);

        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @GetMapping(path = "/deleteCouponsActivity/{id}")
    public String deleteCouponsActivity(@PathVariable("id") long id){

        couponsActivityService.deleteCouponsActivity(id);

        return ResultConstant.SUCCESS.getCodeStat();
    }

    @ResponseBody
    @GetMapping(path = "/batchDeleteCouponsActivity")
    public String batchDeleteCouponsActivity(@RequestParam("ids")long[] ids){

        couponsActivityService.batchDeleteCouponsActivity(ids);

        return ResultConstant.SUCCESS.getCodeStat();
    }
    @ResponseBody
    @GetMapping(path = "/selectCouponsActivityById/{id}")
    public CouponsActivity selectCouponsActivityById(@PathVariable("id") long id){

        return couponsActivityService.selectCouponsActivityById(id);
    }

    @ResponseBody
    @PostMapping(path = "/updateCouponsActivity")
    public String updateCouponsActivity(@RequestBody CouponsActivity couponsActivity){

        couponsActivityService.updateCouponsActivity(couponsActivity);

        return ResultConstant.SUCCESS.getCodeStat();
    }



}
