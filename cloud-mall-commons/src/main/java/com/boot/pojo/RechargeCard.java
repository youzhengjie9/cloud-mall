package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel("充值卡")
public class RechargeCard implements Serializable {

    private long id;
    private String cardNumber; //卡号
    private long password; //密码
    private BigDecimal money; //面值
    private String created;
    private int status; //卡的状态：0是未使用,1是已使用

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

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RechargeCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", password=" + password +
                ", money=" + money +
                ", created='" + created + '\'' +
                ", status=" + status +
                '}';
    }
}
