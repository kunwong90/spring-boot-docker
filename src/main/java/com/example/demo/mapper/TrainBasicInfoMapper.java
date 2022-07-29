package com.example.demo.mapper;

import com.example.demo.entity.TrainBasicInfo;

public interface TrainBasicInfoMapper {

    TrainBasicInfo selectOne(TrainBasicInfo trainBasicInfo);

    int insert(TrainBasicInfo trainBasicInfo);
}
