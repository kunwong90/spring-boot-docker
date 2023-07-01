package com.example.demo.service.impl;


import com.example.demo.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GuavaCacheServiceImpl implements IGuavaCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCacheServiceImpl.class);

    @Resource
    private Cache<String, Long> cache;

    @Override
    public void get(String key) {
        try {
            long result = cache.get(key);
            LOGGER.info("get key = {},value = {}", key, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(String key, Long value) {
        cache.put(key, value);
    }

    @Override
    public void invalidate(String key) {
        cache.invalidate(key);
    }

    @Override
    public void invalidateAll() {
        cache.invalidateAll();
    }
}
