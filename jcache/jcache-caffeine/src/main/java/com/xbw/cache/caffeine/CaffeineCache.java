package com.xbw.cache.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class CaffeineCache {


    public void cache() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(Duration.ofMinutes(5))
                .build();
        cache.put(1, "one");
        System.out.println("cache.getIfPresent(1) -> " + cache.getIfPresent(1));
        System.out.println("cache.getIfPresent(11) -> " + cache.getIfPresent(11));
        System.out.println("cache.get(11, k -> createExpensiveGraph(11)) -> " + cache.get(11, k -> createExpensiveGraph(11)) + " , " + LocalDateTime.now());

    }

    public void loadingCache() {
        LoadingCache<Object, Object> loadingCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));
        loadingCache.put("1", "one");
        System.out.println("loadingCache.getIfPresent(1) -> " + loadingCache.getIfPresent("1"));
        System.out.println("loadingCache.getIfPresent(11) -> " + loadingCache.getIfPresent("11"));
        System.out.println("loadingCache.get(11) -> " + loadingCache.get(11) + " , " + LocalDateTime.now());
        System.out.println("loadingCache.get(11, k -> createExpensiveGraph(11)) -> " + loadingCache.get(11, k -> createExpensiveGraph(11)) + " , " + LocalDateTime.now());
    }

    public void asyncLoadingCache() {
        AsyncLoadingCache<Object, Object> asyncLoadingCache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // Either: Build with a synchronous computation that is wrapped as asynchronous
                .buildAsync(key -> createExpensiveGraph(key));
        // Or: Build with a asynchronous computation that returns a future
        // .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));
    }

    private Object createExpensiveGraph(Object key) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("createExpensiveGraph(?) -> " + key + " , " + LocalDateTime.now());
        return key;
    }

}
