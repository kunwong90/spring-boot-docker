package com.example.demo.entity;

public class TrainPriceQueryVo {
    private String departStationName;

    private String destStationName;

    private int departureDate;

    private String trainNo;

    public String getDepartStationName() {
        return departStationName;
    }

    public void setDepartStationName(String departStationName) {
        this.departStationName = departStationName;
    }

    public String getDestStationName() {
        return destStationName;
    }

    public void setDestStationName(String destStationName) {
        this.destStationName = destStationName;
    }

    public int getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(int departureDate) {
        this.departureDate = departureDate;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }
}
