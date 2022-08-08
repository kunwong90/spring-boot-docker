package com.example.demo.trainprice.utils.h12306.pojo;


public class TrainInfo implements Cloneable {
    /**
     * 车次，G1
     */
    private String cc;

    /**
     * 车次ID
     */
    private int ccId;

    /**
     * 到点
     */

    private String dd;


    private String dd1;
    private String ddj;
    /**
     * 列车等级，新空快速，高速动车
     */
    private String dj;

    /**
     * 到站
     */
    private String dz;


    /**
     * 发站
     */
    private String fz;
    private String id;

    /**
     * 开点
     */
    private String kd;

    /**
     * 里程
     */
    private int lc;

    /**
     * 历时
     */
    private String ls;

    /**
     * 票价信息
     */
    private PJInfo pj;
    /**
     * 是否空调
     */
    private int sfkt;

    /**
     * 始发站
     */
    private String sfz;


    private String zdl;

    /**
     * 终达站
     */
    private String zdz;

    /**
     * 运行信息
     */
    private YunXingInfo basicYunXingInfo;
    private int caceEnd;
    private int caceStart;
    private CCTKBlock dzCCTKBlock;
    private String fullTrainNo;
    private CCTKBlock fzCCTKBlock;
    private DFInfo fzDFInfo;
    private int hcEnd;
    private int hcStart;
    private int jsrq;
    private int ksrq;
    private int kxgl;
    private int kxzq;
    private NoticeBlock noticeBlock;
    private int noticeEnd;
    private int noticeStart;
    private SimpleTrainInfo simpleTrainInfo;
    private boolean tkMode;
    private int trainBJ;
    private String zmInThisTrain;
    private int yxts = 0;

    public String toString() {
        return "TrainInfo{ID='" + this.id + "', CCID=" + this.ccId + ", fullTrainNo='" + this.fullTrainNo + "', CC='" + this.cc + "', DJ='" + this.dj + "', KD='" + this.kd + "', DD='" + this.dd + "', DD1='" + this.dd1 + "', LC=" + this.lc + ", PJ=" + this.pj + ", FZ='" + this.fz + "', DZ='" + this.dz + "', SFZ='" + this.sfz + "', ZDZ='" + this.zdz + "', LS='" + this.ls + "', noticeStart=" + this.noticeStart + ", noticeEnd=" + this.noticeEnd + ", fzCCTKBlock=" + this.fzCCTKBlock + ", dzCCTKBlock=" + this.dzCCTKBlock + ", ksrq=" + this.ksrq + ", jsrq=" + this.jsrq + ", kxzq=" + this.kxzq + ", kxgl=" + this.kxgl + ", yxts=" + this.yxts + ", noticeBlock=" + this.noticeBlock + ", ZDL='" + this.zdl + "', SFKT=" + this.sfkt + ", DDJ='" + this.ddj + "', hcStart=" + this.hcStart + ", hcEnd=" + this.hcEnd + ", trainBJ=" + this.trainBJ + ", caceStart=" + this.caceStart + ", caceEnd=" + this.caceEnd + ", tkMode=" + this.tkMode + ", zmInThisTrain='" + this.zmInThisTrain + '}';
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

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public int getCcId() {
        return ccId;
    }

    public void setCcId(int ccId) {
        this.ccId = ccId;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getDd1() {
        return dd1;
    }

    public void setDd1(String dd1) {
        this.dd1 = dd1;
    }

    public String getDdj() {
        return ddj;
    }

    public void setDdj(String ddj) {
        this.ddj = ddj;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getFz() {
        return fz;
    }

    public void setFz(String fz) {
        this.fz = fz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKd() {
        return kd;
    }

    public void setKd(String kd) {
        this.kd = kd;
    }

    public int getLc() {
        return lc;
    }

    public void setLc(int lc) {
        this.lc = lc;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public PJInfo getPj() {
        return pj;
    }

    public void setPj(PJInfo pj) {
        this.pj = pj;
    }

    public int getSfkt() {
        return sfkt;
    }

    public void setSfkt(int sfkt) {
        this.sfkt = sfkt;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getZdl() {
        return zdl;
    }

    public void setZdl(String zdl) {
        this.zdl = zdl;
    }

    public String getZdz() {
        return zdz;
    }

    public void setZdz(String zdz) {
        this.zdz = zdz;
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

    public CCTKBlock getDzCCTKBlock() {
        return dzCCTKBlock;
    }

    public void setDzCCTKBlock(CCTKBlock dzCCTKBlock) {
        this.dzCCTKBlock = dzCCTKBlock;
    }

    public String getFullTrainNo() {
        return fullTrainNo;
    }

    public void setFullTrainNo(String fullTrainNo) {
        this.fullTrainNo = fullTrainNo;
    }

    public CCTKBlock getFzCCTKBlock() {
        return fzCCTKBlock;
    }

    public void setFzCCTKBlock(CCTKBlock fzCCTKBlock) {
        this.fzCCTKBlock = fzCCTKBlock;
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

    public NoticeBlock getNoticeBlock() {
        return noticeBlock;
    }

    public void setNoticeBlock(NoticeBlock noticeBlock) {
        this.noticeBlock = noticeBlock;
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

    public boolean isTkMode() {
        return tkMode;
    }

    public void setTkMode(boolean tkMode) {
        this.tkMode = tkMode;
    }

    public int getTrainBJ() {
        return trainBJ;
    }

    public void setTrainBJ(int trainBJ) {
        this.trainBJ = trainBJ;
    }

    public String getZmInThisTrain() {
        return zmInThisTrain;
    }

    public void setZmInThisTrain(String zmInThisTrain) {
        this.zmInThisTrain = zmInThisTrain;
    }

    public int getYxts() {
        return yxts;
    }

    public void setYxts(int yxts) {
        this.yxts = yxts;
    }
}