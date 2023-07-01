package com.example.demo.cache;

public interface Cache<K, V> {

    V get(K key);

    void put(K key, V value);

    void invalidate(K key);

    void invalidateAll();
}
