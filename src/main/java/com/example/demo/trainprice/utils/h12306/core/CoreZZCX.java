package com.example.demo.trainprice.utils.h12306.core;


import com.example.demo.trainprice.utils.h12306.MediaEventListener;
import com.example.demo.trainprice.utils.h12306.NotificationManagerCompat;
import com.example.demo.trainprice.utils.h12306.TTAdConstant;
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
            this.jflc = (((lcx - 201) / 20) * 20) + MediaEventListener.EVENT_VIDEO_READY;
        } else if (lcx >= 401 && lcx <= 700) {
            this.jflc = (((lcx - 401) / 30) * 30) + TTAdConstant.VIDEO_COVER_URL_CODE;
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
            jbpj = ((i2 + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED) * 0.05861d * 0.7d) + 50.9907d;
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

    public PJInfo CountSJPJ(int lcdj, int pjsf, boolean sfkt, double[] JcJb, int distance, String ZXBS) {
        double JBPJ_R;
        PJInfo RET;
        PJInfo RET2;
        double[] wp;
        double KT;
        double JBPJ;
        double JBPJ_R2;
        double JK;
        PJInfo RET3;
        int ZPJ;
        Object obj;
        double ed;
        double yd;
        double d;
        double d2;
        double JK2 = 0.0d;
        double KT2 = 0.0d;
        PJInfo RET4 = new PJInfo();
        double JBPJ2 = JcJb[0];
        if (distance >= 20) {
            JBPJ_R = Math.round((JcJb[1] * 2.0d) + this.bxf);
        } else {
            JBPJ_R = 2.0d;
        }
        if (lcdj != 52) {
            if (distance < 130) {
                JK2 = 1.0d;
            } else {
                JK2 = Math.round(JcJb[1] * 0.2d);
            }
        }
        if (sfkt) {
            if (distance < 100) {
                KT2 = 1.0d;
            } else {
                KT2 = Math.rint(JcJb[1] * 0.25d);
            }
        }
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
            KT = KT2;
            JBPJ = JBPJ2;
            JBPJ_R2 = JBPJ_R;
            JK = JK2;
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
            double JK3 = Math.round(sf * JK2);
            if (lcdj != 51) {
                JK3 = Math.round(JK3 * 2.0d);
            }
            KT = Math.round(sf * KT2);
            JBPJ = JBPJ3;
            JBPJ_R2 = JBPJ_R3;
            JK = JK3;
        }
        int ZPJ2 = (int) Math.round(JBPJ + JK + KT);
        int ZPJ_R = (int) Math.round(JBPJ_R2 + JK + KT);
        int hckt = distance > 200 ? 1 : 0;
        int ZPJ3 = ZPJ2 + hckt;
        double kcbxf = getNewBxf(this.bxf, this.jflc, sfkt, lcdj);
        if (JBPJ > 5.0d) {
            int ZPJ4 = ZPJ3 + 1;
            RET3 = RET2;
            RET3.YZ = getNumberFormat().format(ZPJ4 - kcbxf);
            ZPJ = ZPJ4;
        } else {
            RET3 = RET2;
            RET3.YZ = getNumberFormat().format((ZPJ3 + 0.5d) - kcbxf);
            ZPJ = ZPJ3;
        }
        if (JBPJ_R2 > 5.0d) {
            ZPJ_R++;
            RET3.RZ = getNumberFormat().format(ZPJ_R - kcbxf);
        } else {
            RET3.RZ = getNumberFormat().format((ZPJ_R + 0.5d) - kcbxf);
        }
        int[] plus = new int[4];
        int i2 = 0;
        for (int i3 = 4; i2 < i3; i3 = 4) {
            String ps = ZXBS.substring(i2, i2 + 1);
            plus[i2] = Integer.parseInt(ps);
            i2++;
        }
        RET3.YD = "—";
        RET3.ED = "—";
        String fck = Integer.toBinaryString(plus[1]);
        int slen = fck.length();
        StringBuilder zr = new StringBuilder();
        int ZPJ5 = 0;
        while (true) {
            double JK4 = JK;
            if (ZPJ5 >= 3 - slen) {
                break;
            }
            zr.append("0");
            ZPJ5++;
            JK = JK4;
        }
        zr.append(fck);
        String fck2 = zr.toString();
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
                    double d4 = this.bxf;
                    double yd4 = (distance * 0.2796d) + d4;
                    obj = "0";
                    double d5 = d4 + (distance * 0.233d);
                    yd = yd4;
                    ed = d5;
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
            RET3.YD = String.format("%s", Long.valueOf(Math.round(Math.round(yd6) - kcbxf)));
            RET3.ED = String.format("%s", Long.valueOf(Math.round(Math.round(ed2) - kcbxf)));
        }
        RET3.YWs = "—";
        RET3.YWz = "—";
        RET3.YWx = "—";
        if (plus[2] != 0) {
            double YZPJ = Double.parseDouble(RET3.YZ);
            RET3.YWs = getNumberFormat().format(wp[0] + YZPJ + 10.0d);
            RET3.YWz = getNumberFormat().format(wp[1] + YZPJ + 10.0d);
            RET3.YWx = getNumberFormat().format(wp[2] + YZPJ + 10.0d);
        }
        RET3.RWs = "—";
        RET3.RWx = "—";
        if (plus[3] != 0) {
            double RZPJ = Double.parseDouble(RET3.RZ);
            RET3.RWs = getNumberFormat().format(wp[3] + RZPJ + 10.0d);
            RET3.RWx = getNumberFormat().format(wp[4] + RZPJ + 10.0d);
        }
        if (plus[0] == 0) {
            RET3.YZ = "—";
        }
        if (fck2.substring(2, 3).equals(obj)) {
            RET3.RZ = "—";
        }
        return RET3;
    }

    public short getZMWZByName(String zm) {
        return (short) getDataCenter().getZMHZSY1().indexOf(zm);
    }

    public int[] getstation(String CHstation, int SearchType) {
        int[] ReSE = {0, 0, 0, 0, 0, 0, 0};
        String strReturn = CHstation.trim();
        int zw = getZMWZByName(strReturn);
        if (zw < 0) {
            return ReSE;
        }
        int[] ReSE2 = getstationSE(zw);
        return SearchType >= 1 ? ReSE2 : ReSE2;
    }

    public HashSet<Short> getSameCityStations(int mainStationIdx) {
        HashSet<Short> hashSet = new HashSet<>();
        int size = getDataCenter().getZMHZSY2()[6].size();
        for (Short i = (short) 0; i.shortValue() < size; i = Short.valueOf((short) (i.shortValue() + 1))) {
            if (mainStationIdx == ((Short) getDataCenter().getZMHZSY2()[6].get(i.shortValue())).shortValue()) {
                hashSet.add(i);
            }
        }
        return hashSet;
    }

    public Short getMainStation(int stationIdx) {
        return (Short) getDataCenter().getZMHZSY2()[6].get(stationIdx);
    }

    public HashSet<Short> getCHstationIdxs_zmhzsy1x111111111111(String CHstation) {
        HashSet<Short> result = null;
        int zw = getZMWZByName(CHstation);
        if (zw >= 0) {
            result = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                String newCHstation = getDataCenter().getZMHZSY1().get(zw);
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
        int[] ReSE = {((Integer) getDataCenter().getZMHZSY2()[0].get(pos)).intValue(), ((Integer) getDataCenter().getZMHZSY2()[1].get(pos)).intValue(), ((Integer) getDataCenter().getZMHZSY2()[2].get(pos)).intValue(), ((Integer) getDataCenter().getZMHZSY2()[3].get(pos)).intValue(), ((Integer) getDataCenter().getZMHZSY2()[4].get(pos)).intValue(), ((Integer) getDataCenter().getZMHZSY2()[5].get(pos)).intValue(), ((Short) getDataCenter().getZMHZSY2()[6].get(pos)).shortValue()};
        return ReSE;
    }

    public Hashtable<Short, String> getPassTrain1(int from, int to) {
        Hashtable<Short, String> Res = new Hashtable<>((to - from) + 1);
        for (int i = from; i <= to; i++) {
            short ccwz = ByteBuffer.wrap(getDataCenter().getCCTK().get(i)).getShort();
            Object RenameTrain = Res.get(Short.valueOf(ccwz));
            if (RenameTrain == null) {
                Res.put(Short.valueOf(ccwz), Integer.toString(i));
            } else {
                Short valueOf = Short.valueOf(ccwz);
                Res.put(valueOf, i + "," + RenameTrain);
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
            int startfileIdx = from / 1000;
            int skipval = (from % 1000) * 22;
            InputStream in = getDataCenter().getIOFactory().getInpustStream(fileName2 + startfileIdx);
            BufferedInputStream bis = new BufferedInputStream(in);
            DataInputStream dis = new DataInputStream(bis);
            if (skipval > 0) {
                try {
                    dis.skip(skipval);
                } catch (Exception e) {
                    LOGGER.error("readUTF Error:", e);
                    return hashMap;
                } catch (Throwable th) {
                    throw th;
                }
            }
            byte[] bts = new byte[18];
            int i = 0;
            while (i < loops2) {
                int dzwz = dis.readShort();
                int ccwz = dis.readShort();
                dis.read(bts);
                StringBuilder sb = new StringBuilder();
                char c2 = c;
                try {
                    sb.append(dzwz);
                    sb.append("-");
                    sb.append(ccwz);
                    String tmp = sb.toString();
                    if (hashMap.containsKey(tmp)) {
                        loops = loops2;
                        LOGGER.error("重复数据如何处理？？？？？？？");
                    } else {
                        loops = loops2;
                        hashMap.put(tmp, (byte[]) bts.clone());
                    }
                    curCoursor++;
                    if (curCoursor % 1000 == 0 && curCoursor > 0) {
                        dis.close();
                        bis.close();
                        startfileIdx++;
                        in = getDataCenter().getIOFactory().getInpustStream(fileName2 + startfileIdx);
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
                } catch (Throwable th3) {
                    //th = th2;
                    LOGGER.error("", th3);
                    throw th3;
                }
            }
            dis.close();
            bis.close();
            in.close();
        } catch (Throwable th4) {
            LOGGER.error("", th4);
        }
        return hashMap;
    }

    public SimpleTrainInfo getTraininfo(int Pos) {
        ByteBuffer btsBuffer = ByteBuffer.wrap(getDataCenter().getCCData().get(Pos));
        SimpleTrainInfo trainInfo = new SimpleTrainInfo();
        trainInfo.TrainName = getDataCenter().getCC().get(Pos);
        trainInfo.TrainDJ = btsBuffer.get();
        trainInfo.TrainSF = btsBuffer.get();
        trainInfo.TrainInCCTKSYs = btsBuffer.getInt();
        trainInfo.TrainInCCTKSYe = btsBuffer.getInt();
        trainInfo.TrainSFKT = btsBuffer.get() + (-48) == 1;
        trainInfo.TrainXW = String.format("%04d", Short.valueOf(btsBuffer.getShort()));
        trainInfo.TrainBJ = btsBuffer.get() - 48;
        trainInfo.TrainKXZQ = btsBuffer.get();
        trainInfo.TrainKXGL = btsBuffer.getInt();
        trainInfo.TrainKSRQ = btsBuffer.getInt();
        trainInfo.TrainJSRQ = btsBuffer.getInt();
        trainInfo.noticeStart = btsBuffer.getInt();
        trainInfo.noticeEnd = btsBuffer.getInt();
        trainInfo.ddj = ((char) btsBuffer.get()) + "" + ((char) btsBuffer.get()) + "" + ((char) btsBuffer.get());
        trainInfo.hcStart = btsBuffer.getInt();
        trainInfo.hcEnd = btsBuffer.getInt();
        trainInfo.pjdmStart = btsBuffer.getInt();
        trainInfo.pjdmEnd = btsBuffer.getInt();
        trainInfo.caceStart = btsBuffer.getInt();
        trainInfo.caceEnd = btsBuffer.getInt();
        trainInfo.ID = Pos;
        return trainInfo;
    }

    public String toTime(short sTime) {
        Object valueOf;
        Object valueOf2;
        int h = sTime / 100;
        int m = sTime % 100;
        StringBuilder sb = new StringBuilder();
        if (h < 10) {
            valueOf = "0" + h;
        } else {
            valueOf = Integer.valueOf(h);
        }
        sb.append(valueOf);
        sb.append(":");
        if (m < 10) {
            valueOf2 = "0" + m;
        } else {
            valueOf2 = Integer.valueOf(m);
        }
        sb.append(valueOf2);
        return sb.toString();
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
        if (!sfkt || dj.equals("动车组") || dj.equals("城际高速") || dj.equals("高速动车") || dj.equals("市郊列车")) {
            return dj;
        }
        return "新空" + dj;
    }

    public TrainInfo getxInfo(int CCID, String fullTrainName, String RealTrainName, PJInfo pj, short[] Bds, String[] Rds, CCTKBlock Sclp, CCTKBlock Eclp, String dj, int Lc, String[] sfzd, int kxzq, int kxgl, int ksrq, int jsrq, String RcdId, int noticeStart, int noticeEnd, String ddj, int hcStart, int hcEnd, int trainBJ, int caceStart, int caceEnd, SimpleTrainInfo simpleTrainInfo) {
        TrainInfo trainInfo = new TrainInfo();
        trainInfo.fullTrainNo = fullTrainName;
        trainInfo.CC = RealTrainName.trim();
        trainInfo.DJ = dj.trim();
        trainInfo.DD = toTime(Sclp.getDd());
        trainInfo.KD = toTime(Sclp.getKd());
        trainInfo.DD1 = toTime(Eclp.getDd());
        trainInfo.LC = Lc;
        trainInfo.FZ = Rds[0].trim();
        trainInfo.DZ = Rds[1].trim();
        trainInfo.SFZ = sfzd[0].trim();
        trainInfo.ZDZ = sfzd[1].trim();
        trainInfo.LS = Common.covert2StringTime(Common.getMinites(Eclp.getDd(), Eclp.getKd(), Sclp.getKd(), Eclp.getDay() - Sclp.getDay()));
        trainInfo.PJ = pj;
        trainInfo.kxzq = kxzq;
        trainInfo.kxgl = kxgl;
        trainInfo.ksrq = ksrq;
        trainInfo.yxts = Sclp.getDay() - 48;
        trainInfo.jsrq = jsrq;
        trainInfo.ID = RcdId;
        trainInfo.CCID = CCID;
        trainInfo.fzCCTKBlock = Sclp;
        trainInfo.dzCCTKBlock = Eclp;
        trainInfo.noticeStart = noticeStart;
        trainInfo.noticeEnd = noticeEnd;
        trainInfo.DDJ = String.valueOf(ddj);
        trainInfo.hcStart = hcStart;
        trainInfo.hcEnd = hcEnd;
        trainInfo.trainBJ = trainBJ;
        trainInfo.caceStart = caceStart;
        trainInfo.caceEnd = caceEnd;
        NoticeBlock noticeBlock = new NoticeBlock();
        noticeBlock.setKsrq(trainInfo.ksrq);
        noticeBlock.setJsrq(trainInfo.jsrq);
        noticeBlock.setKxgl(trainInfo.kxgl);
        noticeBlock.setKxzq((byte) trainInfo.kxzq);
        noticeBlock.setDay((byte) trainInfo.yxts);
        trainInfo.noticeBlock = noticeBlock;
        trainInfo.setBasicYunXingInfo(new YunXingInfo(simpleTrainInfo.TrainKXZQ, simpleTrainInfo.TrainKXGL, simpleTrainInfo.TrainKSRQ, simpleTrainInfo.TrainJSRQ));
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
            CCTKBlock cctkFz = new CCTKBlock(getDataCenter().getCCTK().get(i5));
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
                short lc_fz2 = cctkFz.getLc();
                int j3 = 0;
                PJInfo mPJInfo3 = mPJInfo2;
                while (j3 < RenamesCount2) {
                    int idx_dz = Integer.parseInt(TrainRenames2[j3]);
                    short lc_dz2 = ByteBuffer.wrap(getDataCenter().getCCTK().get(idx_dz)).getShort(i4);
                    if (lc_dz2 > lc_fz2) {
                        SimpleTrainInfo tInfo = coreZZCX.getTraininfo(ccwz2);
                        int j4 = j3;
                        int RenamesCount3 = RenamesCount2;
                        CCTKBlock cctkDz = new CCTKBlock(getDataCenter().getCCTK().get(idx_dz));
                        String[] SfZd = coreZZCX.getTrainSEinfo(tInfo.TrainInCCTKSYs, tInfo.TrainInCCTKSYe);
                        String dj = coreZZCX.lcdj(tInfo.TrainDJ, tInfo.TrainSFKT);
                        int Lc2 = lc_dz2 - lc_fz2;
                        if (tInfo.TrainBJ == 1) {
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
                            mPJInfo = CountSJPJ(tInfo.TrainDJ, tInfo.TrainSF, tInfo.TrainSFKT, JcJb, Lc, tInfo.TrainXW);
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
                            if (tInfo.pjdmStart != 99999) {
                                Rds2 = Rds3;
                                mPJInfo = getPrice(tInfo.TrainName, cctkFz.getZmwz(), cctkDz.getZmwz(), tInfo.pjdmStart, tInfo.pjdmEnd, getRq(), cctkFz.getDay() - 48);
                            } else {
                                Rds2 = Rds3;
                                mPJInfo = coreZZCX.getPrice(cctkFz.getZmwz(), cctkDz.getZmwz(), lc_dz);
                            }
                        }
                        Bds3[0] = cctkFz.getZmwz();
                        Bds3[1] = cctkDz.getZmwz();
                        for (int s = 0; s < 2; s++) {
                            Rds2[s] = getDataCenter().getZMHZSY1().get(Bds3[s]).trim();
                        }
                        int s2 = cctkFz.getCzcc();
                        int posinfull = s2 - 48;
                        int sStartTo4 = tInfo.ID;
                        String str = tInfo.TrainName;
                        String str2 = tInfo.TrainName;
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
                        cxResult2.add(getxInfo(sStartTo4, str, str3, mPJInfo, Bds2, Rds2, cctkFz, cctkDz, dj, Lc, SfZd, tInfo.TrainKXZQ, tInfo.TrainKXGL, tInfo.TrainKSRQ, tInfo.TrainJSRQ, i3 + "-" + j2, tInfo.noticeStart, tInfo.noticeEnd, tInfo.ddj, tInfo.hcStart, tInfo.hcEnd, tInfo.TrainBJ, tInfo.caceStart, tInfo.caceEnd, tInfo));
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
        int CCTKpos = ((Integer) getDataCenter().getCCTKSY()[0].get(pos)).intValue();
        byte[] bts = getDataCenter().getCCTK().get(CCTKpos);
        short idx = ByteBuffer.wrap(bts).getShort(8);
        return getZM(idx);
    }

    public Short GetZMbyCC_zmhzsyPos(int pos) {
        int CCTKpos = ((Integer) getDataCenter().getCCTKSY()[0].get(pos)).intValue();
        byte[] bts = getDataCenter().getCCTK().get(CCTKpos);
        short idx = ByteBuffer.wrap(bts).getShort(8);
        return Short.valueOf(idx);
    }

    public String getZM(int pos) {
        String zm = getDataCenter().getZMHZSY1().get(pos);
        return zm;
    }

    public String[] getTrainSEinfo(int StarPos, int EndPos) {
        String StarName = GetZMbyCC(StarPos);
        String EndName = GetZMbyCC(EndPos);
        String[] Rtn = {StarName, EndName};
        return Rtn;
    }

    public void resetNessaryVariable() {
        this.bxf = 0.0d;
        setErrCode(0);
        this.kSetCHstations = null;
        this.pjKeysets = null;
        this.priceKeysets = null;
    }

    public ArrayList<TrainInfo> query(String fz, String dz, int rq) {
        resetNessaryVariable();
        this.tmpDZ = dz;
        setRq(rq);
        int[] ST = getstation(fz, 0);
        int[] ET = getstation(dz, 0);
        String ErrStation = "";
        int RtnVal = 0;
        if (ST[1] == 0) {
            ErrStation = "起始站";
            RtnVal = 1;
        }
        if (ET[1] == 0) {
            ErrStation = "终点站";
            RtnVal = 2;
        }
        if (ST[1] == 0 && ET[1] == 0) {
            ErrStation = "起始站和终点站";
            RtnVal = 3;
        }
        if (ErrStation.length() > 0) {
            setErrCode(RtnVal);
            return null;
        }
        return GetPassTrainInfo(ST, ET);
    }

    public List<TransferExCollection> searchByTransfer(String fz, String dz) {
        HashSet<Short> stationsEnd;
        int[] sStar;
        int[] sEnd;
        int[] sStar2 = getstation(fz, 0);
        int[] sEnd2 = getstation(dz, 0);
        HashSet<Short> stationsStar = getAllStationPass_cctkPos(sStar2[0], sStar2[1], getSameCityStations(sStar2[6]), true);
        HashSet<Short> stationsEnd2 = getAllStationPass_cctkPos(sEnd2[0], sEnd2[1], getSameCityStations(sEnd2[6]), false);
        List<StationEx> li1 = new ArrayList<>();
        List<StationEx> li2 = new ArrayList<>();
        Iterator<Short> it = stationsStar.iterator();
        while (it.hasNext()) {
            short idx = it.next().shortValue();
            li1.add(new StationEx(Short.valueOf(idx), getMainStation(idx)));
        }
        Iterator<Short> it2 = stationsEnd2.iterator();
        while (it2.hasNext()) {
            short idx2 = it2.next().shortValue();
            li2.add(new StationEx(Short.valueOf(idx2), getMainStation(idx2)));
        }
        new ArrayList();
        HashMap<Short, ArrayList<TransferEx>> hashMap = new HashMap<>();
        List<TransferExCollection> result = new ArrayList<>();
        for (StationEx s1 : li1) {
            StationEx station = null;
            int i = 0;
            while (true) {
                if (i >= li2.size()) {
                    stationsEnd = stationsEnd2;
                    break;
                }
                stationsEnd = stationsEnd2;
                if (!s1.getMainIdx().equals(li2.get(i).getMainIdx())) {
                    i++;
                    stationsEnd2 = stationsEnd;
                } else {
                    StationEx station2 = li2.get(i);
                    station = station2;
                    break;
                }
            }
            if (station == null) {
                sStar = sStar2;
                sEnd = sEnd2;
            } else {
                short mainIdx = station.getMainIdx().shortValue();
                ArrayList<TransferEx> transferExes = hashMap.get(Short.valueOf(mainIdx));
                if (transferExes == null) {
                    transferExes = new ArrayList<>();
                }
                sStar = sStar2;
                sEnd = sEnd2;
                transferExes.add(new TransferEx(s1.getIdx().shortValue(), station.getIdx().shortValue(), s1.getMainIdx().shortValue()));
                hashMap.put(Short.valueOf(mainIdx), transferExes);
                li2.remove(i);
            }
            stationsEnd2 = stationsEnd;
            sStar2 = sStar;
            sEnd2 = sEnd;
        }
        for (Short sh : hashMap.keySet()) {
            short key = sh.shortValue();
            result.add(new TransferExCollection(new TransferEx(key, key, key), hashMap.get(Short.valueOf(key))));
        }
        return result;
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
            short ccwz = ByteBuffer.wrap(getDataCenter().getCCTK().get(i)).getShort();
            Object iobj = Re2.get(Short.valueOf(ccwz));
            if (iobj != null) {
                String[] TrainRenames = iobj.toString().split(",");
                int RenamesCount = TrainRenames.length;
                short lc_fz = ByteBuffer.wrap(getDataCenter().getCCTK().get(i)).getShort(2);
                int j = 0;
                while (j < RenamesCount) {
                    String[] TrainRenames2 = TrainRenames;
                    int idx_dz = Integer.parseInt(TrainRenames[j]);
                    short lc_dz = ByteBuffer.wrap(getDataCenter().getCCTK().get(idx_dz)).getShort(2);
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
        for (int i = cctkStart; i <= cctkEnd; i++) {
            short ccwz = ByteBuffer.wrap(getDataCenter().getCCTK().get(i)).getShort();
            boolean beginToadd = false;
            SimpleTrainInfo trainInfo = getTraininfo(ccwz);
            for (int j = trainInfo.TrainInCCTKSYs; j <= trainInfo.TrainInCCTKSYe; j++) {
                short zmIdx = GetZMbyCC_zmhzsyPos(j).shortValue();
                if (afterStations) {
                    if (beginToadd) {
                        getZM(zmIdx);
                        hSet.add(Short.valueOf(zmIdx));
                    }
                    if (stationIdxs.contains(Short.valueOf(zmIdx))) {
                        beginToadd = true;
                    }
                } else {
                    if (!beginToadd) {
                        hSet.add(Short.valueOf(zmIdx));
                    }
                    if (stationIdxs.contains(Short.valueOf(zmIdx))) {
                        beginToadd = true;
                    }
                }
            }
        }
        return hSet;
    }

    public double getNewBxf(double bxfBefore, int Jflc, boolean sfkt, int lcdj) {
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
        byte[] pjBts = priceSets.get(String.valueOf(dzZMWZ) + "-" + String.valueOf(ccwz));
        return getPJInfoFromBytes(pjBts);
    }

    public PJInfo getPrice(String train, int fzZMWZ, int dzZMWZ, int pjdmStart, int pjdmEnd, int rq, int yxts) {
        int i = pjdmEnd;
        PJInfo pjInfo = new PJInfo();
        HashMap<String, byte[]> priceSets = getPriceKeysets().get(Integer.valueOf(fzZMWZ));
        if (priceSets == null) {
            priceSets = getPriceByZMWZ(fzZMWZ);
            getPriceKeysets().put(String.valueOf(fzZMWZ), priceSets);
        }
        char c = 5;
        if (pjdmStart != i && rq > 0) {
            int i2 = pjdmStart;
            while (i2 <= i) {
                int ksrq = ((Integer) getDataCenter().getPJDM()[0].get(i2)).intValue();
                int jsrq = ((Integer) getDataCenter().getPJDM()[1].get(i2)).intValue();
                int xw = ((Byte) getDataCenter().getPJDM()[2].get(i2)).byteValue();
                int kxgl = ((Integer) getDataCenter().getPJDM()[3].get(i2)).intValue();
                int kxzq = ((Byte) getDataCenter().getPJDM()[4].get(i2)).byteValue();
                int pjcode = ((Integer) getDataCenter().getPJDM()[c].get(i2)).intValue();
                int i3 = i2;
                int todayState = ZZCXCenter.trainStateToday(rq, ksrq, jsrq, kxzq, kxgl, yxts, false, null);
                if (todayState == 1) {
                    byte[] pjBts = priceSets.get(String.valueOf(dzZMWZ) + "-" + String.valueOf(pjcode));
                    PJInfo pjInfoTemp = getPJInfoFromBytes(pjBts);
                    if (xw == 51) {
                        pjInfo.YWs = pjInfoTemp.YWs;
                        pjInfo.YWz = pjInfoTemp.YWz;
                        pjInfo.YWx = pjInfoTemp.YWx;
                    } else if (xw == 52 || xw == 65 || xw == 70) {
                        pjInfo.RWs = pjInfoTemp.RWs;
                        pjInfo.RWx = pjInfoTemp.RWx;
                    } else if (xw == 77) {
                        pjInfo.YD = pjInfoTemp.YD;
                    } else if (xw == 79) {
                        pjInfo.ED = pjInfoTemp.ED;
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
        int pjCode = ((Integer) getDataCenter().getPJDM()[5].get(pjdmStart)).intValue();
        byte[] pjBts2 = priceSets.get(dzZMWZ + "-" + pjCode);
        PJInfo pjInfo2 = getPJInfoFromBytes(pjBts2);
        return pjInfo2;
    }

    public PJInfo getPrice(String train, int fzZMWZ, int dzZMWZ, int pjdmStart, int pjdmEnd, String ddddd) {
        return getPrice(train, fzZMWZ, dzZMWZ, pjdmStart, pjdmEnd, 0, 0);
    }

    public HashMap<String, byte[]> getPriceByZMWZ(int zmwz) {
        int pjdmStart = ((Integer) getDataCenter().getZMHZSY2()[4].get(zmwz)).intValue();
        int pjdmEnd = ((Integer) getDataCenter().getZMHZSY2()[5].get(zmwz)).intValue();
        return loadPjsets(pjdmStart, pjdmEnd, true);
    }

    public HashMap<String, byte[]> getPJByZMWZ(int zmwz) {
        int zjpjStart = ((Integer) getDataCenter().getZMHZSY2()[2].get(zmwz)).intValue();
        int zjpjEnd = ((Integer) getDataCenter().getZMHZSY2()[3].get(zmwz)).intValue();
        return loadPjsets(zjpjStart, zjpjEnd, false);
    }

    public PJInfo getPJInfoFromBytes(byte[] bts) {
        PJInfo pjInfo = new PJInfo();
        if (bts != null) {
            ByteBuffer bb = ByteBuffer.wrap(bts);
            Double tmppj = Double.valueOf(bb.getShort() / 10.0d);
            String str = "—";
            pjInfo.YZ = tmppj.doubleValue() == 0.0d ? str : getNumberFormat().format(tmppj);
            Double tmppj2 = Double.valueOf(bb.getShort() / 10.0d);
            pjInfo.RZ = tmppj2.doubleValue() == 0.0d ? str : getNumberFormat().format(tmppj2);
            Double tmppj3 = Double.valueOf(bb.getShort() / 10.0d);
            pjInfo.YWs = tmppj3.doubleValue() == 0.0d ? str : getNumberFormat().format(tmppj3);
            Double tmppj4 = Double.valueOf(bb.getShort() / 10.0d);
            pjInfo.YWz = tmppj4.doubleValue() == 0.0d ? str : getNumberFormat().format(tmppj4);
            Double tmppj5 = Double.valueOf(bb.getShort() / 10.0d);
            pjInfo.YWx = tmppj5.doubleValue() == 0.0d ? str : getNumberFormat().format(tmppj5);
            Double tmppj6 = Double.valueOf(bb.getShort() / 10.0d);
            pjInfo.RWs = tmppj6.doubleValue() == 0.0d ? str : getNumberFormat().format(tmppj6);
            Double tmppj7 = Double.valueOf(bb.getShort() / 10.0d);
            pjInfo.RWx = tmppj7.doubleValue() == 0.0d ? str : getNumberFormat().format(tmppj7);
            Double tmppj8 = Double.valueOf(bb.getShort() / 10.0d);
            pjInfo.ED = tmppj8.doubleValue() == 0.0d ? str : getNumberFormat().format(tmppj8);
            Double tmppj9 = Double.valueOf(bb.getShort() / 10.0d);
            if (tmppj9.doubleValue() != 0.0d) {
                str = getNumberFormat().format(tmppj9);
            }
            pjInfo.YD = str;
        } else {
            //LogPrinter.v(LogPrinter.TAG, "price 票价为空");
            LOGGER.warn("price 票价为空");
        }
        return pjInfo;
    }

    public int getErrCode() {
        return this.ErrCode;
    }

    public void setErrCode(int errCode) {
        this.ErrCode = errCode;
    }

    private DataCenter getDataCenter() {
        return dataCenter;
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