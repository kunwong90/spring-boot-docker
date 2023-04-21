package com.example.demo.trainprice.utils.h12306.pojo;


import org.apache.http.util.TextUtils;

public class PJInfo {
    public final String BLANK_PRICE = "—";
    public String YZ = "—";
    public String RZ = "—";
    public String YWs = "—";
    public String YWz = "—";
    public String YWx = "—";
    public String RWs = "—";
    public String RWx = "—";
    public String YD = "—";
    public String ED = "—";
    public String TD = "—";
    public String GR = "—";
    public String SW = "—";
    public String WZ = "—";
    public String QT = "—";
    public String DW = "—";

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
     * 商务座
     */
    private String swz = "—";

    /**
     * 动卧
     */
    private String dw = "—";

    public String getYZ() {
        return this.YZ;
    }

    public void setYZ(String yZ) {
        this.YZ = yZ;
    }

    public String getRZ() {
        return this.RZ;
    }

    public void setRZ(String rZ) {
        this.RZ = rZ;
    }

    public String getYWs() {
        return this.YWs;
    }

    public void setYWs(String yWs) {
        this.YWs = yWs;
    }

    public String getYWz() {
        return this.YWz;
    }

    public void setYWz(String yWz) {
        this.YWz = yWz;
    }

    public String getYWx() {
        return this.YWx;
    }

    public void setYWx(String yWx) {
        this.YWx = yWx;
    }

    public String getRWs() {
        return this.RWs;
    }

    public void setRWs(String rWs) {
        this.RWs = rWs;
    }

    public String getRWx() {
        return this.RWx;
    }

    public void setRWx(String rWx) {
        this.RWx = rWx;
    }

    public String getYD() {
        return this.YD;
    }

    public void setYD(String yD) {
        this.YD = yD;
    }

    public String getED() {
        return this.ED;
    }

    public void setED(String eD) {
        this.ED = eD;
    }

    public String getTD() {
        return this.TD;
    }

    public void setTD(String tD) {
        this.TD = tD;
    }

    public String getGR() {
        return this.GR;
    }

    public void setGR(String gR) {
        this.GR = gR;
    }

    public String getSW() {
        return this.SW;
    }

    public void setSW(String sW) {
        this.SW = sW;
    }

    public String getWZ() {
        return this.WZ;
    }

    public void setWZ(String wZ) {
        this.WZ = wZ;
    }

    public String getQT() {
        return this.QT;
    }

    public void setQT(String qT) {
        this.QT = qT;
    }

    public String getDW() {
        return this.DW;
    }

    public void setDW(String dW) {
        this.DW = dW;
    }

    public String getLowestPrice() {
        if (!isBlankPrice(getYZ())) {
            return getYZ();
        }
        if (!isBlankPrice(getED())) {
            return getED();
        }
        if (!isBlankPrice(getYWs())) {
            return getYWs();
        }
        return getRWs();
    }

    private boolean isBlankPrice(String str) {
        return str == null || "—".equals(str) || TextUtils.isEmpty(str);
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

    public String getSwz() {
        return swz;
    }

    public void setSwz(String swz) {
        this.swz = swz;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    @Override
    public String toString() {
        return "PJInfo{BLANK_PRICE='—', YZ='" + this.YZ + "', RZ='" + this.RZ + "', YWs='" + this.YWs + "', YWz='" + this.YWz + "', YWx='" + this.YWx + "', RWs='" + this.RWs + "', RWx='" + this.RWx + "', YD='" + this.YD + "', ED='" + this.ED + "', TD='" + this.TD + "', GR='" + this.GR + "', SW='" + this.SW + "', WZ='" + this.WZ + "', QT='" + this.QT + "', DW='" + this.DW + "'}";
    }

}