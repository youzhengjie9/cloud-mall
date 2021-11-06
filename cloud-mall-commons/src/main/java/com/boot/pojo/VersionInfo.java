package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author 游政杰
 * 商品版本信息表
 */
@ApiModel("商品版本信息表")
public class VersionInfo implements Serializable {

    private long versionId;
    private String versionName;
    private long productId;
    private double versionPrice;
    private String versionDesc;

    public VersionInfo() {
    }

    public VersionInfo(long versionId, String versionName, long productId, double versionPrice, String versionDesc) {
        this.versionId = versionId;
        this.versionName = versionName;
        this.productId = productId;
        this.versionPrice = versionPrice;
        this.versionDesc = versionDesc;
    }

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getVersionPrice() {
        return versionPrice;
    }

    public void setVersionPrice(double versionPrice) {
        this.versionPrice = versionPrice;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "versionId=" + versionId +
                ", versionName='" + versionName + '\'' +
                ", productId=" + productId +
                ", versionPrice=" + versionPrice +
                ", versionDesc='" + versionDesc + '\'' +
                '}';
    }
}
