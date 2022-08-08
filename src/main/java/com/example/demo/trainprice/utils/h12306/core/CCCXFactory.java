package com.example.demo.trainprice.utils.h12306.core;


import com.example.demo.trainprice.utils.h12306.datacenter.DataCenter;
import com.example.demo.trainprice.utils.h12306.pojo.*;
import com.example.demo.trainprice.utils.h12306.utils.Common;

import java.util.ArrayList;

public class CCCXFactory {
    static char[] CCZMs = {'D', 'Z', 'T', 'K', 'N', 'L', 'A', 'Y', 'C', 'Q', 'G', 'S'};
    int Myfs;
    int ReturnTypeFlag;
    int SearchFlag;
    CoreZZCX coreZZCX = new CoreZZCX();
    char dd;
    int f_from;
    int f_to;
    String[] findFile;
    int howmany;
    String kwords;
    char lastChar;


    int rq;
    String tNO;
    String fname = "";
    int sfAB = 0;
    ArrayList<String> ZMCCS = new ArrayList<>();
    ArrayList<TrainInfo> myCClist = new ArrayList<>();
    ArrayList<ArrayList<CCItem>> myCCdetails = new ArrayList<>();

    public CCCXFactory(String trainNO, int queryType) {
        this.tNO = trainNO.toUpperCase();
        this.SearchFlag = queryType;
        prepareRun(queryType);


        run();
    }

    public CCCXFactory(String trainNO, int queryType, int date) {
        this.tNO = trainNO.toUpperCase();
        this.SearchFlag = queryType;
        prepareRun(queryType);
        this.rq = date;
        run();
    }

    private void prepareRun(int flag) {
        boolean havZM = true;
        char chr = this.tNO.charAt(0);
        String str = this.tNO;
        char tChar = str.charAt(str.length() - 1);
        if (tChar == 'A' || tChar == 'B') {
            String str2 = this.tNO;
            this.tNO = str2.substring(0, str2.length() - 1);
            this.lastChar = tChar;
        }
        if (chr >= '0' && chr <= '9') {
            havZM = false;
        }
        if (havZM) {
            this.ZMCCS.add(this.tNO);
            this.findFile = new String[]{"ccdzzm"};
            this.Myfs = 0;
        } else if (flag == 1) {
            for (int i = 0; i < CCZMs.length; i++) {
                this.ZMCCS.add(CCZMs[i] + this.tNO);
            }
            this.findFile = new String[]{"ccdzzm", "ccdz"};
            this.Myfs = 1;
        } else if (flag == 0) {
            this.ZMCCS.add(this.tNO);
            this.findFile = new String[]{"ccdz"};
            this.Myfs = 0;
        }
    }

    private void run() {
        int i = this.Myfs;
        if (i == 0) {
            getInfoByfs0(this.findFile[0]);
        } else if (i == 1) {
            getInfoByfs1();
        }

    }

    private ArrayList<CCList> getpsInCC(ArrayList<String> tNo, String fname, int StartPS, int EndPS) {
        boolean isCCDZZM;
        String key;
        int wz;
        int trainCount = tNo.size();
        ArrayList<CCList> list = new ArrayList<>(trainCount);
        for (int i = 0; i < trainCount; i++) {
            list.add(new CCList());
        }
        if (fname.equals("ccdzzm")) {
            isCCDZZM = true;
        } else {
            isCCDZZM = false;
        }
        String Pre = null;
        int Cpos = 0;
        for (int i2 = StartPS; i2 <= EndPS; i2++) {
            if (isCCDZZM) {
                key = getDataCenter().getCCDZZM()[0].get(i2).toString().trim();
                wz = ((Integer) getDataCenter().getCCDZZM()[1].get(i2)).intValue();
            } else {
                key = getDataCenter().getCCDZ()[0].get(i2).toString().trim();
                wz = ((Short) getDataCenter().getCCDZ()[1].get(i2)).shortValue();
            }
            if (key.equals(Pre)) {
                list.get(Cpos - 1).getList().add(Integer.valueOf(wz));
            } else if (tNo.indexOf(key) >= 0) {
                list.get(Cpos).getList().add(Integer.valueOf(wz));
                Cpos++;
                Pre = key;
            }
        }
        return list;
    }

