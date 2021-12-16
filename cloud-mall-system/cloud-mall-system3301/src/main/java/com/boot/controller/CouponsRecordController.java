package com.boot.controller;

import com.boot.enums.ResultConstant;
import com.boot.pojo.CouponsActivity;
import com.boot.pojo.CouponsRecord;
import com.boot.service.CouponsActivityService;
import com.boot.service.CouponsRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/feign/couponsRecord")
@Api("优惠券记录服务api")
public class CouponsRecordController {

    @Autowired
    private CouponsRecordService couponsRecordService;

    @ResponseBody
    @PostMapping(path = "/insertCouponsRecord")
    public String insertCouponsRecord(@RequestBody CouponsRecord couponsRecord){

        String res = couponsRecordService.insertCouponsRecord(couponsRecord);

        return res;
    }
    @ResponseBody
    @GetMapping(path = "/selectCouponsRecordByUserIdAndLimit/{page}/{size}/{userid}/{usetype}")
    public List<CouponsRecord> selectCouponsRecordByUserIdAndLimit(@PathVariable("page") int page,
                                                                   @PathVariable("size") int size,
                                                                   @PathVariable("userid") long userid,
                                                                   @PathVariable("usetype") int usetype){

        return couponsRecordService.selectCouponsRecordByUserIdAndLimit(page, size, userid, usetype);
    }
    @ResponseBody
    @GetMapping(path = "/selectCouponsRecord/{couponsid}/{userid}/{usetype}")
    public CouponsRecord selectCouponsRecord(@PathVariable("couponsid") long couponsid,
                                             @PathVariable("userid") long userid,
                                             @PathVariable("usetype") int usetype){

        return couponsRecordService.selectCouponsRecord(couponsid, userid, usetype);
    }

    @ResponseBody
    @GetMapping(path = "/updateCouponsRecordUsetype/{couponsid}/{usetype}/{usetime}")
    public String updateCouponsRecordUsetype(@PathVariable("couponsid") long couponsid,
                                             @PathVariable("usetype") int usetype,
                                             @PathVariable("usetime") String usetime){

        couponsRecordService.updateCouponsRecordUsetype(couponsid, usetype,usetime);

        return ResultConstant.SUCCESS.getCodeStat();
    }



}
