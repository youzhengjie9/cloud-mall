package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel("充值记录")
public class RechargeRecord implements Serializable {

    private long id;
    private String cardNumber;
    private BigDecimal money;
    private String created;
    private long userid;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "RechargeRecord{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", money=" + money +
                ", created='" + created + '\'' +
                ", userid=" + userid +
                '}';
    }
}
