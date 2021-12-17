package com.boot.enums;

/**
 * 秒杀状态
 */
public enum SeckillState {
    not_pay(0,"未支付"),
    waitSend(1,"待发货"),
    alreadySend(2,"已发货"),
    success(3,"交易成功"),
    fail(4,"失效");

    private int code; //编号
    private String msg; //说明

    SeckillState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SeckillState{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
