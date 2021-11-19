package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 游政杰
 * 商品版本信息表
 */
@ApiModel("商品版本信息表")
public class VersionInfo implements Serializable {

    private long versionId;
    private String name;
    private long p_id;
    private int order;
    private BigDecimal price;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "versionId=" + versionId +
                ", name='" + name + '\'' +
                ", p_id=" + p_id +
                ", order=" + order +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
