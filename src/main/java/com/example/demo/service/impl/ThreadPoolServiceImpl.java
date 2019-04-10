package com.example.demo.service.impl;

import com.example.demo.service.IStudentService;
import com.example.demo.service.IThreadPoolService;
import com.example.demo.thread.MdcRunnable;
import com.example.demo.thread.MdcCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ThreadPoolServiceImpl implements IThreadPoolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolServiceImpl.class);

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private IStudentService studentService;

    @Override
    public void run() {
        LOGGER.info("run start");
        threadPoolTaskExecutor.execute(() ->{
            LOGGER.info("ThreadPoolTaskExecutor exec");
            studentService.save();
        });
        threadPoolTaskExecutor.execute(new MdcRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("ThreadPoolTaskExecutor MDCRunnable");
                studentService.save();
            }
        });
        Future<String> future = threadPoolTaskExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                LOGGER.info("Callable exec");
                return "Callable";
            }
        });

        Future<String> myFuture = threadPoolTaskExecutor.submit(new MdcCallable<String>() {
            @Override
            public String callWithMdc() {
                LOGGER.info("MdcCallable exec");
                return "MdcCallable";
            }
        });
        try {
            String result = future.get();
            LOGGER.info("Future get result = {}", result);
            String result1 = myFuture.get();
            LOGGER.info("Future get result1 = {}", result1);
        } catch (InterruptedException | ExecutionException e) {

        }
        LOGGER.info("run end");
    }

    @Override
    public Object call() {
        return null;
    }
}
