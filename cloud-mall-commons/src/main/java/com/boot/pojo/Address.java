package com.boot.pojo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author 游政杰
 */
@ApiModel("收货地址实体类")
public class Address implements Serializable {

    private long id;
    private String realname; //收货人真实姓名
    private String phone;//收货人电话
    private String province; //目的地所在省
    private String city; //目的地所在城市
    private String area; //目的地所在区/县
    private String address; //目的地详细地址
    private long userid; //用户id

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", realname='" + realname + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", userid=" + userid +
                '}';
    }
}
