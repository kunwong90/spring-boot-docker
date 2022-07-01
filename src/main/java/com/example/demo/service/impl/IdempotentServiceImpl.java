package com.example.demo.service.impl;

import com.example.demo.annotation.Idempotent;
import com.example.demo.entity.User;
import com.example.demo.service.IdempotentService;
import com.example.demo.util.JacksonJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IdempotentServiceImpl implements IdempotentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdempotentServiceImpl.class);

    @Idempotent(keyPrefix = "print:user:")
    @Override
    public User printUser(User user) {
        LOGGER.info(JacksonJsonUtils.toJson(user));
        return user;
    }

    @Idempotent(keyPrefix = "print:user:")
    @Override
    public User printUser(User user, Date date) {
        LOGGER.info(JacksonJsonUtils.toJson(user) + " date = " + date);
        return user;
    }
}
