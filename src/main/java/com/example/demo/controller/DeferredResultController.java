package com.example.demo.controller;

import com.example.demo.executor.NameThreadFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.*;

@RestController
public class DeferredResultController {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 60, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), new NameThreadFactory("threadpool-"));

    @GetMapping(value = "/getDeferredResult")
    public DeferredResult<String> test() {
        DeferredResult<String> deferredResult = new DeferredResult<>(20000L, "{\"success\":false}");

        deferredResult.onTimeout(() -> System.out.println(Thread.currentThread().getName() + " DeferredResult time out"));

        // 不论超时还是正常执行完,onCompletion()方法都会执行
        deferredResult.onCompletion(() -> System.out.println(Thread.currentThread().getName() + " DeferredResult completion"));
        /**
         * 自定义的setResultHandler方法
         * 可能会被org.springframework.web.context.request.async.WebAsyncManager#startDeferredResultProcessing(org.springframework.web.context.request.async.DeferredResult, java.lang.Object...)
         * 覆盖，导致不能执行
         */
        deferredResult.setResultHandler(result1 -> {
            System.out.println(Thread.currentThread().getName() + " DeferredResultHandler = " + result1);
        });
        CompletableFuture.supplyAsync(() -> {
            //throw new RuntimeException();
            long sleepTime = ThreadLocalRandom.current().nextInt(1, 200);
            System.out.println("CompletableFuture sleepTime = " + sleepTime);
            try {
                // 模拟业务处理
                TimeUnit.MILLISECONDS.sleep(sleepTime);
            } catch (Exception ignored) {

            }
            return "{\"success\":true}";
        }, threadPoolExecutor).handle((s, throwable) -> {
            System.out.println(Thread.currentThread().getName() + " CompletableFuture handle result = " + s);
            if (throwable == null) {
                return s;
            }
            System.out.println(Thread.currentThread().getName() + " 异常信息 = " + throwable.getMessage());
            return "{\"success\":false}";
        }).whenComplete((result, throwable) -> {
            System.out.println(Thread.currentThread().getName() + " CompletableFuture complete result = " + result);
            deferredResult.setResult(result);
            try {
                // 模拟处理result,这里不会阻塞请求,setResult就会返回给客户端结果了
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ignored) {

            }

        });

        return deferredResult;
    }
}
