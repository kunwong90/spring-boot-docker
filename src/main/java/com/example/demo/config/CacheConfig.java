package com.example.demo.config;


import com.example.demo.cache.Cache;
import com.example.demo.cache.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {


    @Bean
    public Cache<String, Long> cache() {
        return new GuavaCache();
    }
}
