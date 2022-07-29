package com.example.demo.trainprice.utils.h12306.pojo;


import org.json.JSONObject;

import java.nio.ByteBuffer;

public class NoticeBlock implements Comparable<NoticeBlock> {
    private byte command;
    private byte day;
    private short dd;
    private int jsrq;
    private short kd;
    private int ksrq;
    private int kxgl;
    private byte kxzq;
    private short zmhz;

    public JSONObject getJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("ksrq", getKsrq());
            json.put("jsrq", getJsrq());
            json.put("kxgl", getKxgl());
            json.put("kxzq", (int) getKxzq());
            json.put("command", (int) getCommand());
            json.put("zmhz", (int) getZmhz());
            json.put("dd", (int) getDd());
            json.put("kd", (int) getKd());
            json.put("day", (int) getDay());
        } catch (Exception e) {
        }
        return json;
    }

    public NoticeBlock() {
    }

    public NoticeBlock(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        setKsrq(buffer.getInt());
        setJsrq(buffer.getInt());
        setKxgl(buffer.getInt());
        setKxzq(buffer.get());
        setCommand(buffer.get());
        setZmhz(buffer.getShort());
        setDd(buffer.getShort());
        setKd(buffer.getShort());
        setDay(buffer.get());
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

    public int getKxgl() {
        return this.kxgl;
    }

    public void setKxgl(int kxgl) {
        this.kxgl = kxgl;
    }

    public byte getKxzq() {
        return this.kxzq;
    }

    public void setKxzq(byte kxzq) {
        this.kxzq = kxzq;
    }

    public byte getCommand() {
        return this.command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public short getZmhz() {
        return this.zmhz;
    }

    public void setZmhz(short zmhz) {
        this.zmhz = zmhz;
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

    public byte getDay() {
        return this.day;
    }

    public void setDay(byte day) {
        this.day = day;
    }

    @Override // java.lang.Comparable
    public int compareTo(NoticeBlock o) {
        return getKsrq() - o.getKsrq();
    }

    public String toString() {
        return "NoticeBlock{ksrq=" + this.ksrq + ", jsrq=" + this.jsrq + ", kxgl=" + this.kxgl + ", kxzq=" + ((int) this.kxzq) + ", command=" + ((int) this.command) + ", zmhz=" + ((int) this.zmhz) + ", dd=" + ((int) this.dd) + ", kd=" + ((int) this.kd) + ", day=" + ((int) this.day) + '}';
    }
}