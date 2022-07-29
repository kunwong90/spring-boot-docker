package com.example.demo.trainprice.utils.h12306.pojo;

import com.example.demo.trainprice.utils.h12306.core.CoreZZCX;

public class SimpleTrainInfo {
    public int id;
    public int trainBJ;
    /**
     * 列车等级，数字表示
     * @see CoreZZCX#lcdj(int, boolean)
     */
    public short trainDJ;
    public int trainInCCTKSYe;
    public int trainInCCTKSYs;
    public int trainJSRQ;
    public int trainKSRQ;
    public int trainKXGL;
    public int trainKXZQ;
    /**
     * 车次名称，G1，K12，D201
     */
    public String trainName;
    public short trainSF;

    /**
     * 是否空调
     */
    public boolean trainSFKT;
    public String trainXW;
    public int caceEnd;
    public int caceStart;
    public String ddj;
    public int hcEnd;
    public int hcStart;
    public int noticeEnd;
    public int noticeStart;
    public int pjdmEnd;
    public int pjdmStart;
}