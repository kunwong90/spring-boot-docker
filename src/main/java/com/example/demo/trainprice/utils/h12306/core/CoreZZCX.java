package com.example.demo.trainprice.utils.h12306.core;


import com.example.demo.trainprice.utils.h12306.datacenter.DataCenter;
import com.example.demo.trainprice.utils.h12306.pojo.*;
import com.example.demo.trainprice.utils.h12306.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.util.*;

/**
 * 站站查询
 */
public class CoreZZCX {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreZZCX.class);
    private DataCenter dataCenter = new DataCenter();
    private int ErrCode;
    private double bxf;

    private int jflc;
    private int rq;
    private NumberFormat numberFormat = null;
    private ArrayList<Integer> kSetCHstations = null;
    private HashMap<String, HashMap<String, byte[]>> pjKeysets = null;
    private HashMap<String, HashMap<String, byte[]>> priceKeysets = null;
    private String tmpDZ = null;


    public double[] CountPJ(int lcx) {
        double[] RePJ = new double[2];
        // 计费里程
        this.jflc = 0;
        double jbpj = 0.0d;
        if (lcx <= 200) {
            int i = ((lcx / 10) * 10) + 5;
            this.jflc = i;
            if (i < 20) {
                RePJ[0] = 1.0d;
                RePJ[1] = 1.0d;
                this.bxf = 0.1d;
                return RePJ;
            }
        }
        if (lcx <= 200) {
            this.jflc = (((lcx - 1) / 10) * 10) + 5;
        } else if (lcx >= 201 && lcx <= 400) {
            this.jflc = (((lcx - 201) / 20) * 20) + 210;
        } else if (lcx >= 401 && lcx <= 700) {
            this.jflc = (((lcx - 401) / 30) * 30) + 415;
        } else if (lcx >= 701 && lcx <= 1100) {
            this.jflc = (((lcx - 701) / 40) * 40) + 720;
        } else if (lcx >= 1101 && lcx <= 1600) {
            this.jflc = (((lcx - 1101) / 50) * 50) + 1125;
        } else if (lcx >= 1601 && lcx <= 2200) {
            this.jflc = (((lcx - 1601) / 60) * 60) + 1630;
        } else if (lcx >= 2201 && lcx <= 2900) {
            this.jflc = (((lcx - 2201) / 70) * 70) + 2235;
        } else if (lcx >= 2901 && lcx <= 3700) {
            this.jflc = (((lcx - 2901) / 80) * 80) + 2940;
        } else if (lcx >= 3701 && lcx <= 4600) {
            this.jflc = (((lcx - 3701) / 90) * 90) + 3745;
        } else if (lcx >= 4601) {
            this.jflc = (((lcx - 4601) / 100) * 100) + 4650;
        }
        int i2 = this.jflc;
        if (i2 <= 200) {
            jbpj = i2 * 0.05861d;
        }
        if (i2 >= 201 && i2 <= 500) {
            jbpj = ((i2 - 200) * 0.05861d * 0.9d) + 11.722d;
        }
        if (i2 >= 501 && i2 <= 1000) {
            jbpj = ((i2 - 500) * 0.05861d * 0.8d) + 27.5476d;
        }
        if (i2 >= 1001 && i2 <= 1500) {
            jbpj = ((i2 - 1000) * 0.05861d * 0.7d) + 50.9907d;
        }
        if (i2 >= 1501 && i2 <= 2500) {
            jbpj = ((i2 - 1500) * 0.05861d * 0.6d) + 71.5042d;
        }
        if (i2 >= 2501) {
            jbpj = ((i2 - 2500) * 0.05861d * 0.5d) + 106.6702d;
        }
        double bx = Math.ceil((0.02d * jbpj) * 10.0d) / 10.0d;
        StringBuilder sb = new StringBuilder(String.format("%.2f", bx));
        int lastCharPos = sb.length() - 1;
        if (sb.charAt(lastCharPos) != '0') {
            bx = Double.parseDouble(sb.deleteCharAt(lastCharPos).toString()) + 0.1d;
        }
        double jcpj = jbpj + bx;
        this.bxf = bx;
        RePJ[0] = Math.round(jcpj);
        RePJ[1] = jbpj;
        return RePJ;
    }

    /**
     * @param lcdj
     * @param pjsf
     * @param sfkt     是否空调
     * @param JcJb
     * @param distance 距离
     * @param ZXBS
     * @return
     */
    public PJInfo CountSJPJ(int lcdj, int pjsf, boolean sfkt, double[] JcJb, int distance, String ZXBS) {
        double JBPJ_R;
        PJInfo RET;
        PJInfo RET2;
        double[] wp;
        double KT;
        double JBPJ;
        double JBPJ_R2;
        double jk;
        PJInfo pjResult;
        Object obj;
        double ed;
        double yd;
        double d;
        double d2;
        // 加快票价
        double jk2 = 0.0d;
        // 空调票价
        double kt2 = 0.0d;
        PJInfo RET4 = new PJInfo();
        double JBPJ2 = JcJb[0];
        if (distance >= 20) {
            JBPJ_R = Math.round((JcJb[1] * 2.0d) + this.bxf);
        } else {
            JBPJ_R = 2.0d;
        }
        if (lcdj != 52) {
            if (distance < 130) {
                jk2 = 1.0d;
            } else {
                jk2 = Math.round(JcJb[1] * 0.2d);
            }
        }
        if (sfkt) {
            if (distance < 100) {
                kt2 = 1.0d;
            } else {
                kt2 = Math.rint(JcJb[1] * 0.25d);
            }
        }
        // 卧铺 0:硬卧上;1:硬卧中;2:硬卧下;3:软卧上;4:软卧下
        double[] wp2 = new double[5];
        if (distance >= 400) {
            wp2[0] = Math.round(JcJb[1] * 1.1d);
            RET = RET4;
            wp2[1] = Math.round(JcJb[1] * 1.2d);
            wp2[2] = Math.round(JcJb[1] * 1.3d);
            wp2[3] = Math.round(JcJb[1] * 1.75d);
            wp2[4] = Math.round(JcJb[1] * 1.95d);
        } else {
            RET = RET4;
            wp2[0] = 24.0d;
            wp2[1] = 27.0d;
            wp2[2] = 29.0d;
            wp2[3] = 39.0d;
            wp2[4] = 43.0d;
        }
        if (pjsf == 1) {
            RET2 = RET;
            wp = wp2;
            KT = kt2;
            JBPJ = JBPJ2;
            JBPJ_R2 = JBPJ_R;
            jk = jk2;
        } else {
            float sf = (pjsf / 100.0f) + 1.0f;
            RET2 = RET;
            double JBPJ3 = Math.round(sf * JBPJ2);
            double JBPJ_R3 = Math.round(sf * JBPJ_R);
            int i = 0;
            while (i < 5) {
                double[] wp3 = wp2;
                wp3[i] = Math.round(wp2[i] * sf);
                i++;
                wp2 = wp3;
            }
            wp = wp2;
            double JK3 = Math.round(sf * jk2);
            if (lcdj != 51) {
                JK3 = Math.round(JK3 * 2.0d);
            }
            KT = Math.round(sf * kt2);
            JBPJ = JBPJ3;
            JBPJ_R2 = JBPJ_R3;
            jk = JK3;
        }
        int ZPJ2 = (int) Math.round(JBPJ + jk + KT);
        int ZPJ_R = (int) Math.round(JBPJ_R2 + jk + KT);
        // 候车室空调费
        int hckt = distance > 200 ? 1 : 0;
        int ZPJ3 = ZPJ2 + hckt;
        double kcbxf = getNewBxf(this.bxf);
        if (JBPJ > 5.0d) {
            int ZPJ4 = ZPJ3 + 1;
            pjResult = RET2;
            pjResult.setYz(getNumberFormat().format(ZPJ4 - kcbxf));

        } else {
            pjResult = RET2;
            pjResult.setYz(getNumberFormat().format((ZPJ3 + 0.5d) - kcbxf));

        }
        if (JBPJ_R2 > 5.0d) {
            ZPJ_R++;
            pjResult.setRz(getNumberFormat().format(ZPJ_R - kcbxf));
        } else {
            pjResult.setRz(getNumberFormat().format((ZPJ_R + 0.5d) - kcbxf));
        }
        int[] plus = new int[4];
        int i2 = 0;
        for (int i3 = 4; i2 < i3; ) {
            String ps = ZXBS.substring(i2, i2 + 1);
            plus[i2] = Integer.parseInt(ps);
            i2++;
        }
        pjResult.setYdz("—");
        pjResult.setEdz("—");
        String fck = Integer.toBinaryString(plus[1]);
        int slen = fck.length();
        StringBuilder zr = new StringBuilder();
        int ZPJ5 = 0;
        while (true) {
            double JK4 = jk;
            if (ZPJ5 >= 3 - slen) {
                break;
            }
            zr.append("0");
            ZPJ5++;
            jk = JK4;
        }
        String fck2 = zr.append(fck).toString();
        if (fck2.substring(0, 1).equals("0")) {
            obj = "0";
        } else {
            if (lcdj == 68) {
                double d3 = this.bxf;
                double yd2 = (distance * 0.3366d * 1.1d) + d3;
                double yd3 = distance;
                ed = d3 + (yd3 * 0.2805d * 1.1d);
                obj = "0";
                yd = yd2;
            } else {
                if (lcdj == 56) {
                    obj = "0";
                } else if (lcdj == 71) {
                    obj = "0";
                } else {
                    obj = "0";
                }
                double d6 = this.bxf;
                double yd5 = (distance * 0.48d * 1.2d) + d6;
                ed = d6 + (distance * 0.48d);
                yd = yd5;
            }
            if (yd <= 5.0d) {
                d = 0.5d;
                d2 = yd + 0.5d;
            } else {
                d = 0.5d;
                d2 = yd + 1.0d;
            }
            double yd6 = d2;
            double ed2 = ed <= 5.0d ? ed + d : 1.0d + ed;
            pjResult.setYdz(String.format("%s", Math.round(Math.round(yd6) - kcbxf)));
            pjResult.setEdz(String.format("%s", Math.round(Math.round(ed2) - kcbxf)));
        }
        pjResult.setYws("—");
        pjResult.setYwz("—");
        pjResult.setYwx("—");
        if (plus[2] != 0) {
            // 硬座票价
            double YZPJ = Double.parseDouble(pjResult.getYz());
            pjResult.setYws(getNumberFormat().format(wp[0] + YZPJ + 10.0d));
            pjResult.setYwz(getNumberFormat().format(wp[1] + YZPJ + 10.0d));
            pjResult.setYwx(getNumberFormat().format(wp[2] + YZPJ + 10.0d));
        }
        pjResult.setRws("—");
        pjResult.setRwx("—");
        if (plus[3] != 0) {
            // 软座票价
            double rzpj = Double.parseDouble(pjResult.getRz());
            pjResult.setRws(getNumberFormat().format(wp[3] + rzpj + 10.0d));
            pjResult.setRwx(getNumberFormat().format(wp[4] + rzpj + 10.0d));
        }
        if (plus[0] == 0) {
            pjResult.setYz("—");
        }
        if (fck2.substring(2, 3).equals(obj)) {
            pjResult.setRz("—");
        }
        return pjResult;
    }

    public int getZMWZByName(String zm) {
        return dataCenter.getZMHZSY1().indexOf(zm);
    }

    public int[] getstation(String CHstation, int SearchType) {
        int[] ReSE = {0, 0, 0, 0};
        String strReturn = CHstation.trim();
        int zw = getZMWZByName(strReturn);
        if (zw < 0) {
            return ReSE;
        }
        int[] ReSE2 = getstationSE(zw);
        return ReSE2;
    }

    public HashSet<Short> getCHstationIdxs_zmhzsy1(String CHstation) {
        HashSet<Short> result = null;
        int zw = getZMWZByName(CHstation);
        if (zw >= 0) {
            result = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                String newCHstation = dataCenter.getZMHZSY1().get(zw);
                if (!newCHstation.startsWith(CHstation)) {
                    break;
                }
                result.add(Short.valueOf((short) zw));
                zw++;
            }
        }
        return result;
    }

    public int[] getstationSE(int pos) {
        int[] ReSE = {(Integer) dataCenter.getZMHZSY2()[0].get(pos), (Integer) dataCenter.getZMHZSY2()[1].get(pos), (Integer) dataCenter.getZMHZSY2()[2].get(pos), (Integer) dataCenter.getZMHZSY2()[3].get(pos), (Integer) dataCenter.getZMHZSY2()[4].get(pos), (Integer) dataCenter.getZMHZSY2()[5].get(pos)};
        return ReSE;
    }

    public Hashtable<Short, String> getPassTrain1(int from, int to) {
        Hashtable<Short, String> Res = new Hashtable<>((to - from) + 1);
        for (int i = from; i <= to; i++) {
            short ccwz = ByteBuffer.wrap(dataCenter.getCCTK().get(i)).getShort();
            Object RenameTrain = Res.get(Short.valueOf(ccwz));
            if (RenameTrain == null) {
                Res.put(Short.valueOf(ccwz), Integer.toString(i));
            } else {
                Res.put(Short.valueOf(ccwz), i + "," + RenameTrain);
            }
        }
        return Res;
    }

    public HashMap<String, byte[]> loadPjsets(int from, int to, boolean price) {
        int loops;
        String fileName = "zjpj.dat";
        if (price) {
            fileName = "price.dat";
        }
        String fileName2 = fileName;
        HashMap<String, byte[]> hashMap = new HashMap<>();
        char c = 22;
        int loops2 = (to - from) + 1;
        int curCoursor = from;

        try {
            try {
                int startfileIdx = from / 1000;
                int skipval = (from % 1000) * 22;
                InputStream in = dataCenter.getiOFactory().getInpustStream(fileName2 + startfileIdx);
                BufferedInputStream bis = new BufferedInputStream(in);
                DataInputStream dis = new DataInputStream(bis);
                if (skipval > 0) {
                    try {
                        dis.skip(skipval);
                    } catch (Exception e) {
                        System.out.println("readUTF Error:" + e);
                        return hashMap;
                    }
                }
                byte[] bts = new byte[18];
                int i = 0;
                while (i < loops2) {
                    int dzwz = dis.readShort();
                    int ccwz = dis.readShort();
                    dis.read(bts);
                    char c2 = c;
                    try {
                        String tmp = dzwz + "-" + ccwz;
                        if (hashMap.containsKey(tmp)) {
                            loops = loops2;
                            try {
                                //LogPrint.v(LogPrint.TAG, "重复数据如何处理？？？？？？？");
                                LOGGER.error("重复数据如何处理？？？？？？？");
                            } catch (Exception e2) {
                                // e = e2;
                                System.out.println("readUTF Error:" + e2);
                                return hashMap;
                            }
                        } else {
                            loops = loops2;
                            hashMap.put(tmp, bts.clone());
                        }
                        curCoursor++;
                        if (curCoursor % 1000 == 0 && curCoursor > 0) {
                            dis.close();
                            bis.close();
                            startfileIdx++;
                            in = dataCenter.getiOFactory().getInpustStream(fileName2 + startfileIdx);
                            BufferedInputStream bis2 = new BufferedInputStream(in);
                            dis = new DataInputStream(bis2);
                            bis = bis2;
                        }
                        i++;
                        c = c2;
                        loops2 = loops;
                    } catch (Exception e3) {
                        //e = e3;
                        System.out.println("readUTF Error:" + e3);
                        return hashMap;
                    } catch (Throwable th2) {
                        //th = th2;
                        LOGGER.error("", th2);
                        throw th2;
                    }
                }
                dis.close();
                bis.close();
                in.close();
            } catch (Throwable th3) {
                //th = th3;
                LOGGER.error("", th3);
            }
        } catch (Exception e4) {
            //e = e4;
            LOGGER.error("", e4);
        } catch (Throwable th4) {
            //th = th4;
            LOGGER.error("", th4);
        }
        return hashMap;
    }

    public SimpleTrainInfo getTraininfo(int Pos) {
        ByteBuffer btsBuffer = ByteBuffer.wrap(dataCenter.getCCData().get(Pos));
        SimpleTrainInfo trainInfo = new SimpleTrainInfo();
        trainInfo.setTrainName(dataCenter.getCC().get(Pos));
        trainInfo.setTrainDJ(btsBuffer.get());
        trainInfo.setTrainSf(btsBuffer.get());
        trainInfo.setTrainInCCTKSYs(btsBuffer.getInt());
        trainInfo.setTrainInCCTKSYe(btsBuffer.getInt());
        trainInfo.setTrainSfkt(btsBuffer.get() + (-48) == 1);
        trainInfo.setTrainXw(String.format("%04d", btsBuffer.getShort()));
        trainInfo.setTrainBJ(btsBuffer.get() - 48);
        trainInfo.setTrainKxzq(btsBuffer.get());
        trainInfo.setTrainKxgl(btsBuffer.getInt());
        trainInfo.setTrainKsrq(btsBuffer.getInt());
        trainInfo.setTrainJsrq(btsBuffer.getInt());
        trainInfo.setNoticeStart(btsBuffer.getInt());
        trainInfo.setNoticeEnd(btsBuffer.getInt());
        // 数据来源 000_ddj.json
        trainInfo.setDdj(((char) btsBuffer.get()) + "" + ((char) btsBuffer.get()) + "" + ((char) btsBuffer.get()));
        trainInfo.setHcStart(btsBuffer.getInt());
        trainInfo.setHcEnd(btsBuffer.getInt());
        trainInfo.setPjdmStart(btsBuffer.getInt());
        trainInfo.setPjdmEnd(btsBuffer.getInt());
        trainInfo.setCaceStart(btsBuffer.getInt());
        trainInfo.setCaceEnd(btsBuffer.getInt());
        trainInfo.setId(Pos);
        return trainInfo;
    }

    public String toTime(short sTime) {
        int h = sTime / 100;
        int m = sTime % 100;
        return (h < 10 ? "0" + h : Integer.valueOf(h)) + ":" + (m < 10 ? "0" + m : Integer.valueOf(m));
    }

    public String lcdj(int lcdj, boolean sfkt) {
        String dj = "未知";
        if (lcdj == 56) {
            dj = "城际高速";
        } else if (lcdj == 65) {
            dj = "快速";
        } else if (lcdj == 68) {
            dj = "动车组";
        } else if (lcdj != 71) {
            switch (lcdj) {
                case 49:
                    dj = "直达";
                    break;
                case 50:
                    dj = "特快";
                    break;
                case 51:
                    dj = "普快";
                    break;
                case 52:
                    dj = "普客";
                    break;
                case 53:
                    dj = "市郊列车";
                    break;
            }
        } else {
            dj = "高速动车";
        }
        return (!sfkt || dj.equals("动车组") || dj.equals("城际高速") || dj.equals("高速动车") || dj.equals("市郊列车")) ? dj : "新空" + dj;
    }

    public TrainInfo getxInfo(int CCID, String fullTrainName, String realTrainName, PJInfo pj, short[] Bds, String[] Rds, CCTKBlock Sclp, CCTKBlock Eclp, String dj, int Lc, String[] sfzd, int kxzq, int kxgl, int ksrq, int jsrq, String RcdId, int noticeStart, int noticeEnd, String ddj, int hcStart, int hcEnd, int trainBJ, int caceStart, int caceEnd, SimpleTrainInfo simpleTrainInfo) {
        TrainInfo trainInfo = new TrainInfo();
        trainInfo.setFullTrainNo(fullTrainName);
        trainInfo.setCc(realTrainName.trim());
        trainInfo.setDj(dj.trim());
        trainInfo.setDd(toTime(Sclp.getDd()));
        trainInfo.setKd(toTime(Sclp.getKd()));
        trainInfo.setDd1(toTime(Eclp.getDd()));
        trainInfo.setLc(Lc);
        trainInfo.setFz(Rds[0].trim());
        trainInfo.setDz(Rds[1].trim());
        trainInfo.setSfz(sfzd[0].trim());
        trainInfo.setZdz(sfzd[1].trim());
        trainInfo.setLs(Common.covert2StringTime(Common.getMinites(Eclp.getDd(), Eclp.getKd(), Sclp.getKd(), Eclp.getDay() - Sclp.getDay())));
        trainInfo.setPj(pj);
        trainInfo.setKxzq(kxzq);
        trainInfo.setKxgl(kxgl);
        trainInfo.setKsrq(ksrq);
        trainInfo.setYxts(Sclp.getDay() - 48);
        trainInfo.setJsrq(jsrq);
        trainInfo.setId(RcdId);
        trainInfo.setCcId(CCID);
        trainInfo.setFzCCTKBlock(Sclp);
        trainInfo.setDzCCTKBlock(Eclp);
        trainInfo.setNoticeStart(noticeStart);
        trainInfo.setNoticeEnd(noticeEnd);
        trainInfo.setDdj(String.valueOf(ddj));
        trainInfo.setHcStart(hcStart);
        trainInfo.setHcEnd(hcEnd);
        trainInfo.setTrainBJ(trainBJ);
        trainInfo.setCaceStart(caceStart);
        trainInfo.setCaceEnd(caceEnd);
        NoticeBlock noticeBlock = new NoticeBlock();
        noticeBlock.setKsrq(trainInfo.getKsrq());
        noticeBlock.setJsrq(trainInfo.getJsrq());
        noticeBlock.setKxgl(trainInfo.getKxgl());
        noticeBlock.setKxzq((byte) trainInfo.getKxzq());
        noticeBlock.setDay((byte) trainInfo.getYxts());
        trainInfo.setNoticeBlock(noticeBlock);
        trainInfo.setBasicYunXingInfo(new YunXingInfo(simpleTrainInfo.getTrainKxzq(), simpleTrainInfo.getTrainKxgl(), simpleTrainInfo.getTrainKsrq(), simpleTrainInfo.getTrainJsrq()));
        return trainInfo;
    }

    public ArrayList<TrainInfo> GetPassTrainInfo(int[] sStar, int[] sEnd) {
        int i;
        String[] Rds;
        short[] Bds;
        int sStartTo;
        ArrayList<TrainInfo> cxResult;
        Hashtable<Short, String> Re2;
        int j;
        short lc_fz;
        int RenamesCount;
        String[] TrainRenames;
        int i2;
        String[] Rds2;
        short[] Bds2;
        int sStartTo2;
        ArrayList<TrainInfo> cxResult2;
        short ccwz;
        Hashtable<Short, String> Re22;
        int Lc;
        Hashtable<Short, String> Re23;
        ArrayList<TrainInfo> cxResult3;
        int j2;
        short lc_dz;
        int i3;
        PJInfo mPJInfo;
        CoreZZCX coreZZCX = this;
        ArrayList<TrainInfo> cxResult4 = new ArrayList<>();
        PJInfo mPJInfo2 = null;
        Hashtable<Short, String> Re24 = coreZZCX.getPassTrain1(sEnd[0], sEnd[1]);
        int sStarFrom = sStar[0];
        int sStartTo3 = sStar[1];
        int i4 = 2;
        short[] Bds3 = new short[2];
        String[] Rds3 = new String[2];
        int i5 = sStarFrom;
        while (i5 <= sStartTo3) {
            CCTKBlock cctkFz = new CCTKBlock(dataCenter.getCCTK().get(i5));
            short ccwz2 = cctkFz.getCcwz();
            Object iobj = Re24.get(Short.valueOf(ccwz2));
            if (iobj == null) {
                i = i5;
                Rds = Rds3;
                Bds = Bds3;
                sStartTo = sStartTo3;
                cxResult = cxResult4;
                Re2 = Re24;
            } else {
                String[] TrainRenames2 = iobj.toString().split(",");
                int RenamesCount2 = TrainRenames2.length;
                // 出发站所在里程
                short lc_fz2 = cctkFz.getLc();
                int j3 = 0;
                PJInfo mPJInfo3 = mPJInfo2;
                while (j3 < RenamesCount2) {
                    int idx_dz = Integer.parseInt(TrainRenames2[j3]);
                    // 到达站所在里程
                    short lc_dz2 = ByteBuffer.wrap(dataCenter.getCCTK().get(idx_dz)).getShort(i4);
                    if (lc_dz2 > lc_fz2) {
                        SimpleTrainInfo tInfo = coreZZCX.getTraininfo(ccwz2);
                        int j4 = j3;
                        int RenamesCount3 = RenamesCount2;
                        CCTKBlock cctkDz = new CCTKBlock(dataCenter.getCCTK().get(idx_dz));
                        String[] SfZd = coreZZCX.getTrainSEinfo(tInfo.getTrainInCCTKSYs(), tInfo.getTrainInCCTKSYe());
                        String dj = coreZZCX.lcdj(tInfo.getTrainDJ(), tInfo.isTrainSfkt());
                        int Lc2 = lc_dz2 - lc_fz2;
                        if (tInfo.getTrainBJ() == 1) {
                            double[] JcJb = coreZZCX.CountPJ(Lc2);
                            cxResult3 = cxResult4;
                            j2 = j4;
                            lc_fz = lc_fz2;
                            RenamesCount = RenamesCount3;
                            Lc = Lc2;
                            TrainRenames = TrainRenames2;
                            lc_dz = ccwz2;
                            Re23 = Re24;
                            i3 = i5;
                            mPJInfo = CountSJPJ(tInfo.getTrainDJ(), tInfo.getTrainSf(), tInfo.isTrainSfkt(), JcJb, Lc, tInfo.getTrainXw());
                            Rds2 = Rds3;
                        } else {
                            lc_fz = lc_fz2;
                            Lc = Lc2;
                            TrainRenames = TrainRenames2;
                            Re23 = Re24;
                            cxResult3 = cxResult4;
                            j2 = j4;
                            RenamesCount = RenamesCount3;
                            lc_dz = ccwz2;
                            i3 = i5;
                            if (tInfo.getPjdmStart() != 99999) {
                                Rds2 = Rds3;
                                mPJInfo = getPrice(tInfo.getTrainName(), cctkFz.getZmwz(), cctkDz.getZmwz(), tInfo.getPjdmStart(), tInfo.getPjdmEnd(), getRq(), cctkFz.getDay() - 48);
                            } else {
                                Rds2 = Rds3;
                                mPJInfo = coreZZCX.getPrice(cctkFz.getZmwz(), cctkDz.getZmwz(), lc_dz);
                            }
                        }
                        Bds3[0] = cctkFz.getZmwz();
                        Bds3[1] = cctkDz.getZmwz();
                        for (int s = 0; s < 2; s++) {
                            Rds2[s] = dataCenter.getZMHZSY1().get(Bds3[s]).trim();
                        }
                        int s2 = cctkFz.getCzcc();
                        int posinfull = s2 - 48;
                        int sStartTo4 = tInfo.getId();
                        String str = tInfo.getTrainName();
                        String str2 = tInfo.getTrainName();
                        if (posinfull != 0) {
                            str2 = str2.split("/")[posinfull - 1];
                        }
                        Bds2 = Bds3;
                        sStartTo2 = sStartTo3;
                        Re22 = Re23;
                        i2 = i3;
                        String str3 = str2;
                        ccwz = lc_dz;
                        j = j2;
                        cxResult2 = cxResult3;
                        cxResult2.add(getxInfo(sStartTo4, str, str3, mPJInfo, Bds2, Rds2, cctkFz, cctkDz, dj, Lc, SfZd, tInfo.getTrainKxzq(), tInfo.getTrainKxgl(), tInfo.getTrainKsrq(), tInfo.getTrainJsrq(), i3 + "-" + j2, tInfo.getNoticeStart(), tInfo.getNoticeEnd(), tInfo.getDdj(), tInfo.getHcStart(), tInfo.getHcEnd(), tInfo.getTrainBJ(), tInfo.getCaceStart(), tInfo.getCaceEnd(), tInfo));
                        mPJInfo3 = mPJInfo;
                    } else {
                        j = j3;
                        lc_fz = lc_fz2;
                        RenamesCount = RenamesCount2;
                        TrainRenames = TrainRenames2;
                        i2 = i5;
                        Rds2 = Rds3;
                        Bds2 = Bds3;
                        sStartTo2 = sStartTo3;
                        cxResult2 = cxResult4;
                        ccwz = ccwz2;
                        Re22 = Re24;
                    }
                    j3 = j + 1;
                    coreZZCX = this;
                    Bds3 = Bds2;
                    sStartTo3 = sStartTo2;
                    Re24 = Re22;
                    ccwz2 = ccwz;
                    cxResult4 = cxResult2;
                    lc_fz2 = lc_fz;
                    RenamesCount2 = RenamesCount;
                    TrainRenames2 = TrainRenames;
                    i5 = i2;
                    Rds3 = Rds2;
                    i4 = 2;
                }
                i = i5;
                Rds = Rds3;
                Bds = Bds3;
                sStartTo = sStartTo3;
                cxResult = cxResult4;
                Re2 = Re24;
                mPJInfo2 = mPJInfo3;
            }
            coreZZCX = this;
            i5 = i + 1;
            Bds3 = Bds;
            sStartTo3 = sStartTo;
            Re24 = Re2;
            cxResult4 = cxResult;
            Rds3 = Rds;
            i4 = 2;
        }
        return cxResult4;
    }

    public String GetZMbyCC(int pos) {
        int CCTKpos = (Integer) dataCenter.getCCTKSY()[0].get(pos);
        byte[] bts = dataCenter.getCCTK().get(CCTKpos);
        short idx = ByteBuffer.wrap(bts).getShort(8);
        return getZM(idx);
    }

    public Short GetZMbyCC_zmhzsyPos(int pos) {
        int CCTKpos = (Integer) dataCenter.getCCTKSY()[0].get(pos);
        byte[] bts = dataCenter.getCCTK().get(CCTKpos);
        return ByteBuffer.wrap(bts).getShort(8);
    }

    public String getZM(int pos) {
        return dataCenter.getZMHZSY1().get(pos);
    }

    public String[] getTrainSEinfo(int StarPos, int EndPos) {
        String StarName = GetZMbyCC(StarPos);
        String EndName = GetZMbyCC(EndPos);
        return new String[]{StarName, EndName};
    }

    public void resetNessaryVariable() {
        this.bxf = 0.0d;
        setErrCode(0);
        this.kSetCHstations = null;
        this.pjKeysets = null;
        this.priceKeysets = null;
    }

    public List<TrainInfo> query(String fz, String dz, int rq) {
        resetNessaryVariable();
        this.tmpDZ = dz;
        setRq(rq);
        int[] ST = getstation(fz, 0);
        int[] ET = getstation(dz, 0);
        String errorStation = "";
        int RtnVal = 0;
        if (ST[1] == 0) {
            errorStation = "起始站";
            RtnVal = 1;
        }
        if (ET[1] == 0) {
            errorStation = "终点站";
            RtnVal = 2;
        }
        if (ST[1] == 0 && ET[1] == 0) {
            errorStation = "起始站和终点站";
            RtnVal = 3;
        }
        if (errorStation.length() > 0) {
            setErrCode(RtnVal);
            return null;
        }
        return GetPassTrainInfo(ST, ET);
    }

    public HashSet<Short> searchByTransfer(String fz, String dz) {
        int[] sStar = getstation(fz, 0);
        int[] sEnd = getstation(dz, 0);
        HashSet<Short> stationsStar = getAllStationPass_cctkPos(sStar[0], sStar[1], getCHstationIdxs_zmhzsy1(fz), true);
        HashSet<Short> stationsEnd = getAllStationPass_cctkPos(sEnd[0], sEnd[1], getCHstationIdxs_zmhzsy1(dz), false);
        stationsStar.retainAll(stationsEnd);
        return stationsStar;
    }

    public int[] getMinDistance(String fz, String dz) {
        int[] sStar = getstation(fz, 0);
        int[] sEnd = getstation(dz, 0);
        Hashtable Re2 = getPassTrain1(sEnd[0], sEnd[1]);
        int sStarFrom = sStar[0];
        int sStartTo = sStar[1];
        int trainCounter = 0;
        int minDistance = 9999999;
        for (int i = sStarFrom; i <= sStartTo; i++) {
            short ccwz = ByteBuffer.wrap(dataCenter.getCCTK().get(i)).getShort();
            Object iobj = Re2.get(Short.valueOf(ccwz));
            if (iobj != null) {
                String[] TrainRenames = iobj.toString().split(",");
                int RenamesCount = TrainRenames.length;
                short lc_fz = ByteBuffer.wrap(dataCenter.getCCTK().get(i)).getShort(2);
                int j = 0;
                while (j < RenamesCount) {
                    String[] TrainRenames2 = TrainRenames;
                    int idx_dz = Integer.parseInt(TrainRenames[j]);
                    short lc_dz = ByteBuffer.wrap(dataCenter.getCCTK().get(idx_dz)).getShort(2);
                    if (lc_dz > lc_fz) {
                        int tmpDistance = lc_dz - lc_fz;
                        minDistance = Math.min(tmpDistance, minDistance);
                        trainCounter++;
                    }
                    j++;
                    TrainRenames = TrainRenames2;
                }
            }
        }
        return new int[]{minDistance, trainCounter};
    }

    public HashSet<Short> getAllStationPass_cctkPos(int cctkStart, int cctkEnd, HashSet<Short> stationIdxs, boolean afterStations) {
        HashSet<Short> hSet = new HashSet<>();
        for (int i = cctkStart; i < cctkEnd; i++) {
            short ccwz = ByteBuffer.wrap(dataCenter.getCCTK().get(i)).getShort();
            boolean beginToadd = false;
            SimpleTrainInfo trainInfo = getTraininfo(ccwz);
            for (int j = trainInfo.getTrainInCCTKSYs(); j <= trainInfo.getTrainInCCTKSYe(); j++) {
                short zmIdx = GetZMbyCC_zmhzsyPos(j);
                if (afterStations) {
                    if (beginToadd) {
                        hSet.add(zmIdx);
                    }
                    if (stationIdxs.contains(zmIdx)) {
                        beginToadd = true;
                    }
                } else {
                    if (!beginToadd) {
                        hSet.add(zmIdx);
                    }
                    if (stationIdxs.contains(zmIdx)) {
                        beginToadd = true;
                    }
                }
            }
        }
        return hSet;
    }

    public double getNewBxf(double bxfBefore) {
        if (bxfBefore * 10.0d * 0.2d == ((int) Math.floor(bxfBefore * 10.0d * 0.2d))) {
            return ((int) Math.floor((10.0d * bxfBefore) * 0.2d)) / 2.0f;
        }
        return (((int) Math.floor((10.0d * bxfBefore) * 0.2d)) + 1) / 2.0f;
    }

    public PJInfo getPrice(int fzZMWZ, int dzZMWZ, int ccwz) {
        HashMap<String, byte[]> priceSets = getPjKeysets().get(Integer.valueOf(fzZMWZ));
        if (priceSets == null) {
            priceSets = getPJByZMWZ(fzZMWZ);
        }
        byte[] pjBts = priceSets.get(dzZMWZ + "-" + ccwz);
        return getPJInfoFromBytes(pjBts);
    }

    public PJInfo getPrice(String train, int fzZMWZ, int dzZMWZ, int pjdmStart, int pjdmEnd, int rq, int yxts) {
        int i = pjdmEnd;
        PJInfo pjInfo = new PJInfo();
        HashMap<String, byte[]> priceSets = getPriceKeysets().get(fzZMWZ);
        if (priceSets == null) {
            priceSets = getPriceByZMWZ(fzZMWZ);
            getPriceKeysets().put(String.valueOf(fzZMWZ), priceSets);
        }
        char c = 5;
        if (pjdmStart != i && rq > 0) {
            int i2 = pjdmStart;
            while (i2 <= i) {
                int ksrq = (Integer) dataCenter.getPJDM()[0].get(i2);
                int jsrq = (Integer) dataCenter.getPJDM()[1].get(i2);
                int xw = (Byte) dataCenter.getPJDM()[2].get(i2);
                int kxgl = (Integer) dataCenter.getPJDM()[3].get(i2);
                int kxzq = (Byte) dataCenter.getPJDM()[4].get(i2);
                int pjcode = (Integer) dataCenter.getPJDM()[c].get(i2);
                int i3 = i2;
                int todayState = ZZCXCenter.trainStateToday(rq, ksrq, jsrq, kxzq, kxgl, yxts, false, null);
                if (todayState == 1) {
                    byte[] pjBts = priceSets.get(dzZMWZ + "-" + pjcode);
                    PJInfo pjInfoTemp = getPJInfoFromBytes(pjBts);
                    if (xw == 51) {
                        pjInfo.setYws(pjInfoTemp.getYws());
                        pjInfo.setYwz(pjInfoTemp.getYwz());
                        pjInfo.setYwx(pjInfoTemp.getYwx());
                    } else if (xw == 52 || xw == 65 || xw == 70) {
                        pjInfo.setRws(pjInfoTemp.getRws());
                        pjInfo.setRwx(pjInfoTemp.getRwx());
                    } else if (xw == 77) {
                        pjInfo.setYdz(pjInfoTemp.getYdz());
                    } else if (xw == 79) {
                        pjInfo.setEdz(pjInfoTemp.getEdz());
                    } else if (xw == 122) {
                        pjInfo = pjInfoTemp;
                    }
                }
                i2 = i3 + 1;
                i = pjdmEnd;
                c = 5;
            }
            return pjInfo;
        }
        int pjCode = (Integer) dataCenter.getPJDM()[5].get(pjdmStart);
        byte[] pjBts2 = priceSets.get(dzZMWZ + "-" + pjCode);
        return getPJInfoFromBytes(pjBts2);
    }

    public PJInfo getPrice(String train, int fzZMWZ, int dzZMWZ, int pjdmStart, int pjdmEnd, String ddddd) {
        return getPrice(train, fzZMWZ, dzZMWZ, pjdmStart, pjdmEnd, 0, 0);
    }

    public HashMap<String, byte[]> getPriceByZMWZ(int zmwz) {
        int pjdmStart = (Integer) dataCenter.getZMHZSY2()[4].get(zmwz);
        int pjdmEnd = (Integer) dataCenter.getZMHZSY2()[5].get(zmwz);
        return loadPjsets(pjdmStart, pjdmEnd, true);
    }

    public HashMap<String, byte[]> getPJByZMWZ(int zmwz) {
        int zjpjStart = (Integer) dataCenter.getZMHZSY2()[2].get(zmwz);
        int zjpjEnd = (Integer) dataCenter.getZMHZSY2()[3].get(zmwz);
        return loadPjsets(zjpjStart, zjpjEnd, false);
    }

    public PJInfo getPJInfoFromBytes(byte[] bts) {
        PJInfo pjInfo = new PJInfo();
        if (bts != null) {
            ByteBuffer bb = ByteBuffer.wrap(bts);
            Double tmppj = bb.getShort() / 10.0d;
            String str = "—";
            pjInfo.setYz(tmppj == 0.0d ? str : getNumberFormat().format(tmppj));
            Double tmppj2 = bb.getShort() / 10.0d;
            pjInfo.setRz(tmppj2 == 0.0d ? str : getNumberFormat().format(tmppj2));
            Double tmppj3 = bb.getShort() / 10.0d;
            pjInfo.setYws(tmppj3 == 0.0d ? str : getNumberFormat().format(tmppj3));
            Double tmppj4 = bb.getShort() / 10.0d;
            pjInfo.setYwz(tmppj4 == 0.0d ? str : getNumberFormat().format(tmppj4));
            Double tmppj5 = bb.getShort() / 10.0d;
            pjInfo.setYwx(tmppj5 == 0.0d ? str : getNumberFormat().format(tmppj5));
            Double tmppj6 = bb.getShort() / 10.0d;
            pjInfo.setRws(tmppj6 == 0.0d ? str : getNumberFormat().format(tmppj6));
            Double tmppj7 = bb.getShort() / 10.0d;
            pjInfo.setRwx(tmppj7 == 0.0d ? str : getNumberFormat().format(tmppj7));
            Double tmppj8 = bb.getShort() / 10.0d;
            pjInfo.setEdz(tmppj8 == 0.0d ? str : getNumberFormat().format(tmppj8));
            Double tmppj9 = bb.getShort() / 10.0d;
            if (tmppj9 != 0.0d) {
                str = getNumberFormat().format(tmppj9);
            }
            pjInfo.setYdz(str);
        } else {
            LOGGER.error("price 票价为空");
        }
        return pjInfo;
    }

    public int getErrCode() {
        return this.ErrCode;
    }

    public void setErrCode(int errCode) {
        this.ErrCode = errCode;
    }

    public ArrayList<Integer> getkSetCHstations() {
        return this.kSetCHstations;
    }

    public void setkSetCHstations(ArrayList<Integer> kSetCHstations) {
        this.kSetCHstations = kSetCHstations;
    }

    public HashMap<String, HashMap<String, byte[]>> getPriceKeysets() {
        if (this.priceKeysets == null) {
            this.priceKeysets = new HashMap<>();
        }
        return this.priceKeysets;
    }

    public HashMap<String, HashMap<String, byte[]>> getPjKeysets() {
        if (this.pjKeysets == null) {
            this.pjKeysets = new HashMap<>();
        }
        return this.pjKeysets;
    }

    public NumberFormat getNumberFormat() {
        if (this.numberFormat == null) {
            NumberFormat numberFormat = NumberFormat.getInstance();
            this.numberFormat = numberFormat;
            numberFormat.setGroupingUsed(false);
        }
        return this.numberFormat;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public int getRq() {
        return this.rq;
    }

    public void setRq(int rq) {
        this.rq = rq;
    }
}