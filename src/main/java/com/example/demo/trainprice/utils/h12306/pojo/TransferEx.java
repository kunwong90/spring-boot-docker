package com.example.demo.trainprice.utils.h12306.pojo;

import com.alibaba.fastjson.JSONObject;

public class TransferEx {

    private short dzFzIdx;
    private short fzDzIdx;
    private short mainStationIdx;

    public TransferEx() {
    }

    public TransferEx(short fzDzIdx, short dzFzIdx, short mainStationIdx) {
        this.fzDzIdx = fzDzIdx;
        this.dzFzIdx = dzFzIdx;
        this.mainStationIdx = mainStationIdx;
    }

    public TransferEx(JSONObject json) {
        setFzDzIdx(Short.parseShort(json.getString("fzDzIdx")));
        setDzFzIdx(Short.parseShort(json.getString("dzFzIdx")));
        setMainStationIdx(Short.parseShort(json.getString("mainStationIdx")));
    }

    public short getFzDzIdx() {
        return this.fzDzIdx;
    }

    public void setFzDzIdx(short fzDzIdx) {
        this.fzDzIdx = fzDzIdx;
    }

    public short getDzFzIdx() {
        return this.dzFzIdx;
    }

    public void setDzFzIdx(short dzFzIdx) {
        this.dzFzIdx = dzFzIdx;
    }

    public short getMainStationIdx() {
        return this.mainStationIdx;
    }

    public void setMainStationIdx(short mainStationIdx) {
        this.mainStationIdx = mainStationIdx;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("fzDzIdx", (int) getFzDzIdx());
            json.put("dzFzIdx", (int) getDzFzIdx());
            json.put("mainStationIdx", (int) getMainStationIdx());
        } catch (Exception e) {
        }
        return json;
    }

    @Override
    public String toString() {
        return "TransferEx{fzDzIdx=" + ((int) this.fzDzIdx) + ", dzFzIdx=" + ((int) this.dzFzIdx) + ", mainStationIdx=" + ((int) this.mainStationIdx) + '}';
    }

}
