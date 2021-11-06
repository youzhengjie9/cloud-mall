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
    private String productName; //商品名称
    private double productPrice; //商品价格
    private String productImg; //商品图片
    private int productNumber; //商品还剩的数量
    private long classifyId; //商品所属分类id

    public Product() {
    }

    public Product(long productId, String productName, double productPrice, String productImg, int productNumber, long classifyId) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productNumber = productNumber;
        this.classifyId = classifyId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(long classifyId) {
        this.classifyId = classifyId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productImg='" + productImg + '\'' +
                ", productNumber=" + productNumber +
                ", classifyId=" + classifyId +
                '}';
    }
}
