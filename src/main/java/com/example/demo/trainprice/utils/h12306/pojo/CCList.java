package com.example.demo.trainprice.utils.h12306.pojo;

import java.util.ArrayList;

public class CCList {
    private ArrayList<Integer> list;

    public ArrayList<Integer> getList() {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        return this.list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }
}