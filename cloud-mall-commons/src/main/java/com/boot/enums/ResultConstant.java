package com.boot.enums;

public enum  ResultConstant {

    SUCCESS(200,"SUCCESS"),
    FAILURE(404,"FAILURE");

    private int code;
    private String codeStat;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeStat() {
        return codeStat;
    }

    public void setCodeStat(String codeStat) {
        this.codeStat = codeStat;
    }

    private ResultConstant(int code, String codeStat)
    {
        this.code=code;
        this.codeStat=codeStat;
    }



}
