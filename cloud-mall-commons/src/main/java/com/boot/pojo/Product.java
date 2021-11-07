package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author 游政杰
 * 商品实体类
 */
@ApiModel("商品实体类")
public class Product implements Serializable {

    private long productId; //商品id
    private String name; //商品名称
    private double price; //商品价格
    private String img; //商品图片
    private int number; //商品还剩的数量
    private long fl_id; //商品所属分类id=classifyid

    public Product() {
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getFl_id() {
        return fl_id;
    }

    public void setFl_id(long fl_id) {
        this.fl_id = fl_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", number=" + number +
                ", fl_id=" + fl_id +
                '}';
    }
}
