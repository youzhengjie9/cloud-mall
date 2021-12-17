package com.boot.pojo;

import io.swagger.annotations.ApiModel;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 游政杰
 */
@ApiModel("秒杀商品model")
@Accessors(chain = true)
public class Seckill implements Serializable {

    private long seckillId; //秒杀商品id
    private String seckillName; //秒杀商品名称
    private int seckillNumber;
    private BigDecimal price;
    private String img;
    private int limitCount;
    private String startTime;
    private String endTime;
    private String createTime;
    private User user;

    public long getSeckillId() {
        return seckillId;
    }

    public Seckill setSeckillId(long seckillId) {
        this.seckillId = seckillId;
        return this;
    }

    public String getSeckillName() {
        return seckillName;
    }

    public Seckill setSeckillName(String seckillName) {
        this.seckillName = seckillName;
        return this;
    }

    public int getSeckillNumber() {
        return seckillNumber;
    }

    public Seckill setSeckillNumber(int seckillNumber) {
        this.seckillNumber = seckillNumber;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Seckill setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImg() {
        return img;
    }

    public Seckill setImg(String img) {
        this.img = img;
        return this;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public Seckill setLimitCount(int limitCount) {
        this.limitCount = limitCount;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public Seckill setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public Seckill setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Seckill setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Seckill setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", seckillName='" + seckillName + '\'' +
                ", seckillNumber=" + seckillNumber +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", limitCount=" + limitCount +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", user=" + user +
                '}';
    }
}
