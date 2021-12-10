package com.boot.pojo;

import io.swagger.annotations.ApiModel;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@ApiModel("优惠券记录")
public class CouponsRecord implements Serializable {

    private long id;
    private CouponsActivity couponsActivity; //所持有的优惠券
    private long user_id; //用户id
    private String getTime; //获取该优惠券的时间
    private int useType;//优惠券状态(0是未使用,1是已使用)
    private String useTime;//使用时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CouponsActivity getCouponsActivity() {
        return couponsActivity;
    }

    public void setCouponsActivity(CouponsActivity couponsActivity) {
        this.couponsActivity = couponsActivity;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    @Override
    public String toString() {
        return "CouponsRecord{" +
                "id=" + id +
                ", couponsActivity=" + couponsActivity +
                ", user_id=" + user_id +
                ", getTime='" + getTime + '\'' +
                ", useType=" + useType +
                ", useTime='" + useTime + '\'' +
                '}';
    }
}
