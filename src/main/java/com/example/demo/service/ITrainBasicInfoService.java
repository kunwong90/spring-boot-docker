package com.example.demo.service;

import com.example.demo.entity.TrainBasicInfo;

public interface ITrainBasicInfoService {

    TrainBasicInfo selectOne(TrainBasicInfo trainBasicInfo);

    void queryAllAndSave();
}
