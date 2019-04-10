package com.example.demo.service.impl;

import com.example.demo.service.IStudentService;
import com.example.demo.service.IThreadPoolService;
import com.example.demo.thread.MdcRunnable;
import com.example.demo.thread.MdcThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements IStudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    MdcThreadPoolExecutor threadPoolExecutor = MdcThreadPoolExecutor.newWithCurrentMdc(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

    MdcThreadPoolExecutor threadPoolExecutor1 = MdcThreadPoolExecutor.newWithInheritedMdc(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

    @Resource
    private IStudentService self;

    @Resource
    private IThreadPoolService threadPoolService;

    @Override
    public void save() {
        LOGGER.info("execute save method");
        new Thread(new MdcRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("MDCRunnable from service");
                new Thread(new MdcRunnable() {
                    @Override
                    public void runWithMDC() {
                        LOGGER.info("MDCRunnable MDCRunnable");
                    }
                }).start();
            }
        }).start();

        threadPoolExecutor.execute(()-> {
            LOGGER.info("threadPoolExecutor executor");
        });

        threadPoolExecutor1.execute(()-> {
            LOGGER.info("threadPoolExecutor1 executor");
        });
        LOGGER.info("over");
    }

    @Async
    @Override
    public Future<List<String>> findAll() {
        LOGGER.info("Repository in action");
        threadPoolService.run();
        return new AsyncResult<>(Arrays.asList("Hello World", "Spring Boot is awesome"));
    }


}
