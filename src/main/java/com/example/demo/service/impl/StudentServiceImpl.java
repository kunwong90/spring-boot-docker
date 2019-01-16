package com.example.demo.service.impl;

import com.example.demo.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class StudentServiceImpl implements IStudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public void save() {
        LOGGER.info("execute save method");
    }

    @Async
    @Override
    public Future<List<String>> findAll() {
        LOGGER.info("Repository in action");
        return new AsyncResult<>(Arrays.asList("Hello World", "Spring Boot is awesome"));
    }


}
