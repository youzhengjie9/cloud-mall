package com.boot.service.impl;

import com.alibaba.fastjson.JSON;
import com.boot.dao.RechargeCardMapper;
import com.boot.data.layuiJSON;
import com.boot.enums.RechargeCardStatus;
import com.boot.feign.user.notFallback.UserFeign;
import com.boot.pojo.RechargeCard;
import com.boot.pojo.RechargeRecord;
import com.boot.service.RechargeCardService;
import com.boot.service.RechargeRecordService;
import com.boot.utils.SnowId;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@Service
@Slf4j
public class RechargeCardServiceImpl implements RechargeCardService {

    @Autowired
    private RechargeCardMapper rechargeCardMapper;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private RechargeRecordService rechargeRecordService;

    @Override
    public RechargeCard selectOneRechargeCard(String cardNumber, long password) {
        return rechargeCardMapper.selectOneRechargeCard(cardNumber, password);
    }

    @Override
    public void updateCardStatus(String cardNumber, long password) {
        rechargeCardMapper.updateCardStatus(cardNumber, password);
    }

    @GlobalTransactional(name = "seata_recharge",rollbackFor = Exception.class)
    @Override
    public String recharge(String cardNumber, long password, long userid) {

        try{
            layuiJSON layuiJSON = new layuiJSON();
            RechargeCard rechargeCard = rechargeCardMapper.selectOneRechargeCard(cardNumber, password);

            if(rechargeCard==null){ //说明卡号或密码不正确，充值失败

                layuiJSON.setSuccess(false);
                layuiJSON.setMsg("没有该卡号，充值失败");
                return JSON.toJSONString(layuiJSON);
            }else {

                //如果卡未被使用则可以充值
                if(rechargeCard.getStatus()== RechargeCardStatus.notUsed.getStatus())
                {

                    rechargeCardMapper.updateCardStatus(cardNumber, password); //修改成已被使用

                    RechargeRecord rechargeRecord = new RechargeRecord();
                    rechargeRecord.setId(SnowId.nextId());
                    rechargeRecord.setCardNumber(cardNumber);
                    rechargeRecord.setMoney(rechargeCard.getMoney());
                    rechargeRecord.setUserid(userid);
                    Date d1 = new Date();
                    java.sql.Date date = new java.sql.Date(d1.getTime());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String created = format.format(date);
                    rechargeRecord.setCreated(created);

                    rechargeRecordService.insertRechargeRecord(rechargeRecord); //插入记录
                    userFeign.incrMoneyByUserId(userid,rechargeCard.getMoney().toString());//增加余额

                    layuiJSON.setSuccess(true);
                    layuiJSON.setMsg("用户:"+userid+"充值成功，面值为："+rechargeCard.getMoney()+"元");
                    return JSON.toJSONString(layuiJSON);
                }else {

                    log.error("卡号:"+cardNumber+"已被使用过,充值失败");
                    layuiJSON.setSuccess(false);
                    layuiJSON.setMsg("卡号:"+cardNumber+"已被使用过,充值失败");
                    return JSON.toJSONString(layuiJSON);
                }

            }

        }catch (Exception e){

            throw new RuntimeException();
        }

    }
}
