package com.example.demo.entity;

import java.util.Date;

public class TrainBasicInfo {
    private Long id;

    private String trainNo;

    private String startStationName;
    private String endStationName;

    private String departureStationName;

    private String destStationName;

    private Date departureDate;

    private Integer distance;

    /**
     * 一等座
     */
    private String ydz;

    /**
     * 二等座
     */
    private String edz;

    /**
     * 商务座
     */
    private String swz;

    /**
     * 特等座
     */
    private String tdz;

    /**
     * 软座
     */
    private String rz;

    /**
     * 硬座
     */
    private String yz;

    /**
     * 高级软卧上
     */
    private String gjrws;

    /**
     * 高级软卧下
     */
    private String gjrwx;

    /**
     * 动卧上
     */
    private String dws;

    /**
     * 动卧下
     */
    private String dwx;

    /**
     * 一等卧上
     */
    private String ydws;

    /**
     * 一等卧下
     */

    private String ydwx;

    /**
     * 二等卧上
     */
    private String edws;
    /**
     * 二等卧中
     */
    private String edwz;
    /**
     * 二等卧下
     */
    private String edwx;

    /**
     * 无座
     */
    private String wz;

    /**
     * 其他
     */
    private String qt;

    /**
     * 软卧上
     */
    private String rws;

    /**
     * 软卧下
     */
    private String rwx;

    /**
     * 硬卧上
     */
    private String yws;
    /**
     * 硬卧中
     */
    private String ywz;

    /**
     * 硬卧下
     */
    private String ywx;

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

    public String getDepartureStationName() {
        return departureStationName;
    }

    public void setDepartureStationName(String departureStationName) {
        this.departureStationName = departureStationName;
    }

    public String getDestStationName() {
        return destStationName;
    }

    public void setDestStationName(String destStationName) {
        this.destStationName = destStationName;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getYdz() {
        return ydz;
    }

    public void setYdz(String ydz) {
        this.ydz = ydz;
    }

    public String getEdz() {
        return edz;
    }

    public void setEdz(String edz) {
        this.edz = edz;
    }

    public String getSwz() {
        return swz;
    }

    public void setSwz(String swz) {
        this.swz = swz;
    }

    public String getTdz() {
        return tdz;
    }

    public void setTdz(String tdz) {
        this.tdz = tdz;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
    }

    public String getYz() {
        return yz;
    }

    public void setYz(String yz) {
        this.yz = yz;
    }

    public String getGjrws() {
        return gjrws;
    }

    public void setGjrws(String gjrws) {
        this.gjrws = gjrws;
    }

    public String getGjrwx() {
        return gjrwx;
    }

    public void setGjrwx(String gjrwx) {
        this.gjrwx = gjrwx;
    }

    public String getDws() {
        return dws;
    }

    public void setDws(String dws) {
        this.dws = dws;
    }

    public String getDwx() {
        return dwx;
    }

    public void setDwx(String dwx) {
        this.dwx = dwx;
    }

    public String getYdws() {
        return ydws;
    }

    public void setYdws(String ydws) {
        this.ydws = ydws;
    }

    public String getYdwx() {
        return ydwx;
    }

    public void setYdwx(String ydwx) {
        this.ydwx = ydwx;
    }

    public String getEdws() {
        return edws;
    }

    public void setEdws(String edws) {
        this.edws = edws;
    }

    public String getEdwz() {
        return edwz;
    }

    public void setEdwz(String edwz) {
        this.edwz = edwz;
    }

    public String getEdwx() {
        return edwx;
    }

    public void setEdwx(String edwx) {
        this.edwx = edwx;
    }

    public String getWz() {
        return wz;
    }

    public void setWz(String wz) {
        this.wz = wz;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getRws() {
        return rws;
    }

    public void setRws(String rws) {
        this.rws = rws;
    }

    public String getRwx() {
        return rwx;
    }

    public void setRwx(String rwx) {
        this.rwx = rwx;
    }

    public String getYws() {
        return yws;
    }

    public void setYws(String yws) {
        this.yws = yws;
    }

    public String getYwz() {
        return ywz;
    }

    public void setYwz(String ywz) {
        this.ywz = ywz;
    }

    public String getYwx() {
        return ywx;
    }

    public void setYwx(String ywx) {
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
