package com.boot.pojo;

import java.io.Serializable;

public class SlideShow implements Serializable {

    private long id;
    private String src; //轮播图src
    private long productId; //轮播图对应的商品id
    private int sort; //排序==sort值越大，优先级越高

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SlideShow{" +
                "id=" + id +
                ", src='" + src + '\'' +
                ", productId=" + productId +
                ", sort=" + sort +
                '}';
    }
}
