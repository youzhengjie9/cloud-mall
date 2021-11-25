package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 游政杰
 * 商品实体类
 */
@ApiModel(value = "商品实体类",description = "")
public class Product implements Serializable {

    private long productId; //商品id
    private String name; //商品名称
    private BigDecimal price; //商品价格
    private String img; //商品图片
    private int number; //商品还剩的数量
    private Brand brand; //品牌
    private Classify classify; //分类
    private String introduce_img;//商品介绍图片地址用逗号分隔
    private String content; //商品内容介绍
    private long userid; //商品是被谁发布出去的

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public String getIntroduce_img() {
        return introduce_img;
    }

    public void setIntroduce_img(String introduce_img) {
        this.introduce_img = introduce_img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", number=" + number +
                ", brand=" + brand +
                ", classify=" + classify +
                ", introduce_img='" + introduce_img + '\'' +
                ", content='" + content + '\'' +
                ", userid=" + userid +
                '}';
    }
}
