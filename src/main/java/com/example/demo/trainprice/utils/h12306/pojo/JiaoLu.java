package com.example.demo.trainprice.utils.h12306.pojo;


public class JiaoLu {
    String ddj;
    int endDate;
    String flag1;
    String flag2;
    int startDate;
    String trainType;
    String trains;

    public JiaoLu() {
    }

    public JiaoLu(int startDate, int endDate, String flag1, String flag2, String trainType, String ddj, String trains) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.trainType = trainType;
        this.ddj = ddj;
        this.trains = trains;
    }

    public JiaoLu(String[] items) {
        this.startDate = Integer.parseInt(items[0]);
        this.endDate = Integer.parseInt(items[1]);
        this.flag1 = items[2];
        this.flag2 = items[3];
        this.trainType = items[4];
        this.ddj = items[5];
        this.trains = items[6];
    }

    public int getStartDate() {
        return this.startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return this.endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getFlag1() {
        return this.flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return this.flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getTrainType() {
        return this.trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getDdj() {
        return this.ddj;
    }

    public void setDdj(String ddj) {
        this.ddj = ddj;
    }

    public String getTrains() {
        return this.trains;
    }

    public void setTrains(String trains) {
        this.trains = trains;
    }

    public String toString() {
        return "JiaoLu{startDate=" + this.startDate + ", endDate=" + this.endDate + ", flag1='" + this.flag1 + "', flag2='" + this.flag2 + "', trainType='" + this.trainType + "', ddj='" + this.ddj + "', trains='" + this.trains + "'}";
    }
}