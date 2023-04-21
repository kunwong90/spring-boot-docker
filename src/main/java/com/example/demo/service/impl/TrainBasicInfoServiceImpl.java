package com.example.demo.service.impl;

import com.example.demo.entity.TrainBasicInfo;
import com.example.demo.mapper.TrainBasicInfoMapper;
import com.example.demo.retrofit2.converter.StringConvertFactory;
import com.example.demo.service.ITrainBasicInfoService;
import com.example.demo.service.QueryTrainStationName;
import com.example.demo.trainprice.utils.h12306.core.CoreZZCX;
import com.example.demo.trainprice.utils.h12306.pojo.PJInfo;
import com.example.demo.trainprice.utils.h12306.pojo.TrainInfo;
import com.example.demo.trainprice.utils.h12306.pojo.YunXingInfo;
import com.example.demo.util.JacksonJsonUtils;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class TrainBasicInfoServiceImpl implements ITrainBasicInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainBasicInfoServiceImpl.class);

    @Resource
    private TrainBasicInfoMapper trainBasicInfoMapper;

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(100, 100,
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /*private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //.addInterceptor(new AddCookiesInterceptor())
            .connectTimeout(1, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .readTimeout(2, TimeUnit.SECONDS).build();*/

    public OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder
                    .connectTimeout(2, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                    .retryOnConnectionFailure(true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //private static final String API_URL = "https://www.12306.cn";
    private static final String API_URL = "https://kyfw.12306.cn";

    private Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl(API_URL).callFactory(getUnsafeOkHttpClient())
                    .addConverterFactory(StringConvertFactory.create())
                    .build();

    private CoreZZCX coreZZCX = new CoreZZCX();


    @Override
    public TrainBasicInfo selectOne(TrainBasicInfo trainBasicInfo) {
        LocalDate localDate = LocalDate.now();

        String date = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        TrainBasicInfo trainBasicInfo1 = trainBasicInfoMapper.selectOne(trainBasicInfo);
        if (trainBasicInfo1 == null) {
            long start = System.currentTimeMillis();
            List<TrainInfo> trainInfoList = coreZZCX.query(trainBasicInfo.getDepartureStationName(), trainBasicInfo.getDestStationName(), Integer.parseInt(date));
            LOGGER.info("查询耗时 = {}", (System.currentTimeMillis() - start));
            for (TrainInfo trainInfo : trainInfoList) {
                if (StringUtils.equals(trainBasicInfo.getDepartureStationName(), trainInfo.FZ) && StringUtils.equals(trainBasicInfo.getDestStationName(), trainInfo.DZ)
                        && StringUtils.equals(trainBasicInfo.getTrainNo(), trainInfo.CC)) {
                    PJInfo pjInfo = trainInfo.PJ;
                    trainBasicInfo1 = new TrainBasicInfo();
                    trainBasicInfo1.setTrainNo(trainBasicInfo.getTrainNo());
                    trainBasicInfo1.setStartStationName(trainInfo.SFZ);
                    trainBasicInfo1.setEndStationName(trainInfo.ZDZ);
                    trainBasicInfo1.setDepartureStationName(trainInfo.FZ);
                    trainBasicInfo1.setDestStationName(trainInfo.DZ);
                    trainBasicInfo1.setDistance(trainInfo.LC);
                    trainBasicInfo1.setYdz(pjInfo.getYdz());
                    trainBasicInfo1.setEdz(pjInfo.getEdz());
                    trainBasicInfo1.setSwz(pjInfo.getSwz());
                    trainBasicInfo1.setTdz(pjInfo.getTdz());
                    trainBasicInfo1.setRz(pjInfo.RZ);
                    trainBasicInfo1.setYz(pjInfo.YZ);
                    // 查询不到
                    trainBasicInfo1.setGjrws(pjInfo.BLANK_PRICE);
                    // 查询不到
                    trainBasicInfo1.setGjrwx(pjInfo.BLANK_PRICE);
                    trainBasicInfo1.setDws(pjInfo.getDw());
                    trainBasicInfo1.setDwx(pjInfo.BLANK_PRICE);
                    if (StringUtils.startsWith(trainBasicInfo.getTrainNo(), "D")) {
                        trainBasicInfo1.setYdws(pjInfo.RWs);
                        trainBasicInfo1.setYdwx(pjInfo.RWx);
                        trainBasicInfo1.setEdws(pjInfo.YWs);
                        trainBasicInfo1.setEdwz(pjInfo.YWz);
                        trainBasicInfo1.setEdwx(pjInfo.YWx);

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

                        trainBasicInfo1.setRws(pjInfo.RWs);
                        trainBasicInfo1.setRwx(pjInfo.RWx);
                        trainBasicInfo1.setYws(pjInfo.YWs);
                        trainBasicInfo1.setYwz(pjInfo.YWz);
                        trainBasicInfo1.setYwx(pjInfo.YWx);
                    }
                    trainBasicInfo1.setWz(pjInfo.WZ);
                    trainBasicInfo1.setQt(pjInfo.QT);
                    trainBasicInfo1.setAddTime(new Date());
                    trainBasicInfo1.setUpdateTime(new Date());
                    trainBasicInfoMapper.insert(trainBasicInfo1);
                }
            }

        }
        return trainBasicInfo1;
    }

    @Override
    public void queryAllAndSave() {


        // Create an instance of our GitHub API interface.
        QueryTrainStationName queryTrainStationName = retrofit.create(QueryTrainStationName.class);

        // Create a call instance for looking up Retrofit contributors.

        Call<String> call = queryTrainStationName.getStationName();

        try {
            // Fetch and print a list of the contributors to the library.
            String stationNameStr = call.execute().body();
            if (StringUtils.isNotBlank(stationNameStr)) {
                List<String> stationNameList = new ArrayList<>();
                String[] stationNames = stationNameStr.split("@");
                for (int i = 1; i < stationNames.length; i++) {
                    String name = stationNames[i].split("\\|")[1];
                    if (stationNameList.contains(name)) {
                        continue;
                    }
                    stationNameList.add(name);
                }
                if (CollectionUtils.isEmpty(stationNameList)) {
                    LOGGER.warn("未查询到车站信息");
                    return;
                }
                int threadNums = THREAD_POOL_EXECUTOR.getCorePoolSize();
                int total = stationNameList.size();
                int avg = total / threadNums;

                if (avg != 0) {
                    threadNums = threadNums + 1;
                }
                for (int i = 0; i < threadNums; i++) {
                    int fromIndex = i * avg;
                    int endIndex = (i + 1) * avg;
                    if (endIndex > total) {
                        endIndex = total;
                    }
                    List<String> subList = stationNameList.subList(fromIndex, endIndex);
                    THREAD_POOL_EXECUTOR.submit(() -> {
                        try {
                            for (String depName : subList) {
                                for (String destName : stationNameList) {
                                    for (int dateIndex = 0; dateIndex < 15; dateIndex++) {
                                        TrainBasicInfo trainBasicInfo = new TrainBasicInfo();
                                        trainBasicInfo.setDepartureStationName(depName);
                                        trainBasicInfo.setDestStationName(destName);
                                        LocalDate localDate = LocalDate.now().plusDays(dateIndex);
                                        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                                        trainBasicInfo.setDepartureDate(Date.from(instant));
                                        long start = System.currentTimeMillis();
                                        String date = new SimpleDateFormat("yyyyMMdd").format(trainBasicInfo.getDepartureDate());
                                        List<TrainInfo> trainInfoList = coreZZCX.query(trainBasicInfo.getDepartureStationName(), trainBasicInfo.getDestStationName(), Integer.parseInt(date));
                                        LOGGER.info("出发站 = {} 到达站 = {} 出发日期 = {} 查询耗时 = {}", trainBasicInfo.getDepartureStationName(), trainBasicInfo.getDestStationName(), Integer.parseInt(date), (System.currentTimeMillis() - start));
                                        if (CollectionUtils.isNotEmpty(trainInfoList)) {
                                            //LOGGER.info("查询参数 = {},结果 = {}", JacksonJsonUtils.toJson(trainBasicInfo), JacksonJsonUtils.toJson(trainInfoList));
                                            for (TrainInfo trainInfo : trainInfoList) {
                                                YunXingInfo yunXingInfo = trainInfo.getBasicYunXingInfo();
                                                Date jsrq = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(yunXingInfo.getJsrq()));
                                                if (jsrq.before(trainBasicInfo.getDepartureDate())) {
                                                    LOGGER.warn("结束日期 {} 大于发车时间 {}", yunXingInfo.getJsrq(), date);
                                                    continue;
                                                }
                                                PJInfo pjInfo = trainInfo.PJ;
                                                if (StringUtils.equals(pjInfo.BLANK_PRICE, pjInfo.YWs)
                                                        && StringUtils.equals(pjInfo.BLANK_PRICE, pjInfo.YWz)
                                                        && StringUtils.equals(pjInfo.BLANK_PRICE, pjInfo.YWx)
                                                        && StringUtils.equals(pjInfo.BLANK_PRICE, pjInfo.RWs)
                                                        && StringUtils.equals(pjInfo.BLANK_PRICE, pjInfo.RWx)) {
                                                    continue;
                                                }
                                                TrainBasicInfo query = new TrainBasicInfo();
                                                query.setTrainNo(trainInfo.CC);
                                                query.setDepartureStationName(trainInfo.FZ);
                                                query.setDestStationName(trainInfo.DZ);
                                                try {
                                                    query.setDepartureDate(DateUtils.parseDate(date, "yyyyMMdd"));
                                                } catch (Exception e) {
                                                    LOGGER.error("日期转换异常", e);
                                                }
                                                TrainBasicInfo result = trainBasicInfoMapper.selectOne(query);
                                                TrainBasicInfo trainBasicInfo1 = new TrainBasicInfo();
                                                trainBasicInfo1.setTrainNo(trainInfo.CC);
                                                trainBasicInfo1.setStartStationName(trainInfo.SFZ);
                                                trainBasicInfo1.setEndStationName(trainInfo.ZDZ);
                                                trainBasicInfo1.setDepartureStationName(trainInfo.FZ);
                                                trainBasicInfo1.setDestStationName(trainInfo.DZ);
                                                trainBasicInfo1.setDepartureDate(trainBasicInfo.getDepartureDate());
                                                trainBasicInfo1.setDistance(trainInfo.LC);
                                                trainBasicInfo1.setYdz(pjInfo.getYdz());
                                                trainBasicInfo1.setEdz(pjInfo.getEdz());
                                                trainBasicInfo1.setSwz(pjInfo.getSwz());
                                                trainBasicInfo1.setTdz(pjInfo.getTdz());
                                                trainBasicInfo1.setRz(pjInfo.RZ);
                                                trainBasicInfo1.setYz(pjInfo.YZ);
                                                // 查询不到
                                                trainBasicInfo1.setGjrws(pjInfo.BLANK_PRICE);
                                                // 查询不到
                                                trainBasicInfo1.setGjrwx(pjInfo.BLANK_PRICE);
                                                trainBasicInfo1.setDws(pjInfo.getDw());
                                                trainBasicInfo1.setDwx(pjInfo.BLANK_PRICE);
                                                if (StringUtils.startsWith(trainBasicInfo1.getTrainNo(), "D")) {
                                                    trainBasicInfo1.setYdws(pjInfo.RWs);
                                                    trainBasicInfo1.setYdwx(pjInfo.RWx);
                                                    trainBasicInfo1.setEdws(pjInfo.YWs);
                                                    trainBasicInfo1.setEdwz(pjInfo.YWz);
                                                    trainBasicInfo1.setEdwx(pjInfo.YWx);

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

                                                    trainBasicInfo1.setRws(pjInfo.RWs);
                                                    trainBasicInfo1.setRwx(pjInfo.RWx);
                                                    trainBasicInfo1.setYws(pjInfo.YWs);
                                                    trainBasicInfo1.setYwz(pjInfo.YWz);
                                                    trainBasicInfo1.setYwx(pjInfo.YWx);
                                                }
                                                trainBasicInfo1.setWz(pjInfo.WZ);
                                                trainBasicInfo1.setQt(pjInfo.QT);
                                                trainBasicInfo1.setUpdateTime(new Date());

                                                if (result == null) {
                                                    trainBasicInfo1.setAddTime(new Date());
                                                    trainBasicInfoMapper.insert(trainBasicInfo1);
                                                } else {
                                                    trainBasicInfo1.setId(result.getId());
                                                    //LOGGER.warn("更新数据,查询入参 = {}", JacksonJsonUtils.toJson(query));
                                                    trainBasicInfoMapper.updateById(trainBasicInfo1);
                                                }
                                            }
                                        } else {
                                            LOGGER.info("查询结果为空,参数 = {}", JacksonJsonUtils.toJson(trainBasicInfo));
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            LOGGER.error("处理数据异常", e);
                        }

                    });
                }
            }
        } catch (Exception e) {
            LOGGER.error("异常", e);
        }


    }
}
