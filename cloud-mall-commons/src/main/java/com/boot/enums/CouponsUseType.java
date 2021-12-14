package com.boot.enums;

/**
 * 优惠券使用类型
 * @author 游政杰
 */
public enum CouponsUseType {

    all(-1,"所有"),
    notused(0,"未使用"),
    used(1,"已使用");

    private int usetype;
    private String note;

    CouponsUseType(int usetype, String note) {
        this.usetype = usetype;
        this.note = note;
    }

    public int getUsetype() {
        return usetype;
    }

    public void setUsetype(int usetype) {
        this.usetype = usetype;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CouponsUseType{" +
                "usetype=" + usetype +
                ", note='" + note + '\'' +
                '}';
    }
}
