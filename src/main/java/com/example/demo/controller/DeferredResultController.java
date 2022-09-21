package com.example.demo.controller;

import com.example.demo.executor.NameThreadFactory;
import com.example.demo.thread.AbstractTracerRunnable;
import com.example.demo.wrapper.DeferredResultWrapper;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@RestController
public class DeferredResultController {


    private static final Logger LOGGER = LoggerFactory.getLogger(DeferredResultController.class);

    private final Multimap<String, DeferredResultWrapper> deferredResults =
            Multimaps.synchronizedSetMultimap(TreeMultimap.create(String.CASE_INSENSITIVE_ORDER, Ordering.natural()));

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 60, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), new NameThreadFactory("threadpool-"));

    @GetMapping(value = "/getDeferredResult")
    public DeferredResult<String> test() {
        DeferredResult<String> deferredResult = new DeferredResult<>(20000L, "{\"success\":false}");

        deferredResult.onTimeout(() -> LOGGER.info(Thread.currentThread().getName() + " DeferredResult time out"));

        // 不论超时还是正常执行完,onCompletion()方法都会执行
        deferredResult.onCompletion(() -> LOGGER.info(Thread.currentThread().getName() + " DeferredResult completion"));
        /**
         * 自定义的setResultHandler方法
         * 可能会被org.springframework.web.context.request.async.WebAsyncManager#startDeferredResultProcessing(org.springframework.web.context.request.async.DeferredResult, java.lang.Object...)
         * 覆盖，导致不能执行
         */
        deferredResult.setResultHandler(result -> {
            LOGGER.info(Thread.currentThread().getName() + " DeferredResultHandler = " + result);
        });
        CompletableFuture.supplyAsync(() -> {
            //throw new RuntimeException();
            long sleepTime = ThreadLocalRandom.current().nextInt(1, 200);
            LOGGER.info("CompletableFuture sleepTime = " + sleepTime);
            try {
                // 模拟业务处理
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (Exception ignored) {

            }
            return "{\"success\":true}";
        }, threadPoolExecutor).handle((s, throwable) -> {
            LOGGER.info(Thread.currentThread().getName() + " CompletableFuture handle result = " + s);
            if (throwable == null) {
                return s;
            }
            LOGGER.info(Thread.currentThread().getName() + " 异常信息 = " + throwable.getMessage());
            return "{\"success\":false}";
        }).whenComplete((result, throwable) -> {
            LOGGER.info(Thread.currentThread().getName() + " CompletableFuture complete result = " + result);
            deferredResult.setResult(result);
            try {
                // 模拟处理result,这里不会阻塞请求,setResult就会返回给客户端结果了
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ignored) {

            }

        });

        return deferredResult;
    }


    @GetMapping(value = "/pollNotification/{key}")
    public DeferredResult<ResponseEntity<List<String>>> pollNotification(@PathVariable("key") String key) {
        DeferredResultWrapper deferredResultWrapper = new DeferredResultWrapper(10 * 1000);

        /**
         * 1、set deferredResult before the check, for avoid more waiting
         * If the check before setting deferredResult,it may receive a notification the next time
         * when method handleMessage is executed between check and set deferredResult.
         */
        deferredResultWrapper.onTimeout(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("time out");
            }
        });


        deferredResultWrapper.onCompletion(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                //unregister all keys
                LOGGER.info("onCompletion");
                deferredResults.remove(key, deferredResultWrapper);
            }
        });

        //register all keys
        this.deferredResults.put(key, deferredResultWrapper);

        return deferredResultWrapper.getResult();
    }


    @GetMapping(value = "/handleMessage/{key}")
    public String handleMessage(@PathVariable("key") String key) {

        if (deferredResults.containsKey(key)) {
            LOGGER.info("key = {} 存在待处理数据", key);
            List<DeferredResultWrapper> results = Lists.newArrayList(deferredResults.get(key));
            results.forEach(deferredResultWrapper -> deferredResultWrapper.setResult("current time = " + new Date()));
        } else {
            LOGGER.info("key = {} 不存在请求数据", key);
        }
        return "{\"success\":true}";
    }
}
