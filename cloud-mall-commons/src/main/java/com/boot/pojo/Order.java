package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 游政杰
 * @date 2021/11/18 21:26
 */
@ApiModel("订单类")
public class Order implements Serializable {

    private long id;
    private String imgUrl;
    private String goodsInfo;
    private String goodsParams;
    private int goodsCount;
    private BigDecimal singleGoodsMoney;
    private String realname;
    private String phone;
    private String address;
    private long userid;
    private long productid;
    private long statusid; //订单状态


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getGoodsParams() {
        return goodsParams;
    }

    public void setGoodsParams(String goodsParams) {
        this.goodsParams = goodsParams;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getSingleGoodsMoney() {
        return singleGoodsMoney;
    }

    public void setSingleGoodsMoney(BigDecimal singleGoodsMoney) {
        this.singleGoodsMoney = singleGoodsMoney;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getProductid() {
        return productid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
    }

    public long getStatusid() {
        return statusid;
    }

    public void setStatusid(long statusid) {
        this.statusid = statusid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", imgUrl='" + imgUrl + '\'' +
                ", goodsInfo='" + goodsInfo + '\'' +
                ", goodsParams='" + goodsParams + '\'' +
                ", goodsCount=" + goodsCount +
                ", singleGoodsMoney=" + singleGoodsMoney +
                ", realname='" + realname + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", userid=" + userid +
                ", productid=" + productid +
                ", statusid=" + statusid +
                '}';
    }
}
