package com.example.demo.controller;

import com.example.demo.entity.TrainBasicInfo;
import com.example.demo.entity.TrainPriceQueryVo;
import com.example.demo.service.ITrainBasicInfoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class TrainPriceController {

    @Resource
    private ITrainBasicInfoService trainBasicInfoService;

    @RequestMapping(method = RequestMethod.POST, value = "/queryprice")
    public TrainBasicInfo query(@RequestBody TrainPriceQueryVo trainPriceQueryVo) {
        TrainBasicInfo trainBasicInfo = new TrainBasicInfo();
        trainBasicInfo.setDepartStationName(trainPriceQueryVo.getDepartStationName());
        trainBasicInfo.setDestStationName(trainPriceQueryVo.getDestStationName());
        trainBasicInfo.setTrainNo(trainPriceQueryVo.getTrainNo());
        return trainBasicInfoService.selectOne(trainBasicInfo);
    }
}
