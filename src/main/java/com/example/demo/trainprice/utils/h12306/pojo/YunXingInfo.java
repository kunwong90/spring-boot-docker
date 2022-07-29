package com.example.demo.trainprice.utils.h12306.pojo;


public class YunXingInfo {

    /**
     * 结束日期
     */
    int jsrq;
    /**
     * 开始日期
     */
    int ksrq;
    int kxgl;
    int kxzq;

    public YunXingInfo() {
    }

    public YunXingInfo(int kxzq, int kxgl, int ksrq, int jsrq) {
        this.kxzq = kxzq;
        this.kxgl = kxgl;
        this.ksrq = ksrq;
        this.jsrq = jsrq;
    }

    public int getKxzq() {
        return this.kxzq;
    }

    public void setKxzq(int kxzq) {
        this.kxzq = kxzq;
    }

    public int getKxgl() {
        return this.kxgl;
    }

    public void setKxgl(int kxgl) {
        this.kxgl = kxgl;
    }

    public int getKsrq() {
        return this.ksrq;
    }

    public void setKsrq(int ksrq) {
        this.ksrq = ksrq;
    }

    public int getJsrq() {
        return this.jsrq;
    }

    public void setJsrq(int jsrq) {
        this.jsrq = jsrq;
    }

    public String toString() {
        return "YunXingInfo{kxzq=" + this.kxzq + ", kxgl=" + this.kxgl + ", ksrq=" + this.ksrq + ", jsrq=" + this.jsrq + '}';
    }
}