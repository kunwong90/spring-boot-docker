package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TrainBasicInfo {
    private Long id;

    private String trainNo;

    private String startStationName;
    private String endStationName;

    private String departStationName;

    private String destStationName;

    private Integer distance;

    private BigDecimal rws;

    private BigDecimal rwx;

    private BigDecimal yws;

    private BigDecimal ywz;

    private BigDecimal ywx;

    private Date addTime;

    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public BigDecimal getRws() {
        return rws;
    }

    public void setRws(BigDecimal rws) {
        this.rws = rws;
    }

    public BigDecimal getRwx() {
        return rwx;
    }

    public void setRwx(BigDecimal rwx) {
        this.rwx = rwx;
    }

    public BigDecimal getYws() {
        return yws;
    }

    public void setYws(BigDecimal yws) {
        this.yws = yws;
    }

    public BigDecimal getYwz() {
        return ywz;
    }

    public void setYwz(BigDecimal ywz) {
        this.ywz = ywz;
    }

    public BigDecimal getYwx() {
        return ywx;
    }

    public void setYwx(BigDecimal ywx) {
        this.ywx = ywx;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
