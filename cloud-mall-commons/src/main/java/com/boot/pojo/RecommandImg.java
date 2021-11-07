package com.boot.pojo;

import java.io.Serializable;

public class RecommandImg implements Serializable {

    private long id;
    private String imgName;
    private long productId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "RecommandImg{" +
                "id=" + id +
                ", imgName='" + imgName + '\'' +
                ", productId=" + productId +
                '}';
    }
}
