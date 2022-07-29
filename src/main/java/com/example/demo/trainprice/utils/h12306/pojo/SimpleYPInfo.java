package com.example.demo.trainprice.utils.h12306.pojo;

public class SimpleYPInfo {
    private String YPStr;
    private String payload;

    public SimpleYPInfo() {
    }

    public SimpleYPInfo(String ypStr, String payload) {
        setYPStr(ypStr);
        setPayload(payload);
    }

    public String getYPStr() {
        return this.YPStr;
    }

    public void setYPStr(String yPStr) {
        this.YPStr = yPStr;
    }

    public String getPayload() {
        return this.payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}