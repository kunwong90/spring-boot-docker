package com.example.demo.trainprice.utils.h12306.pojo;

import java.util.ArrayList;
import java.util.List;

public class CCList {
    private List<Integer> list;

    public CCList() {
        this.list = new ArrayList<>();
    }

    public List<Integer> getList() {
        return this.list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }
}