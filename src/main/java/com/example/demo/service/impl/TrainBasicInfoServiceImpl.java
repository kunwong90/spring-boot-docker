package com.example.demo.service.impl;

import com.example.demo.entity.TrainBasicInfo;
import com.example.demo.mapper.TrainBasicInfoMapper;
import com.example.demo.service.ITrainBasicInfoService;
import com.example.demo.trainprice.utils.h12306.core.CoreZZCX;
import com.example.demo.trainprice.utils.h12306.pojo.PJInfo;
import com.example.demo.trainprice.utils.h12306.pojo.TrainInfo;
import com.example.demo.util.JacksonJsonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TrainBasicInfoServiceImpl implements ITrainBasicInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainBasicInfoServiceImpl.class);

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
            LOGGER.info("查询耗时 = {}", (System.currentTimeMillis() - start));
            for (TrainInfo trainInfo : trainInfoList) {
                if (StringUtils.equals(trainBasicInfo.getDepartStationName(), trainInfo.getFz()) && StringUtils.equals(trainBasicInfo.getDestStationName(), trainInfo.getDz())
                        && StringUtils.equals(trainBasicInfo.getTrainNo(), trainInfo.getCc())) {
                    PJInfo pjInfo = trainInfo.getPj();
                    trainBasicInfo1 = new TrainBasicInfo();
                    trainBasicInfo1.setTrainNo(trainBasicInfo.getTrainNo());
                    trainBasicInfo1.setStartStationName(trainInfo.getSfz());
                    trainBasicInfo1.setEndStationName(trainInfo.getZdz());
                    trainBasicInfo1.setDepartStationName(trainInfo.getFz());
                    trainBasicInfo1.setDestStationName(trainInfo.getDz());
                    trainBasicInfo1.setDistance(trainInfo.getLc());
                    trainBasicInfo1.setYdz(pjInfo.getYdz());
                    trainBasicInfo1.setEdz(pjInfo.getEdz());
                    trainBasicInfo1.setSwz(pjInfo.getSwz());
                    trainBasicInfo1.setTdz(pjInfo.getTdz());
                    trainBasicInfo1.setRz(pjInfo.getRz());
                    trainBasicInfo1.setYz(pjInfo.getYz());
                    // 查询不到
                    trainBasicInfo1.setGjrws(pjInfo.BLANK_PRICE);
                    // 查询不到
                    trainBasicInfo1.setGjrwx(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setDws(pjInfo.getDw());
                    trainBasicInfo1.setDwx(pjInfo.BLANK_PRICE);
                    if (StringUtils.startsWith(trainBasicInfo.getTrainNo(), "D")) {
                        trainBasicInfo1.setYdws(pjInfo.getRws());
                        trainBasicInfo1.setYdwx(pjInfo.getRwx());
                        trainBasicInfo1.setEdws(pjInfo.getYws());
                        trainBasicInfo1.setEdwz(pjInfo.getYwz());
                        trainBasicInfo1.setEdwx(pjInfo.getYwx());

                        trainBasicInfo1.setRws(pjInfo.BLANK_PRICE);
                        trainBasicInfo1.setRwx(pjInfo.BLANK_PRICE);
                        trainBasicInfo1.setYws(pjInfo.BLANK_PRICE);
                        trainBasicInfo1.setYwz(pjInfo.BLANK_PRICE);
                        trainBasicInfo1.setYwx(pjInfo.BLANK_PRICE);
                    } else {
                        trainBasicInfo1.setYdws(pjInfo.BLANK_PRICE);
                        trainBasicInfo1.setYdwx(pjInfo.BLANK_PRICE);
                        trainBasicInfo1.setEdws(pjInfo.BLANK_PRICE);
                        trainBasicInfo1.setEdwz(pjInfo.BLANK_PRICE);
                        trainBasicInfo1.setEdwx(pjInfo.BLANK_PRICE);

                        trainBasicInfo1.setRws(pjInfo.getRws());
                        trainBasicInfo1.setRwx(pjInfo.getRwx());
                        trainBasicInfo1.setYws(pjInfo.getYws());
                        trainBasicInfo1.setYwz(pjInfo.getYwz());
                        trainBasicInfo1.setYwx(pjInfo.getYwx());
                    }
                    trainBasicInfo1.setWz(pjInfo.getWz());
                    trainBasicInfo1.setQt(pjInfo.getQt());
                    trainBasicInfo1.setAddTime(new Date());
                    trainBasicInfo1.setUpdateTime(new Date());
                    trainBasicInfoMapper.insert(trainBasicInfo1);
                }
            }

        }
        return trainBasicInfo1;
    }

    @Override
    public void queryAndSave(TrainBasicInfo trainBasicInfo) {
        long start = System.currentTimeMillis();
        String date = new SimpleDateFormat("yyyyMMdd").format(trainBasicInfo.getDepartureDate());
        List<TrainInfo> trainInfoList = coreZZCX.query(trainBasicInfo.getDepartStationName(), trainBasicInfo.getDestStationName(), Integer.parseInt(date));
        LOGGER.info("查询耗时 = {}", (System.currentTimeMillis() - start));
        if (CollectionUtils.isNotEmpty(trainInfoList)) {
            List<TrainBasicInfo> trainBasicInfoList = new ArrayList<>();
            for (TrainInfo trainInfo : trainInfoList) {
                /*if (StringUtils.startsWithAny(trainInfo.getCc(), "G")) {
                    // 过滤G车次
                    continue;
                }*/
                TrainBasicInfo query = new TrainBasicInfo();
                query.setTrainNo(trainInfo.getCc());
                query.setDepartStationName(trainInfo.getFz());
                query.setDestStationName(trainInfo.getDz());
                try {
                    query.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                } catch (Exception ignore) {

                }
                TrainBasicInfo result = trainBasicInfoMapper.selectOne(query);
                PJInfo pjInfo = trainInfo.getPj();
                TrainBasicInfo trainBasicInfo1 = new TrainBasicInfo();
                trainBasicInfo1.setTrainNo(trainInfo.getCc());
                trainBasicInfo1.setStartStationName(trainInfo.getSfz());
                trainBasicInfo1.setEndStationName(trainInfo.getZdz());
                trainBasicInfo1.setDepartStationName(trainInfo.getFz());
                trainBasicInfo1.setDestStationName(trainInfo.getDz());
                trainBasicInfo1.setDepartureDate(trainBasicInfo.getDepartureDate());
                trainBasicInfo1.setDistance(trainInfo.getLc());
                trainBasicInfo1.setYdz(pjInfo.getYdz());
                trainBasicInfo1.setEdz(pjInfo.getEdz());
                trainBasicInfo1.setSwz(pjInfo.getSwz());
                trainBasicInfo1.setTdz(pjInfo.getTdz());
                trainBasicInfo1.setRz(pjInfo.getRz());
                trainBasicInfo1.setYz(pjInfo.getYz());
                // 查询不到
                trainBasicInfo1.setGjrws(pjInfo.BLANK_PRICE);
                // 查询不到
                trainBasicInfo1.setGjrwx(pjInfo.BLANK_PRICE);
                trainBasicInfo1.setDws(pjInfo.getDw());
                trainBasicInfo1.setDwx(pjInfo.BLANK_PRICE);
                if (StringUtils.startsWith(trainBasicInfo.getTrainNo(), "D")) {
                    trainBasicInfo1.setYdws(pjInfo.getRws());
                    trainBasicInfo1.setYdwx(pjInfo.getRwx());
                    trainBasicInfo1.setEdws(pjInfo.getYws());
                    trainBasicInfo1.setEdwz(pjInfo.getYwz());
                    trainBasicInfo1.setEdwx(pjInfo.getYwx());

                    trainBasicInfo1.setRws(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setRwx(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setYws(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setYwz(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setYwx(pjInfo.BLANK_PRICE);
                } else {
                    trainBasicInfo1.setYdws(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setYdwx(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setEdws(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setEdwz(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setEdwx(pjInfo.BLANK_PRICE);

                    trainBasicInfo1.setRws(pjInfo.getRws());
                    trainBasicInfo1.setRwx(pjInfo.getRwx());
                    trainBasicInfo1.setYws(pjInfo.getYws());
                    trainBasicInfo1.setYwz(pjInfo.getYwz());
                    trainBasicInfo1.setYwx(pjInfo.getYwx());
                }
                trainBasicInfo1.setWz(pjInfo.getWz());
                trainBasicInfo1.setQt(pjInfo.getQt());
                trainBasicInfo1.setUpdateTime(new Date());
                if (result == null) {
                    trainBasicInfo1.setAddTime(new Date());
                    trainBasicInfoMapper.insert(trainBasicInfo1);
                } else {
                    trainBasicInfo1.setId(result.getId());
                    trainBasicInfoMapper.updateById(trainBasicInfo1);
                }
            }
        } else {
            LOGGER.info("查询结果为空,参数 = {}", JacksonJsonUtils.toJson(trainBasicInfo));
        }


    }
}
