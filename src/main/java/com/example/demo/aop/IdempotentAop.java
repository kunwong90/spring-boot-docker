package com.example.demo.aop;

import com.example.demo.annotation.Idempotent;
import com.example.demo.entity.User;
import com.example.demo.redis.lock.RedisDistributedLock;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class IdempotentAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdempotentAop.class);

    @Resource
    private RedisDistributedLock redisDistributedLock;

    @Pointcut(value = "@annotation(com.example.demo.annotation.Idempotent)")
    public void idempotentCheckAspect() {
    }

    @Around(value = "idempotentCheckAspect() && @annotation(idempotent)")
    public Object around(ProceedingJoinPoint point, Idempotent idempotent) throws Throwable {
        String keyPrefix = idempotent.keyPrefix();
        if (StringUtils.isNotBlank(keyPrefix)) {
            Object[] args = point.getArgs();
            User user = (User) args[0];
            String key = keyPrefix + user.getId();
            String value = UUID.randomUUID().toString();
            try {
                if (redisDistributedLock.tryLock(key, value, 60, TimeUnit.SECONDS)) {
                    LOGGER.info("key = {},value = {}获取锁成功", key, value);
                    return point.proceed();
                } else {
                    LOGGER.warn("key = {},value = {}获取锁失败", key, value);
                }
            } finally {
                redisDistributedLock.unLock(key, value);
            }

        } else {
            throw new IllegalArgumentException("keyPrefix 不能为空");
        }
        return null;
    }
}
