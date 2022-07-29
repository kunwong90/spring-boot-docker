package com.example.demo.controller;

import com.example.demo.entity.TrainPriceQueryVo;
import com.example.demo.trainprice.utils.h12306.core.CoreZZCX;
import com.example.demo.trainprice.utils.h12306.pojo.TrainInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TrainPriceController {

    @RequestMapping(method = RequestMethod.POST, value = "/queryprice")
    public List<TrainInfo> query(@RequestBody TrainPriceQueryVo trainPriceQueryVo) {
        CoreZZCX coreZZCX = new CoreZZCX();
        List<TrainInfo> trainInfoList = coreZZCX.query(trainPriceQueryVo.getDepartureStationName(), trainPriceQueryVo.getDestStationName(), trainPriceQueryVo.getDepartureDate());
        return trainInfoList;
    }
}
