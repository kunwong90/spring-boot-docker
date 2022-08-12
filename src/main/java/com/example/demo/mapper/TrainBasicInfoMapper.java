package com.example.demo.mapper;

import com.example.demo.entity.TrainBasicInfo;

import java.util.List;

public interface TrainBasicInfoMapper {

    TrainBasicInfo selectOne(TrainBasicInfo trainBasicInfo);

    int insert(TrainBasicInfo trainBasicInfo);

    int batchInsert(List<TrainBasicInfo> trainBasicInfoList);
}
