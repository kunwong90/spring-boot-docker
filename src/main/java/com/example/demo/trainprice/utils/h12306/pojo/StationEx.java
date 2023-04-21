package com.example.demo.trainprice.utils.h12306.pojo;

public class StationEx {
    Short idx;
    Short mainIdx;
    String mainZm;
    String zm;

    public StationEx() {
    }

    public StationEx(Short idx, Short mainIdx) {
        this.idx = idx;
        this.mainIdx = mainIdx;
    }

    public StationEx(Short idx, String zm, Short mainIdx, String mainZm) {
        this.idx = idx;
        this.zm = zm;
        this.mainIdx = mainIdx;
        this.mainZm = mainZm;
    }

    public Short getIdx() {
        return this.idx;
    }

    public void setIdx(Short idx) {
        this.idx = idx;
    }

    public String getZm() {
        return this.zm;
    }

    public void setZm(String zm) {
        this.zm = zm;
    }

    public Short getMainIdx() {
        return this.mainIdx;
    }

    public void setMainIdx(Short mainIdx) {
        this.mainIdx = mainIdx;
    }

    public String getMainZm() {
        return this.mainZm;
    }

    public void setMainZm(String mainZm) {
        this.mainZm = mainZm;
    }

    @Override
    public String toString() {
        return "StationEx{idx=" + this.idx + ", zm='" + this.zm + "', mainIdx=" + this.mainIdx + ", mainZm='" + this.mainZm + "'}";
    }
}