package com.example.demo.trainprice.utils.h12306.core;


import com.example.demo.trainprice.utils.h12306.pojo.TrainInfo;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        CoreZZCX coreZZCX = new CoreZZCX();
        List<TrainInfo> trainInfos = coreZZCX.query("北京西", "三亚", 20230804);

    }
}
