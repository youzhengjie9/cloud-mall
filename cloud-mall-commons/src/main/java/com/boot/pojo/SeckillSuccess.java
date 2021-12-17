package com.boot.pojo;

import io.swagger.annotations.ApiModel;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ApiModel("秒杀成功model")
@Accessors(chain = true)
public class SeckillSuccess implements Serializable {

    private long id;
    private Seckill secKill; //用户秒杀的商品
    private String createTime; //秒杀到商品的时间，后面我们需要判断30分钟未支付则失效的需求
    private int state; //状态（0=未支付 ，1=待发货，2=已发货 ，3=交易成功 ，4=失效）
    private long userId; //秒杀成功的用户id

    public long getId() {
        return id;
    }

    public SeckillSuccess setId(long id) {
        this.id = id;
        return this;
    }

    public Seckill getSecKill() {
        return secKill;
    }

    public SeckillSuccess setSecKill(Seckill secKill) {
        this.secKill = secKill;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public SeckillSuccess setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public int getState() {
        return state;
    }

    public SeckillSuccess setState(int state) {
        this.state = state;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public SeckillSuccess setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "SeckillSuccess{" +
                "id=" + id +
                ", secKill=" + secKill +
                ", createTime='" + createTime + '\'' +
                ", state=" + state +
                ", userId=" + userId +
                '}';
    }
}
