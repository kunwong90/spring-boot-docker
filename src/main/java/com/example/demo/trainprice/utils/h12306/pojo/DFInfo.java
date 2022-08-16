package com.example.demo.trainprice.utils.h12306.pojo;

public class DFInfo {
    private String ds;
    private String fs;
    private String station;

    public DFInfo() {
    }

    public DFInfo(String station, String ds, String fs) {
        this.station = station;
        this.ds = ds;
        this.fs = fs;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DFInfo{");
        sb.append("ds='").append(ds).append('\'');
        sb.append(", fs='").append(fs).append('\'');
        sb.append(", station='").append(station).append('\'');
        sb.append('}');
        return sb.toString();
    }
}