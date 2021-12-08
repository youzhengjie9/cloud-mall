package com.boot.enums;

//卡状态
public enum RechargeCardStatus {

    notUsed(0,"卡未使用"),
    used(1,"卡已使用");


    private int status;
    private String msg;

    RechargeCardStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RechargeCardStatus{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
