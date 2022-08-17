package com.example.demo.trainprice.utils.h12306.datacenter;


import com.example.demo.trainprice.utils.h12306.pojo.JiaoLu;
import com.example.demo.trainprice.utils.h12306.utils.Common;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataCenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataCenter.class);
    private int[] pyEnd;
    private int[] pyStart;
    private boolean assetsMode;
    private ArrayList<String> autocomplete_hz;
    private ArrayList<String> autocomplete_zm;
    private List<byte[]> bts_cctk;

    private List<byte[]> dataLoader_cace1;
    private List<String> dataLoader_cace2;
    private List<String> dataLoader_cc;
    private List<byte[]> dataLoader_ccData;
    private ArrayList[] dataLoader_ccdz;
    private ArrayList[] dataLoader_ccdzzm;
    private ArrayList[] dataLoader_ccsy;
    private ArrayList[] dataLoader_cctksy;
    private ArrayList[] dataLoader_pjdm;
    private ArrayList[] dataLoader_zm;
    private List<String> dataLoader_zmhzsy1;
    private ArrayList[] dataLoader_zmhzsy2;
    private ArrayList[] dataLoader_zmsy;
    private HashMap<String, String> ddjs;
    private String defaultDataPath;
    private ArrayList<JiaoLu> jiaolu;
    private IOFactory iOFactory = new IOFactory();
    private HashMap<String, String> stationLocations;

    public DataCenter() {
        this.defaultDataPath = "";
    }

    public DataCenter(String defaultDataPath, boolean isAssetsMode) {

        setDefaultDataPath(defaultDataPath);
        setAssetsMode(isAssetsMode);
        reset();
    }

    /**
     * 中文车站名
     *
     * @return
     */
    public List<String> getZMHZSY1() {
        if (this.dataLoader_zmhzsy1 == null) {
            try {
                this.dataLoader_zmhzsy1 = iOFactory.dataloader_line("zmhzsy1.txt", 3500);
            } catch (Exception e) {
            }
        }
        return this.dataLoader_zmhzsy1;
    }

    public ArrayList[] getZM() {
        if (this.dataLoader_zm == null) {
            this.dataLoader_zm = iOFactory.dataloader_utf("zm.txt", new int[]{5, 4}, new int[]{0, 1}, 5000);
        }
        return this.dataLoader_zm;
    }

    public ArrayList<String> getAutoCompleteHZ() {
        if (this.autocomplete_hz == null && getZMHZSY1() != null) {
            int sizes = getZMHZSY1().size();
            try {
                this.autocomplete_hz = new ArrayList<>();
                for (int i = 0; i < sizes; i++) {
                    int idx = ((Integer) getZM()[1].get(i)).intValue();
                    this.autocomplete_hz.add(getZMHZSY1().get(idx));
                }
            } catch (Exception e) {
            }
        }
        return this.autocomplete_hz;
    }

    public ArrayList<String> getAutoCompleteZM() {
        if (this.autocomplete_zm == null && getZMHZSY1() != null) {
            int sizes = getZMHZSY1().size();
            try {
                this.autocomplete_zm = new ArrayList<>();
                for (int i = 0; i < sizes; i++) {
                    this.autocomplete_zm.add((String) getZM()[0].get(i));
                }
            } catch (Exception e) {
            }
        }
        return this.autocomplete_zm;
    }

    public List<String> getCC() {
        if (this.dataLoader_cc == null) {
            try {
                this.dataLoader_cc = iOFactory.dataloader_line("cc.idx", 4500);
            } catch (Exception e) {
            }
        }
        return this.dataLoader_cc;
    }

    /**
     * 车次数据
     *
     * @return
     */
    public List<byte[]> getCCData() {
        if (this.dataLoader_ccData == null) {
            try {
                this.dataLoader_ccData = iOFactory.dataloader_binary_BlockRead("cc.dat", 62);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.dataLoader_ccData;
    }

    public ArrayList[] getCCDZZM() {
        if (this.dataLoader_ccdzzm == null) {
            this.dataLoader_ccdzzm = iOFactory.dataloader_utf("ccdzzm.txt", new int[]{5, 5}, new int[]{0, 1}, 5000);
        }
        return this.dataLoader_ccdzzm;
    }

    public List<byte[]> getCCTK() {
        if (this.bts_cctk == null) {
            try {
                this.bts_cctk = iOFactory.dataloader_binary_BlockRead("cctk.dat", 12);
            } catch (Exception e) {
            }
        }
        return this.bts_cctk;
    }

    public ArrayList[] getCCDZ() {
        if (this.dataLoader_ccdz == null) {
            this.dataLoader_ccdz = iOFactory.dataloader_binary("ccdz.dat", new int[]{1, 0});
        }
        return this.dataLoader_ccdz;
    }

    public ArrayList[] getCCSY() {
        if (this.dataLoader_ccsy == null) {
            this.dataLoader_ccsy = iOFactory.dataloader_binary("ccsy.dat", new int[]{1, 1, 1, 1});
        }
        return this.dataLoader_ccsy;
    }

    public ArrayList[] getCCTKSY() {
        if (this.dataLoader_cctksy == null) {
            this.dataLoader_cctksy = iOFactory.dataloader_binary("cctksy.dat", new int[]{1});
        }
        return this.dataLoader_cctksy;
    }

    public ArrayList[] getZMHZSY2() {
        if (this.dataLoader_zmhzsy2 == null) {
            this.dataLoader_zmhzsy2 = iOFactory.dataloader_binary("zmhzsy2.dat", new int[]{1, 1, 1, 1, 1, 1});
        }
        return this.dataLoader_zmhzsy2;
    }

    public ArrayList[] getZMSY() {
        if (this.dataLoader_zmsy == null) {
            this.dataLoader_zmsy = iOFactory.dataloader_binary("zmsy.dat", new int[]{0, 0});
        }
        return this.dataLoader_zmsy;
    }

    public ArrayList[] getPJDM() {
        if (this.dataLoader_pjdm == null) {
            this.dataLoader_pjdm = iOFactory.dataloader_binary("pjdm.dat", new int[]{1, 1, 2, 1, 2, 1});
        }
        return this.dataLoader_pjdm;
    }

    public HashMap<String, String> getDDJ() {
        if (this.ddjs == null) {
            try {
                this.ddjs = new HashMap<>();
                JSONArray jsonArray = new JSONArray(Common.readFile(iOFactory.getInpustStream("000_ddj.json"), "UTF-8"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.optJSONObject(i);
                    this.ddjs.put(json.optString("k"), json.optString("v"));
                }
            } catch (Exception e) {
            }
        }
        return this.ddjs;
    }

    public HashMap<String, String> getStationLocation() {
        if (this.stationLocations == null) {
            try {
                this.stationLocations = new HashMap<>();
                JSONArray jsonArray = new JSONArray(Common.readFile(iOFactory.getInpustStream("station_poi.json"), "UTF-8"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.optJSONObject(i);
                    this.stationLocations.put(json.optString("n"), json.optString("l"));
                }
            } catch (Exception e) {
            }
        }
        return this.stationLocations;
    }

    public int getDataVer() {
        int ver = -1;

        try {
            ver = Integer.parseInt(iOFactory.dataloader_line("data.ver", 1).get(0));
        } catch (Exception e) {

        }

        return ver;
    }

    public int[] getPinYinStart() {
        if (this.pyStart == null) {
            this.pyStart = new int[26];
            int aSize = getZMSY()[0].size();
            for (int i = 0; i < aSize; i++) {
                this.pyStart[i] = ((Short) getZMSY()[0].get(i)).shortValue();
            }
        }
        return this.pyStart;
    }

    public int[] getPinYinEnd() {
        if (this.pyEnd == null) {
            this.pyEnd = new int[26];
            int aSize = getZMSY()[0].size();
            for (int i = 0; i < aSize; i++) {
                this.pyEnd[i] = ((Short) getZMSY()[1].get(i)).shortValue();
            }
        }
        return this.pyEnd;
    }

    public String getHCInfo(int ccwz, int zmwz, int rq, int start, int end) throws Exception {
        String hcInfo = null;
        if (start != 999999 && end != 999999) {
            int currentIdx = start;
            byte[] buffer = new byte[14];
            boolean isFirstFile = true;
            int endFileIndex = (end / 1000) + Math.min(end % 1000, 1);
            for (int startFileIndex = (start / 1000) + Math.min(start % 1000, 1); startFileIndex <= endFileIndex; startFileIndex++) {
                InputStream is = iOFactory.getInpustStream("hc1.dat" + (startFileIndex - 1));
                if (isFirstFile) {
                    is.skip((start % 1000) * 14);
                }
                BufferedInputStream bis = new BufferedInputStream(is);
                while (bis.read(buffer) > 0 && currentIdx <= end) {
                    if (isThisStationInfo(ccwz, zmwz, rq, buffer)) {
                        hcInfo = getStationHCInfo(currentIdx);
                    }
                    if (StringUtils.isNotBlank(hcInfo)) {
                        break;
                    }

                    currentIdx++;
                }
                if (StringUtils.isNotBlank(hcInfo)) {
                    break;
                }
                isFirstFile = false;
            }
            return hcInfo;
        }
        return null;
    }

    private String getStationHCInfo(int index) throws Exception {
        int fileIndex = (index / 1000) + Math.min(index % 1000, 1);
        List<String> hcList = iOFactory.dataloader_line("hc2.dat" + (fileIndex - 1), 1000 + 1);
        return hcList.get(index % 1000);
    }

    private boolean isThisStationInfo(int ccwz, int zmwz, int rq, byte[] bts) {
        ByteBuffer buffer = ByteBuffer.wrap(bts);
        int tmpCCWZ = buffer.getInt();
        int tmpZMWZ = buffer.getShort();
        int tmpKSRQ = buffer.getInt();
        int tmpJSRQ = buffer.getInt();
        if (!String.format("%d%d", ccwz, zmwz).equals(String.format("%s%s", tmpCCWZ, tmpZMWZ)) || rq < tmpKSRQ || rq > tmpJSRQ) {
            return false;
        }
        return true;
    }

    public String getCaceInfo(int rq, int start, int end) {
        for (int i = start; i <= end; i++) {
            ByteBuffer buffer = ByteBuffer.wrap(getCace1().get(i));
            int rqStart = buffer.getInt();
            int rqEnd = buffer.getInt();
            if (rq >= rqStart && rq <= rqEnd) {
                String caceInfo = getCace2().get(i);
                return caceInfo;
            }
        }
        return null;
    }

    public String getDefaultDataPath() {
        return this.defaultDataPath;
    }

    public void setDefaultDataPath(String defaultDataPath) {
        this.defaultDataPath = defaultDataPath;
    }


    public boolean isAssetsMode() {
        return this.assetsMode;
    }

    public void setAssetsMode(boolean assetsMode) {
        this.assetsMode = assetsMode;
    }

    public List<String> getCace2() {
        if (this.dataLoader_cace2 == null) {
            try {
                this.dataLoader_cace2 = iOFactory.dataloader_line("cace2.dat", 3500);
            } catch (Exception e) {
            }
        }
        return this.dataLoader_cace2;
    }

    public List<byte[]> getCace1() {
        if (this.dataLoader_cace1 == null) {
            try {
                this.dataLoader_cace1 = iOFactory.dataloader_binary_BlockRead("cace1.dat", 8);
            } catch (Exception e) {
            }
        }
        return this.dataLoader_cace1;
    }

    public List<JiaoLu> getJiaoLu() {
        if (this.jiaolu == null) {
            try {
                this.jiaolu = new ArrayList<>();
                List<String> tmp = iOFactory.dataloader_line("000_jlb.txt", 3500);
                for (int i = 0; i < tmp.size(); i++) {
                    String[] strArray = tmp.get(i).split("\t");
                    if (strArray.length < 7) {
                        LOGGER.error("jlt 数据错误:row->" + (i + 1) + ",data->" + tmp.get(i));

                    } else {
                        this.jiaolu.add(new JiaoLu(strArray));
                    }
                }
            } catch (Exception e) {
                LOGGER.error("jlt 解析错误:", e);
            }
        }
        return this.jiaolu;
    }

    public void reset() {
        this.dataLoader_ccdz = null;
        this.dataLoader_ccdzzm = null;
        this.dataLoader_ccsy = null;
        this.dataLoader_cctksy = null;
        this.dataLoader_zm = null;
        this.dataLoader_zmhzsy2 = null;
        this.dataLoader_zmsy = null;
        this.dataLoader_pjdm = null;
        this.dataLoader_cace1 = null;
        this.dataLoader_cace2 = null;
        this.dataLoader_cc = null;
        this.dataLoader_ccData = null;
        this.dataLoader_zmhzsy1 = null;
        this.bts_cctk = null;
        this.autocomplete_hz = null;
        this.autocomplete_zm = null;
        this.pyStart = null;
        this.pyEnd = null;
        this.jiaolu = null;
        this.ddjs = null;
        this.stationLocations = null;
    }

    public IOFactory getiOFactory() {
        return iOFactory;
    }
}