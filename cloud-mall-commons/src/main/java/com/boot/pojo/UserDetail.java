package com.boot.pojo;

import java.io.Serializable;

public class UserDetail implements Serializable {

    private long id;
    private long userid;
    private int sex; //性别：0是男 1是女
    private String signature; //个人签名
    private String icon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", userid=" + userid +
                ", sex=" + sex +
                ", signature='" + signature + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
