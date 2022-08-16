package com.example.demo.trainprice.utils.h12306.pojo;


import java.nio.ByteBuffer;


public class CCTKBlock {
    private short ccwz;
    private byte czcc;
    private byte day;
    private short dd;
    private short kd;
    private short lc;
    private short zmwz;

    public CCTKBlock(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        setCcwz(buffer.getShort());
        setLc(buffer.getShort());
        setDd(buffer.getShort());
        setKd(buffer.getShort());
        setZmwz(buffer.getShort());
        setDay(buffer.get());
        setCzcc(buffer.get());
    }

    public short getCcwz() {
        return this.ccwz;
    }

    public void setCcwz(short ccwz) {
        this.ccwz = ccwz;
    }

    public short getLc() {
        return this.lc;
    }

    public void setLc(short lc) {
        this.lc = lc;
    }

    public short getDd() {
        return this.dd;
    }

    public void setDd(short dd) {
        this.dd = dd;
    }

    public short getKd() {
        return this.kd;
    }

    public void setKd(short kd) {
        this.kd = kd;
    }

    public short getZmwz() {
        return this.zmwz;
    }

    public void setZmwz(short zmwz) {
        this.zmwz = zmwz;
    }

    public byte getDay() {
        return this.day;
    }

    public void setDay(byte day) {
        this.day = day;
    }

    public byte getCzcc() {
        return this.czcc;
    }

    public void setCzcc(byte czcc) {
        this.czcc = czcc;
    }
}