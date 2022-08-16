package com.example.demo.trainprice.utils.h12306.pojo;

public class CCItem {
    private short dd;
    private Object ext1;
    private Object ext2;
    private short kd;
    private int lc;
    private String ss;
    private int ts;
    /**
     * 站名
     */
    private String zm;
    private int cctkWz;
    private short ccwz;

    private int number;

    public short getDd() {
        return dd;
    }

    public void setDd(short dd) {
        this.dd = dd;
    }

    public Object getExt1() {
        return ext1;
    }

    public void setExt1(Object ext1) {
        this.ext1 = ext1;
    }

    public Object getExt2() {
        return ext2;
    }

    public void setExt2(Object ext2) {
        this.ext2 = ext2;
    }

    public short getKd() {
        return kd;
    }

    public void setKd(short kd) {
        this.kd = kd;
    }

    public int getLc() {
        return lc;
    }

    public void setLc(int lc) {
        this.lc = lc;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public String getZm() {
        return zm;
    }

    public void setZm(String zm) {
        this.zm = zm;
    }

    public int getCctkWz() {
        return cctkWz;
    }

    public void setCctkWz(int cctkWz) {
        this.cctkWz = cctkWz;
    }

    public short getCcwz() {
        return ccwz;
    }

    public void setCcwz(short ccwz) {
        this.ccwz = ccwz;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CCItem{");
        sb.append("dd=").append(dd);
        sb.append(", ext1=").append(ext1);
        sb.append(", ext2=").append(ext2);
        sb.append(", kd=").append(kd);
        sb.append(", lc=").append(lc);
        sb.append(", ss='").append(ss).append('\'');
        sb.append(", ts=").append(ts);
        sb.append(", zm='").append(zm).append('\'');
        sb.append(", cctkWz=").append(cctkWz);
        sb.append(", ccwz=").append(ccwz);
        sb.append(", number=").append(number);
        sb.append('}');
        return sb.toString();
    }
}