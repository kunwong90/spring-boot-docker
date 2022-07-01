package com.example.demo.redis.lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributedLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributedLock.class);

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    public boolean tryLock(String key, String value, long time, TimeUnit timeUnit) {
        try {
            String result = stringRedisTemplate.execute(new DefaultRedisScript<String>("return redis.call('SET', KEYS[1], ARGV[1], 'EX', ARGV[2], 'NX')", String.class), Collections.singletonList(key), value, String.valueOf(timeUnit.toSeconds(time)));
            return StringUtils.equalsIgnoreCase(result, "OK");
        } catch (Exception e) {
            LOGGER.error("获取锁异常.key = " + key + " ,value = " + value, e);
        }
        return true;

    }


    public void unLock(String key, String value) {
        try {
            stringRedisTemplate.execute(new DefaultRedisScript<Boolean>("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Boolean.class), Collections.singletonList(key), value);
        } catch (Exception e) {
            LOGGER.error("解锁异常.key = " + key + " ,value = " + value, e);
        }

    }
}