    private ArrayList<CCItem> CCtolst(int from, int to) {
        ArrayList<CCItem> alCCItem = new ArrayList<>();
        int xh = 0;
        int i = from;
        while (i <= to) {
            int idx = ((Integer) getDataCenter().getCCTKSY()[0].get(i)).intValue();
            CCTKBlock cctkBlock = new CCTKBlock(getDataCenter().getCCTK().get(idx));
            int zmIdx = cctkBlock.getZmwz();
            CCItem mCCItem = new CCItem();
            mCCItem.number = xh;
            mCCItem.ZM = getCoreZZCX().getZM(zmIdx);
            mCCItem.DD = cctkBlock.getDd();
            mCCItem.KD = cctkBlock.getKd();
            mCCItem.LC = cctkBlock.getLc();
            mCCItem.TS = cctkBlock.getDay() - 48;
            mCCItem.Ext1 = cctkBlock.getCzcc() - 48;
            mCCItem.Ext2 = cctkBlock.getZmwz();
            mCCItem.ccwz = cctkBlock.getCcwz();
            mCCItem.cctkWz = idx;
            alCCItem.add(mCCItem);
            i++;
            xh++;
        }
        return alCCItem;
    }

    private int getFirstNum(String Train) {
        for (int i = 0; i < Train.length(); i++) {
            char ChrINT = Train.charAt(i);
            if (ChrINT >= '0' && ChrINT <= '9') {
                return Integer.parseInt(Train.substring(i, i + 1));
            }
        }
        return -1;
    }

    public int getinfoby(String TrainNO, String fname) {
        int starpos;
        int endpos;
        int StarPS = getFirstNum(TrainNO);
        int Rtn = -1;
        if (StarPS == -1) {
            return -1;
        }
        int i = 0;
        if (fname.equals("ccdzzm")) {
            starpos = (Integer) getDataCenter().getCCSY()[2].get(StarPS);
            endpos = (Integer) getDataCenter().getCCSY()[3].get(StarPS);
        } else {
            starpos = (Integer) getDataCenter().getCCSY()[0].get(StarPS);
            endpos = (Integer) getDataCenter().getCCSY()[1].get(StarPS);
        }
        if (starpos != endpos) {
            ArrayList<CCList> CCps = getpsInCC(this.ZMCCS, fname, starpos, endpos);
            int i2 = 0;
            while (i2 < this.ZMCCS.size()) {
                char c = this.lastChar;
                if (c > 0) {
                    int pos = c - 'A';
                    SimpleTrainInfo CCinfo = getCoreZZCX().getTraininfo(CCps.get(i2).getList().get(pos));
                    int from = CCinfo.getTrainInCCTKSYs();
                    int to = CCinfo.getTrainInCCTKSYe();
                    ArrayList<CCItem> alCCItem = CCtolst(from, to);
                    this.myCCdetails.add(alCCItem);
                    this.myCClist.add(CSimpleTrainInfo(CCinfo, alCCItem.get(i), alCCItem.get(alCCItem.size() - 1)));
                    Rtn = 0;
                } else {
                    int listSize = CCps.get(i2).getList().size();
                    int j = 0;
                    while (j < listSize) {
                        SimpleTrainInfo CCinfo2 = getCoreZZCX().getTraininfo(CCps.get(i2).getList().get(j));
                        int from2 = CCinfo2.getTrainInCCTKSYs();
                        int to2 = CCinfo2.getTrainInCCTKSYe();
                        ArrayList<CCItem> alCCItem2 = CCtolst(from2, to2);
                        this.myCCdetails.add(alCCItem2);
                        ArrayList<TrainInfo> arrayList = this.myCClist;
                        CCItem cCItem = alCCItem2.get(0);
                        int listSize2 = listSize;
                        int listSize3 = alCCItem2.size() - 1;
                        arrayList.add(CSimpleTrainInfo(CCinfo2, cCItem, alCCItem2.get(listSize3)));
                        Rtn = 0;
                        j++;
                        listSize = listSize2;
                    }
                }
                i2++;
                i = 0;
            }
            return Rtn;
        }
        return -1;
    }

