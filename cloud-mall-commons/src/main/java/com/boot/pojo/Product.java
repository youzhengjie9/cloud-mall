package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 游政杰
 * 商品实体类
 */
@ApiModel("商品实体类")
public class Product implements Serializable {

    private long productId; //商品id
    private String name; //商品名称
    private BigDecimal price; //商品价格
    private String img; //商品图片
    private int number; //商品还剩的数量
    private long fl_id; //商品所属分类id=classifyid
    private long b_id; //商品所属品牌id
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

    public long getFl_id() {
        return fl_id;
    }

    public void setFl_id(long fl_id) {
        this.fl_id = fl_id;
    }

    public long getB_id() {
        return b_id;
    }

    public void setB_id(long b_id) {
        this.b_id = b_id;
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
                ", fl_id=" + fl_id +
                ", b_id=" + b_id +
                ", introduce_img='" + introduce_img + '\'' +
                ", content='" + content + '\'' +
                ", userid=" + userid +
                '}';
    }
}
