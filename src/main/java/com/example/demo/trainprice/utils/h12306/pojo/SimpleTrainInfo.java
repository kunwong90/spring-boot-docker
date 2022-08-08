package com.example.demo.trainprice.utils.h12306.pojo;

import com.example.demo.trainprice.utils.h12306.core.CoreZZCX;

public class SimpleTrainInfo {
    private int id;
    private int trainBJ;
    /**
     * 列车等级，数字表示
     *
     * @see CoreZZCX#lcdj(int, boolean)
     */
    private short trainDJ;
    private int trainInCCTKSYe;
    private int trainInCCTKSYs;
    private int trainJsrq;
    private int trainKsrq;
    private int trainKxgl;
    private int trainKxzq;
    /**
     * 车次名称，G1，K12，D201
     */
    private String trainName;
    private short trainSf;

    /**
     * 是否空调
     */
    private boolean trainSfkt;
    private String trainXw;
    private int caceEnd;
    private int caceStart;
    private String ddj;
    private int hcEnd;
    private int hcStart;
    private int noticeEnd;
    private int noticeStart;
    private int pjdmEnd;
    private int pjdmStart;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainBJ() {
        return trainBJ;
    }

    public void setTrainBJ(int trainBJ) {
        this.trainBJ = trainBJ;
    }

    public short getTrainDJ() {
        return trainDJ;
    }

    public void setTrainDJ(short trainDJ) {
        this.trainDJ = trainDJ;
    }

    public int getTrainInCCTKSYe() {
        return trainInCCTKSYe;
    }

    public void setTrainInCCTKSYe(int trainInCCTKSYe) {
        this.trainInCCTKSYe = trainInCCTKSYe;
    }

    public int getTrainInCCTKSYs() {
        return trainInCCTKSYs;
    }

    public void setTrainInCCTKSYs(int trainInCCTKSYs) {
        this.trainInCCTKSYs = trainInCCTKSYs;
    }

    public int getTrainJsrq() {
        return trainJsrq;
    }

    public void setTrainJsrq(int trainJsrq) {
        this.trainJsrq = trainJsrq;
    }

    public int getTrainKsrq() {
        return trainKsrq;
    }

    public void setTrainKsrq(int trainKsrq) {
        this.trainKsrq = trainKsrq;
    }

    public int getTrainKxgl() {
        return trainKxgl;
    }

    public void setTrainKxgl(int trainKxgl) {
        this.trainKxgl = trainKxgl;
    }

    public int getTrainKxzq() {
        return trainKxzq;
    }

    public void setTrainKxzq(int trainKxzq) {
        this.trainKxzq = trainKxzq;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public short getTrainSf() {
        return trainSf;
    }

    public void setTrainSf(short trainSf) {
        this.trainSf = trainSf;
    }

    public boolean isTrainSfkt() {
        return trainSfkt;
    }

    public void setTrainSfkt(boolean trainSfkt) {
        this.trainSfkt = trainSfkt;
    }

    public String getTrainXw() {
        return trainXw;
    }

    public void setTrainXw(String trainXw) {
        this.trainXw = trainXw;
    }

    public int getCaceEnd() {
        return caceEnd;
    }

    public void setCaceEnd(int caceEnd) {
        this.caceEnd = caceEnd;
    }

    public int getCaceStart() {
        return caceStart;
    }

    public void setCaceStart(int caceStart) {
        this.caceStart = caceStart;
    }

    public String getDdj() {
        return ddj;
    }

    public void setDdj(String ddj) {
        this.ddj = ddj;
    }

    public int getHcEnd() {
        return hcEnd;
    }

    public void setHcEnd(int hcEnd) {
        this.hcEnd = hcEnd;
    }

    public int getHcStart() {
        return hcStart;
    }

    public void setHcStart(int hcStart) {
        this.hcStart = hcStart;
    }

    public int getNoticeEnd() {
        return noticeEnd;
    }

    public void setNoticeEnd(int noticeEnd) {
        this.noticeEnd = noticeEnd;
    }

    public int getNoticeStart() {
        return noticeStart;
    }

    public void setNoticeStart(int noticeStart) {
        this.noticeStart = noticeStart;
    }

    public int getPjdmEnd() {
        return pjdmEnd;
    }

    public void setPjdmEnd(int pjdmEnd) {
        this.pjdmEnd = pjdmEnd;
    }

    public int getPjdmStart() {
        return pjdmStart;
    }

    public void setPjdmStart(int pjdmStart) {
        this.pjdmStart = pjdmStart;
    }
}