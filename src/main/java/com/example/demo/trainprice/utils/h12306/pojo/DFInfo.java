package com.example.demo.trainprice.utils.h12306.pojo;

public class DFInfo {
    String ds;
    String fs;
    String station;

    public DFInfo() {
    }

    public DFInfo(String station, String ds, String fs) {
        this.station = station;
        this.ds = ds;
        this.fs = fs;
    }

    public String getStation() {
        return this.station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDs() {
        return this.ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getFs() {
        return this.fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }
}