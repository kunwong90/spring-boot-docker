package com.example.demo.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GuavaCache implements LocalCache<String, Long> {


    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCache.class);


    private ListeningExecutorService service = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000)));

    private Callable<Long> callable = () -> {
        LOGGER.info("模拟耗时10s");
        TimeUnit.SECONDS.sleep(10);
        return System.currentTimeMillis();
    };


    public final LoadingCache<String, Long> CACHE = CacheBuilder.newBuilder()
            .initialCapacity(2)
            .maximumSize(2)
            .recordStats()
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .expireAfterWrite(3000, TimeUnit.SECONDS)
            .removalListener((RemovalListener<String, Long>) notification -> LOGGER.info("cause " + notification.getCause() + " key = " + notification.getKey() + " value = " + notification.getValue() + " 被移除"))
            .build(new CacheLoader<String, Long>() {
                       // 对于同一个key，并发访问时，只会有一个线程去加载数据，其余线程会被阻塞
                       @Override
                       public Long load(String s) throws Exception {
                           LOGGER.info("load 模拟耗时5s");
                           TimeUnit.SECONDS.sleep(5);
                           long value = System.currentTimeMillis();
                           LOGGER.info("load param = " + s + " ,value = " + value);
                           return value;

                       }

                       // 对于同一个key，并发访问时，所有的线程都会先返回旧值，然后异步加载新值
                       @Override
                       public ListenableFuture<Long> reload(String key, Long oldValue) throws Exception {
                           LOGGER.info("reload key = {}, oldValue = {}", key, oldValue);
                           return service.submit(callable);
                       }
                   }


            );


    @Override
    public Long get(String key) {
        try {
            return CACHE.get(key);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public void put(String key, Long value) {
        CACHE.put(key, value);
    }

    @Override
    public void invalidate(String key) {
        CACHE.invalidate(key);
    }

    @Override
    public void invalidateAll() {
        CACHE.invalidateAll();
    }
}
