package com.example.demo.service.impl;

public interface IGuavaCacheService {

    void get(String key);

    void put(String key, Long value);

    void invalidate(String key);

    void invalidateAll();
}
