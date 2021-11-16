package com.boot.data;

import com.boot.constant.ResultCode;

import java.io.Serializable;

/**
 * @author 游政杰
 * @date 2021/11/16
 */
public class CommonResult<T> implements Serializable {

    private T obj;

    private int code;

    public CommonResult() {
        this.code= ResultCode.SUCCESS; //无參默认成功
        this.obj=null;
    }

    public CommonResult(int code) {
        this.code=code;
        this.obj=null;
    }

    public CommonResult(T obj, int code) {
        this.obj = obj;
        this.code = code;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "obj=" + obj +
                ", code=" + code +
                '}';
    }
}
