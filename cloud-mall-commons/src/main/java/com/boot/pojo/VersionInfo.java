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
    private String name;
    private long p_id;
    private double price;
    private String description;

    public VersionInfo() {
    }

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getP_id() {
        return p_id;
    }

    public void setP_id(long p_id) {
        this.p_id = p_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "versionId=" + versionId +
                ", name='" + name + '\'' +
                ", p_id=" + p_id +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
