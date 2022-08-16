package com.example.demo.trainprice.utils.h12306.pojo;


public class JiaoLu {
    private String ddj;
    private int endDate;
    private String flag1;
    private String flag2;
    private int startDate;
    private String trainType;
    private String trains;

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

    public String getDdj() {
        return ddj;
    }

    public void setDdj(String ddj) {
        this.ddj = ddj;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getTrains() {
        return trains;
    }

    public void setTrains(String trains) {
        this.trains = trains;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JiaoLu{");
        sb.append("ddj='").append(ddj).append('\'');
        sb.append(", endDate=").append(endDate);
        sb.append(", flag1='").append(flag1).append('\'');
        sb.append(", flag2='").append(flag2).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append(", trainType='").append(trainType).append('\'');
        sb.append(", trains='").append(trains).append('\'');
        sb.append('}');
        return sb.toString();
    }
}