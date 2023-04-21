package com.example.demo.trainprice.utils.h12306.pojo;


public class TrainInfo implements Cloneable {
    public String CC;
    public int CCID;
    public String DD;
    public String DD1;
    public String DDJ;
    public String DJ;
    public String DZ;
    public Object Ex1;
    public Object Ex2;
    public Object Ex3;
    public Object Ex4;
    public String FZ;
    public String ID;
    public String KD;
    public int LC;
    public String LS;
    public PJInfo PJ;
    public int SFKT;
    public String SFZ;
    public String ZDL;
    public String ZDZ;
    private YunXingInfo basicYunXingInfo;
    public int caceEnd;
    public int caceStart;
    public CCTKBlock dzCCTKBlock;
    public String fullTrainNo;
    public CCTKBlock fzCCTKBlock;
    private DFInfo fzDFInfo;
    public int hcEnd;
    public int hcStart;
    public int jsrq;
    public int ksrq;
    public int kxgl;
    public int kxzq;
    public NoticeBlock noticeBlock;
    public int noticeEnd;
    public int noticeStart;
    private SimpleTrainInfo simpleTrainInfo;
    public boolean tkMode;
    public int trainBJ;
    public SimpleYPInfo ypInfo;
    public String zmInThisTrain;
    public int yxts = 0;
    public Object oriEx1 = null;

    public String toString() {
        return "TrainInfo{ID='" + this.ID + "', CCID=" + this.CCID + ", fullTrainNo='" + this.fullTrainNo + "', CC='" + this.CC + "', DJ='" + this.DJ + "', KD='" + this.KD + "', DD='" + this.DD + "', DD1='" + this.DD1 + "', LC=" + this.LC + ", PJ=" + this.PJ + ", FZ='" + this.FZ + "', DZ='" + this.DZ + "', SFZ='" + this.SFZ + "', ZDZ='" + this.ZDZ + "', LS='" + this.LS + "', noticeStart=" + this.noticeStart + ", noticeEnd=" + this.noticeEnd + ", fzCCTKBlock=" + this.fzCCTKBlock + ", dzCCTKBlock=" + this.dzCCTKBlock + ", ksrq=" + this.ksrq + ", jsrq=" + this.jsrq + ", kxzq=" + this.kxzq + ", kxgl=" + this.kxgl + ", yxts=" + this.yxts + ", noticeBlock=" + this.noticeBlock + ", Ex1=" + this.Ex1 + ", Ex2=" + this.Ex2 + ", Ex3=" + this.Ex3 + ", Ex4=" + this.Ex4 + ", ypInfo=" + this.ypInfo + ", ZDL='" + this.ZDL + "', SFKT=" + this.SFKT + ", DDJ='" + this.DDJ + "', hcStart=" + this.hcStart + ", hcEnd=" + this.hcEnd + ", trainBJ=" + this.trainBJ + ", caceStart=" + this.caceStart + ", caceEnd=" + this.caceEnd + ", tkMode=" + this.tkMode + ", zmInThisTrain='" + this.zmInThisTrain + "', oriEx1=" + this.oriEx1 + '}';
    }

    public YunXingInfo getBasicYunXingInfo() {
        if (this.basicYunXingInfo == null) {
            this.basicYunXingInfo = new YunXingInfo();
        }
        return this.basicYunXingInfo;
    }

    public void setBasicYunXingInfo(YunXingInfo basicYunXingInfo) {
        this.basicYunXingInfo = basicYunXingInfo;
    }

    public DFInfo getFzDFInfo() {
        if (this.fzDFInfo == null) {
            this.fzDFInfo = new DFInfo();
        }
        return this.fzDFInfo;
    }

    public void setFzDFInfo(DFInfo fzDFInfo) {
        this.fzDFInfo = fzDFInfo;
    }

    public SimpleTrainInfo getSimpleTrainInfo() {
        if (this.simpleTrainInfo == null) {
            this.simpleTrainInfo = new SimpleTrainInfo();
        }
        return this.simpleTrainInfo;
    }

    public void setSimpleTrainInfo(SimpleTrainInfo simpleTrainInfo) {
        this.simpleTrainInfo = simpleTrainInfo;
    }
}