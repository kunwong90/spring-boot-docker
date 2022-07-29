package com.example.demo.trainprice.utils.h12306.pojo;


public class TrainInfo implements Cloneable {
    /**
     * 车次，G1
     */
    public String CC;

    /**
     * 车次ID
     */
    public int CCID;

    /**
     * 到点
     */

    public String DD;


    public String DD1;
    public String DDJ;
    /**
     * 列车等级，新空快速，高速动车
     */
    public String DJ;

    /**
     * 到站
     */
    public String DZ;


    /**
     * 发站
     */
    public String FZ;
    public String ID;

    /**
     * 开点
     */
    public String KD;

    /**
     * 里程
     */
    public int LC;

    /**
     * 历时
     */
    public String LS;

    /**
     * 票价信息
     */
    public PJInfo PJ;
    /**
     * 是否空调
     */
    public int SFKT;

    /**
     * 始发站
     */
    public String SFZ;


    public String ZDL;

    /**
     * 终达站
     */
    public String ZDZ;

    /**
     * 运行信息
     */
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
    public String zmInThisTrain;
    public int yxts = 0;

    public String toString() {
        return "TrainInfo{ID='" + this.ID + "', CCID=" + this.CCID + ", fullTrainNo='" + this.fullTrainNo + "', CC='" + this.CC + "', DJ='" + this.DJ + "', KD='" + this.KD + "', DD='" + this.DD + "', DD1='" + this.DD1 + "', LC=" + this.LC + ", PJ=" + this.PJ + ", FZ='" + this.FZ + "', DZ='" + this.DZ + "', SFZ='" + this.SFZ + "', ZDZ='" + this.ZDZ + "', LS='" + this.LS + "', noticeStart=" + this.noticeStart + ", noticeEnd=" + this.noticeEnd + ", fzCCTKBlock=" + this.fzCCTKBlock + ", dzCCTKBlock=" + this.dzCCTKBlock + ", ksrq=" + this.ksrq + ", jsrq=" + this.jsrq + ", kxzq=" + this.kxzq + ", kxgl=" + this.kxgl + ", yxts=" + this.yxts + ", noticeBlock=" + this.noticeBlock + ", ZDL='" + this.ZDL + "', SFKT=" + this.SFKT + ", DDJ='" + this.DDJ + "', hcStart=" + this.hcStart + ", hcEnd=" + this.hcEnd + ", trainBJ=" + this.trainBJ + ", caceStart=" + this.caceStart + ", caceEnd=" + this.caceEnd + ", tkMode=" + this.tkMode + ", zmInThisTrain='" + this.zmInThisTrain + '}';
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