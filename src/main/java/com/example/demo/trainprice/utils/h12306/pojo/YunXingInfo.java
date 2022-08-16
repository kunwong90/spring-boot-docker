package com.example.demo.trainprice.utils.h12306.pojo;


public class YunXingInfo {

    /**
     * 结束日期
     */
    private int jsrq;
    /**
     * 开始日期
     */
    private int ksrq;
    private int kxgl;
    private int kxzq;

    public YunXingInfo() {
    }

    public YunXingInfo(int kxzq, int kxgl, int ksrq, int jsrq) {
        this.kxzq = kxzq;
        this.kxgl = kxgl;
        this.ksrq = ksrq;
        this.jsrq = jsrq;
    }

    public int getJsrq() {
        return jsrq;
    }

    public void setJsrq(int jsrq) {
        this.jsrq = jsrq;
    }

    public int getKsrq() {
        return ksrq;
    }

    public void setKsrq(int ksrq) {
        this.ksrq = ksrq;
    }

    public int getKxgl() {
        return kxgl;
    }

    public void setKxgl(int kxgl) {
        this.kxgl = kxgl;
    }

    public int getKxzq() {
        return kxzq;
    }

    public void setKxzq(int kxzq) {
        this.kxzq = kxzq;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("YunXingInfo{");
        sb.append("jsrq=").append(jsrq);
        sb.append(", ksrq=").append(ksrq);
        sb.append(", kxgl=").append(kxgl);
        sb.append(", kxzq=").append(kxzq);
        sb.append('}');
        return sb.toString();
    }
}