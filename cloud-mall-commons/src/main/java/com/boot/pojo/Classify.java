package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author 游政杰
 * 分类实体类
 */
@ApiModel("分类实体类")
public class Classify implements Serializable {

    private long classifyId;
    private String text;
    private int isNav;

    public Classify() {
    }

    public Classify(long classifyId, String text, int isNav) {
        this.classifyId = classifyId;
        this.text = text;
        this.isNav = isNav;
    }

    public long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(long classifyId) {
        this.classifyId = classifyId;
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
                "classifyId=" + classifyId +
                ", text='" + text + '\'' +
                ", isNav=" + isNav +
                '}';
    }
}
