package com.example.demo.longpolling;

import com.example.demo.executor.NameThreadFactory;
import com.example.demo.longpolling.service.LongPollingService;
import com.example.demo.retrofit2.converter.StringConvertFactory;
import com.example.demo.util.JacksonJsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.UUID;
import java.util.concurrent.*;

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header(LongPollingService.LONG_POLLING_HEADER_TIMEOUT, "30000")
                        .header(LongPollingService.LONG_POLLING_HEADER_REQUEST_ID, UUID.randomUUID().toString())
                        .header("Content-Type", "application/json;charset=utf-8")
                        .method(original.method(), original.body()).build();
                return chain.proceed(request);
            })
            .connectTimeout(1, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .readTimeout(60, TimeUnit.SECONDS).build();

    private static final String API_URL = "http://localhost:8080";

    private static Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl(API_URL).callFactory(okHttpClient)
                    .addConverterFactory(StringConvertFactory.create())
                    .build();


    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5000), new NameThreadFactory("client-longpolling"));
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            threadPoolExecutor.execute(() -> {
                while (true) {
                    try {
                        try {
                            long sleepTime = ThreadLocalRandom.current().nextInt(500, 5000);
                            LOGGER.info("sleepTime = {}", sleepTime);
                            TimeUnit.MILLISECONDS.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                        QueryLoginResult queryLoginResult = retrofit.create(QueryLoginResult.class);
                        String param = "{\"username\":\"test\"}";
                        Call<String> call = queryLoginResult.queryLoginResult(param);
                        StopWatch stopWatch = new StopWatch();
                        stopWatch.start("监控HTTP请求");
                        LOGGER.info("开始HTTP请求");
                        String result = call.execute().body();
                        stopWatch.stop();
                        LOGGER.info("query result = {},耗时 = {}", result, stopWatch.getTotalTimeMillis());
                        ObjectNode objectNode = JacksonJsonUtils.toBean(result, ObjectNode.class);
                        if (objectNode != null) {
                            JsonNode jsonNode = objectNode.get("status");
                            if (jsonNode != null) {
                                if (jsonNode.asInt() == 200) {
                                    countDownLatch.countDown();
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                }
            });


        }

        LOGGER.info("await");
        countDownLatch.await();
        LOGGER.info("done");
        threadPoolExecutor.shutdown();
    }


    public interface QueryLoginResult {

        @POST("/login/listener")
            //@Headers({"Content-Type: application/json;charset=utf-8", "Long-Pulling-Timeout: 30000"})
        Call<String> queryLoginResult(@Body String body);
    }
}
