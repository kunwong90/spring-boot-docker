package com.example.demo.controller;


import com.example.demo.service.impl.IGuavaCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/guava")
public class GuavaCacheController {

    @Resource
    private IGuavaCacheService guavaCacheService;

    @GetMapping(value = "/get")
    public String get(String key) {
        guavaCacheService.get(key);
        return "{\"success\":true}";
    }

    @GetMapping(value = "/put")
    public String put(String key, Long value) {
        guavaCacheService.put(key, value);
        return "{\"success\":true}";
    }


    @GetMapping(value = "/invalidate")
    public String invalidate(String key) {
        guavaCacheService.invalidate(key);
        return "{\"success\":true}";
    }


    @GetMapping(value = "/invalidateAll")
    public String invalidateAll() {
        guavaCacheService.invalidateAll();
        return "{\"success\":true}";
    }
}
