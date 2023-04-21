package com.example.demo.trainprice.utils.h12306.pojo;

import java.util.ArrayList;

public class TransferExCollection {

    ArrayList<TransferEx> list;
    TransferEx mainStation;

    public TransferExCollection() {
    }

    public TransferExCollection(TransferEx mainStation, ArrayList<TransferEx> list) {
        this.mainStation = mainStation;
        this.list = list;
    }

    public ArrayList<TransferEx> getList() {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        return this.list;
    }

    public void setList(ArrayList<TransferEx> list) {
        this.list = list;
    }

    public TransferEx getMainStation() {
        return this.mainStation;
    }

    public void setMainStation(TransferEx mainStation) {
        this.mainStation = mainStation;
    }

}
