package com.boot.pojo;

import io.swagger.annotations.ApiModel;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Accessors(chain = true)
@ApiModel("优惠券活动")
public class CouponsActivity implements Serializable {

    private long id;
    private String couponsName; //优惠券名称
    private int couponsCount; //优惠券数量
    private BigDecimal amount; //优惠券金额
    private int limitCount; //每人限制领取多少张
    private BigDecimal minPoint;//使用门槛 ====也就是满多少元才可以使用
    private String startTime;//优惠券开始时间
    private String endTime; //优惠券结束时间
    private String note;//优惠券备注
    private int valid;//是否生效(0是失效,1是生效)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCouponsName() {
        return couponsName;
    }

    public void setCouponsName(String couponsName) {
        this.couponsName = couponsName;
    }

    public int getCouponsCount() {
        return couponsCount;
    }

    public void setCouponsCount(int couponsCount) {
        this.couponsCount = couponsCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public BigDecimal getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(BigDecimal minPoint) {
        this.minPoint = minPoint;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "CouponsActivity{" +
                "id=" + id +
                ", couponsName='" + couponsName + '\'' +
                ", couponsCount=" + couponsCount +
                ", amount=" + amount +
                ", limitCount=" + limitCount +
                ", minPoint=" + minPoint +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", note='" + note + '\'' +
                ", valid=" + valid +
                '}';
    }
}
