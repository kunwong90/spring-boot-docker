package com.example.demo.service.impl;

import com.example.demo.entity.TrainBasicInfo;
import com.example.demo.mapper.TrainBasicInfoMapper;
import com.example.demo.service.ITrainBasicInfoService;
import com.example.demo.trainprice.utils.h12306.core.CoreZZCX;
import com.example.demo.trainprice.utils.h12306.pojo.PJInfo;
import com.example.demo.trainprice.utils.h12306.pojo.TrainInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class TrainBasicInfoServiceImpl implements ITrainBasicInfoService {

    @Resource
    private TrainBasicInfoMapper trainBasicInfoMapper;

    private CoreZZCX coreZZCX = new CoreZZCX();


    @Override
    public TrainBasicInfo selectOne(TrainBasicInfo trainBasicInfo) {
        LocalDate localDate = LocalDate.now();

        String date = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        TrainBasicInfo trainBasicInfo1 = trainBasicInfoMapper.selectOne(trainBasicInfo);
        if (trainBasicInfo1 == null) {
            long start = System.currentTimeMillis();
            List<TrainInfo> trainInfoList = coreZZCX.query(trainBasicInfo.getDepartStationName(), trainBasicInfo.getDestStationName(), Integer.parseInt(date));
            System.out.println("查询耗时 = " + (System.currentTimeMillis() - start));
            for (TrainInfo trainInfo : trainInfoList) {
                if (StringUtils.equals(trainBasicInfo.getDepartStationName(), trainInfo.FZ) && StringUtils.equals(trainBasicInfo.getDestStationName(), trainInfo.DZ)
                        && StringUtils.equals(trainBasicInfo.getTrainNo(), trainInfo.CC)) {
                    PJInfo pjInfo = trainInfo.PJ;
                    trainBasicInfo1 = new TrainBasicInfo();
                    trainBasicInfo1.setTrainNo(trainBasicInfo.getTrainNo());
                    trainBasicInfo1.setStartStationName(trainInfo.SFZ);
                    trainBasicInfo1.setEndStationName(trainInfo.ZDZ);
                    trainBasicInfo1.setDepartStationName(trainInfo.FZ);
                    trainBasicInfo1.setDestStationName(trainInfo.DZ);
                    trainBasicInfo1.setDistance(trainInfo.LC);
                    trainBasicInfo1.setRws(new BigDecimal(pjInfo.getRws()));
                    trainBasicInfo1.setRwx(new BigDecimal(pjInfo.getRwx()));
                    trainBasicInfo1.setYws(new BigDecimal(pjInfo.getYws()));
                    trainBasicInfo1.setYwz(new BigDecimal(pjInfo.getYwz()));
                    trainBasicInfo1.setYwx(new BigDecimal(pjInfo.getYwx()));
                    trainBasicInfo1.setAddTime(new Date());
                    trainBasicInfo1.setUpdateTime(new Date());
                    trainBasicInfoMapper.insert(trainBasicInfo1);
                }
            }

        }
        return trainBasicInfo1;
    }
}
