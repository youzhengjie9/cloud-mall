package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel("分类导航条条")
public class ClassifyBar implements Serializable {

    private int id;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ClassifyBar{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
