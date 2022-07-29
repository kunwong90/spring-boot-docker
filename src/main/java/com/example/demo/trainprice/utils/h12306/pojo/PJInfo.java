package com.example.demo.trainprice.utils.h12306.pojo;


import org.apache.commons.lang3.StringUtils;


public class PJInfo {
    public final String BLANK_PRICE = "—";

    /**
     * 硬座
     */
    private String yz = "—";

    /**
     * 软座
     */
    private String rz = "—";

    /**
     * 硬卧上
     */
    private String yws = "—";

    /**
     * 硬卧中
     */
    private String ywz = "—";

    /**
     * 硬卧下
     */
    private String ywx = "—";

    /**
     * 软卧上
     */
    private String rws = "—";

    /**
     * 软卧下
     */
    private String rwx = "—";

    /**
     * 一等座
     */
    private String ydz = "—";

    /**
     * 二等座
     */
    private String edz = "—";

    /**
     * 特等座
     */
    private String tdz = "—";

    /**
     * 高级软卧
     */
    private String gjrw = "—";

    /**
     * 商务座
     */
    private String swz = "—";

    /**
     * 无座
     */
    private String wz = "—";

    /**
     * 其他
     */
    private String qt = "—";

    /**
     * 动卧
     */
    private String dw = "—";

    public String getYz() {
        return yz;
    }

    public void setYz(String yz) {
        this.yz = yz;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
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

    public String getTdz() {
        return tdz;
    }

    public void setTdz(String tdz) {
        this.tdz = tdz;
    }

    public String getGjrw() {
        return gjrw;
    }

    public void setGjrw(String gjrw) {
        this.gjrw = gjrw;
    }

    public String getSwz() {
        return swz;
    }

    public void setSwz(String swz) {
        this.swz = swz;
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

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getLowestPrice() {
        if (!isBlankPrice(getYz())) {
            return getYz();
        }
        if (!isBlankPrice(getEdz())) {
            return getEdz();
        }
        if (!isBlankPrice(getYws())) {
            return getYws();
        }
        return getRws();
    }

    private boolean isBlankPrice(String str) {

        return str == null || "—".equals(str) || StringUtils.isEmpty(str);
    }

    public String toString() {
        return "PJInfo{BLANK_PRICE='—', yz='" + this.yz + "', rz='" + this.rz + "', yws='" + this.yws + "', ywz='" + this.ywz + "', ywx='" + this.ywx + "', rws='" + this.rws + "', rwx='" + this.rwx + "', ydz='" + this.ydz + "', edz='" + this.edz + "', tdz='" + this.tdz + "', gjrw='" + this.gjrw + "', swz='" + this.swz + "', wz='" + this.wz + "', qt='" + this.qt + "', dw='" + this.dw + "'}";
    }
}