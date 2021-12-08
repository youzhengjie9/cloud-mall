package com.boot.controller;

import com.boot.enums.ResultConstant;
import com.boot.pojo.RechargeCard;
import com.boot.service.RechargeCardService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/feign/rechargeCard")
@Api("充值卡服务api")
public class RechargeCardController {

    @Autowired
    private RechargeCardService rechargeCardService;

    //根据卡号和密码查询卡
    @ResponseBody
    @GetMapping(path = "/selectOneRechargeCard/{cardNumber}/{password}")
    public RechargeCard selectOneRechargeCard(@PathVariable("cardNumber") String cardNumber,
                                              @PathVariable("password") long password){


        return rechargeCardService.selectOneRechargeCard(cardNumber, password);

    }

    //使用卡
    @ResponseBody
    @PostMapping(path = "/updateCardStatus")
    public String updateCardStatus(@RequestBody RechargeCard rechargeCard){

        rechargeCardService.updateCardStatus(rechargeCard.getCardNumber(),rechargeCard.getPassword());

        return ResultConstant.SUCCESS.getCodeStat();
    }

    //充值的核心方法
    @ResponseBody
    @PostMapping(path = "/recharge")
    public String recharge(@RequestBody RechargeCard rechargeCard){

        //rechargeCard.getId()  这里的属性为userid。。。。。。。。。。。仅此这里为userid而已

        String recharge = rechargeCardService.
                recharge(rechargeCard.getCardNumber(),
                rechargeCard.getPassword(),
                rechargeCard.getId());

        return recharge;
    }


}
