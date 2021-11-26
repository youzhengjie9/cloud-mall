package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author 游政杰
 * 分类实体类
 */
@ApiModel("分类实体类")
public class Classify implements Serializable {

    private long id;
    private String text;
    private int isNav;

    public Classify() {
    }

    public Classify(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIsNav() {
        return isNav;
    }

    public void setIsNav(int isNav) {
        this.isNav = isNav;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isNav=" + isNav +
                '}';
    }
}
