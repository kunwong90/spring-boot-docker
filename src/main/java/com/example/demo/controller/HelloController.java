package com.example.demo.controller;

import com.example.demo.service.IStudentService;
import com.example.demo.thread.AbstractTracerRunnable;
import com.example.demo.thread.TracerThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    TracerThreadPoolExecutor threadPoolExecutor = TracerThreadPoolExecutor.newWithCurrentMdc(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

    TracerThreadPoolExecutor threadPoolExecutor1 = TracerThreadPoolExecutor.newWithInheritedMdc(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

    //@Value(value = "${envName}")
    private String envName;

    @Resource
    private IStudentService studentService;

    @GetMapping(value = "/envName")
    private String envName() {
        return envName;
    }


    @GetMapping(value = "/hello")
    public String hello() {
        LOGGER.info("my first spring-boot-docker application");

        threadPoolExecutor.execute(() -> {
            LOGGER.info("threadPoolExecutor executor");
        });

        threadPoolExecutor1.execute(() -> {
            LOGGER.info("threadPoolExecutor1 executor");
        });

        studentService.save();

        new Thread(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("MDCRunnable execute");
            }
        }).start();

        LOGGER.info("controller over");
        return "my first spring-boot-docker application";
    }

    @GetMapping(value = "/list")
    public List<String> list() throws Exception {
        LOGGER.info("list method.thread id = {}", Thread.currentThread().getId());
        return studentService.findAll().get();
    }


    @GetMapping(value = "/thread")
    public String thread() {
        threadPoolTaskExecutor.execute(new AbstractTracerRunnable() {
            @Override
            public void runWithMDC() {
                LOGGER.info("thread method.thread id = {}", Thread.currentThread().getId());
                studentService.log();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }


                try {
                    threadPoolTaskExecutor.execute(new AbstractTracerRunnable() {
                        @Override
                        public void runWithMDC() {
                            LOGGER.info("thread method.thread id = {}", Thread.currentThread().getId());
                            int i = 1 / 0;
                        }
                    });
                } catch (Exception e) {

                }

                threadPoolTaskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        LOGGER.info("thread method.thread id = {}", Thread.currentThread().getId());
                    }
                });

                threadPoolTaskExecutor.execute(new AbstractTracerRunnable() {
                    @Override
                    public void runWithMDC() {
                        LOGGER.info("thread method.thread id = {}", Thread.currentThread().getId());
                        studentService.log();
                    }
                });
            }
        });


        return "ok";
    }

    //private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    private static final InheritableThreadLocal<Integer> currentUser = new InheritableThreadLocal<>();

    @GetMapping("wrong")
    public Map<String, String> wrong(@RequestParam("userId") Integer userId) {
        //设置用户信息之前先查询一次ThreadLocal中的用户信息
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        //设置用户信息到ThreadLocal
        currentUser.set(userId);
        //设置用户信息之后再查询一次ThreadLocal中的用户信息
        String after = Thread.currentThread().getName() + ":" + currentUser.get();
        //汇总输出两次查询结果
        Map<String, String> result = new HashMap<>();
        result.put("before", before);
        result.put("after", after);
        return result;
    }

    @GetMapping("right")
    public Map<String, String> right(@RequestParam("userId") Integer userId) {
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        currentUser.set(userId);
        try {
            String after = Thread.currentThread().getName() + ":" + currentUser.get();
            Map<String, String> result = new HashMap<>();
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            //在finally代码块中删除ThreadLocal中的数据，确保数据不串
            currentUser.remove();
        }
    }

    @GetMapping("visit")
    public void visit(HttpServletResponse response) {

        try {
            String originalUrl = "https://www.baidu.com/";
            response.setHeader("Location", originalUrl);
            response.setStatus(HttpStatus.FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