    private TrainInfo CSimpleTrainInfo(SimpleTrainInfo train, CCItem firstCCItem, CCItem lastCCItem) {
        PJInfo mPJInfo;
        TrainInfo simpleTrainInfo = new TrainInfo();
        simpleTrainInfo.setCc(train.getTrainName());
        simpleTrainInfo.setFullTrainNo(train.getTrainName());
        simpleTrainInfo.setDj(getCoreZZCX().lcdj(train.getTrainDJ(), train.isTrainSfkt()));
        simpleTrainInfo.setSfkt(train.isTrainSfkt() ? 1 : 0);
        simpleTrainInfo.setFz(firstCCItem.ZM);
        simpleTrainInfo.setDz(lastCCItem.ZM);
        simpleTrainInfo.setSfz(firstCCItem.ZM);
        simpleTrainInfo.setZdz(lastCCItem.ZM);
        simpleTrainInfo.setKd(Common.toTime(firstCCItem.KD));
        simpleTrainInfo.setDd(Common.toTime(lastCCItem.DD));
        simpleTrainInfo.setDd1(Common.toTime(lastCCItem.DD));
        simpleTrainInfo.setLc(lastCCItem.LC);
        int fzs = Common.getMinites(lastCCItem.DD, lastCCItem.KD, firstCCItem.KD, lastCCItem.TS - firstCCItem.TS);
        simpleTrainInfo.setLs(Common.covert2StringTime(fzs));
        if (train.getTrainBJ() == 1) {
            getCoreZZCX().resetNessaryVariable();
            double[] JcJb = getCoreZZCX().CountPJ(simpleTrainInfo.getLc());
            mPJInfo = getCoreZZCX().CountSJPJ(train.getTrainDJ(), train.getTrainSf(), train.isTrainSfkt(), JcJb, simpleTrainInfo.getLc(), train.getTrainXw());
        } else {
            int fzZmwz = Integer.parseInt(String.valueOf(firstCCItem.Ext2));
            int dzZmwz = Integer.parseInt(String.valueOf(lastCCItem.Ext2));
            mPJInfo = train.getPjdmStart() != 99999 ? getCoreZZCX().getPrice(train.getTrainName(), fzZmwz, dzZmwz, train.getPjdmStart(), train.getPjdmEnd(), getRq(), firstCCItem.TS) : getCoreZZCX().getPrice(fzZmwz, dzZmwz, firstCCItem.ccwz);
        }
        simpleTrainInfo.setPj(mPJInfo);
        simpleTrainInfo.setKsrq(train.getTrainKsrq());
        simpleTrainInfo.setJsrq(train.getTrainJsrq());
        simpleTrainInfo.setKxzq(train.getTrainKxzq());
        simpleTrainInfo.setKxgl(train.getTrainKxgl());
        simpleTrainInfo.setYxts(0);
        simpleTrainInfo.setFzCCTKBlock(new CCTKBlock(getDataCenter().getCCTK().get(firstCCItem.cctkWz)));
        simpleTrainInfo.setDzCCTKBlock(new CCTKBlock(getDataCenter().getCCTK().get(lastCCItem.cctkWz)));
        simpleTrainInfo.setNoticeStart(train.getNoticeStart());
        simpleTrainInfo.setNoticeEnd(train.getNoticeEnd());
        simpleTrainInfo.setDdj(String.valueOf(train.getDdj()));
        simpleTrainInfo.setTrainBJ(train.getTrainBJ());
        simpleTrainInfo.setHcStart(train.getHcStart());
        simpleTrainInfo.setHcEnd(train.getHcEnd());
        simpleTrainInfo.setCaceStart(train.getCaceStart());
        simpleTrainInfo.setCaceEnd(train.getCaceEnd());
        NoticeBlock noticeBlock = new NoticeBlock();
        noticeBlock.setKsrq(simpleTrainInfo.getKsrq());
        noticeBlock.setJsrq(simpleTrainInfo.getJsrq());
        noticeBlock.setKxgl(simpleTrainInfo.getKxgl());
        noticeBlock.setKxzq((byte) simpleTrainInfo.getKxzq());
        noticeBlock.setDay((byte) simpleTrainInfo.getYxts());
        simpleTrainInfo.setNoticeBlock(noticeBlock);
        simpleTrainInfo.setBasicYunXingInfo(new YunXingInfo(train.getTrainKxzq(), train.getTrainKxgl(), train.getTrainKsrq(), train.getTrainJsrq()));
        return simpleTrainInfo;
    }

    public int getInfoByfs0(String filename) {
        this.myCCdetails = new ArrayList<>();
        this.myCClist = new ArrayList<>();
        return getinfoby(this.tNO, filename);
    }

    public int getInfoByfs1() {
        this.myCCdetails = new ArrayList<>();
        this.myCClist = new ArrayList<>();
        int Rtn1 = getinfoby(this.tNO, "ccdzzm");
        ArrayList<String> arrayList = new ArrayList<>();
        this.ZMCCS = arrayList;
        arrayList.add(this.tNO);
        int Rtn2 = getinfoby(this.tNO, "ccdz");
        return (Rtn1 > -1 || Rtn2 > -1) ? 0 : -1;
    }

    private DataCenter getDataCenter() {
        return new DataCenter();
    }

    public CoreZZCX getCoreZZCX() {

        return this.coreZZCX;
    }

    public void setCoreZZCX(CoreZZCX coreZZCX) {
        this.coreZZCX = coreZZCX;
    }

    public int getRq() {
        return this.rq;
    }

    public void setRq(int rq) {
        this.rq = rq;
    }
}