package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 游政杰
 */
@ApiModel("购物车")
public class Cart implements Serializable {

    private long id;
    private String imgUrl;//商品图片url
    private String goodsInfo; //商品信息，例如商品名
    private String goodsParams; //商品参数，也就是商品版本信息内容
    private BigDecimal price; //商品单价
    private int goodsCount; //商品数量
    private BigDecimal singleGoodsMoney; //该商品的单价*数量的值
    private long userid; //所属用户id,因为用户id唯一，所以通过用户id可以查询一个集合就是购物车
    private long productid; //商品id
    private String skus; //所选sku，逗号分隔


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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", imgUrl='" + imgUrl + '\'' +
                ", goodsInfo='" + goodsInfo + '\'' +
                ", goodsParams='" + goodsParams + '\'' +
                ", price=" + price +
                ", goodsCount=" + goodsCount +
                ", singleGoodsMoney=" + singleGoodsMoney +
                ", userid=" + userid +
                ", productid=" + productid +
                ", skus='" + skus + '\'' +
                '}';
    }
}
