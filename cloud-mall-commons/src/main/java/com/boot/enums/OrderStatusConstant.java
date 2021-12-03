package com.boot.enums;


public enum OrderStatusConstant {

    waitSend(1,"待发货"),
    alreadySend(2,"已发货"),
    success(3,"交易成功"),
    cancel(4,"已取消"),
    returnGoods(5,"退款中"),
    returnGoodsSuccess(6,"退款完成");

    private long id; //id
    private String status; //状态

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    OrderStatusConstant(long id, String status)
    {
        this.id=id;
        this.status=status;

    }

}
