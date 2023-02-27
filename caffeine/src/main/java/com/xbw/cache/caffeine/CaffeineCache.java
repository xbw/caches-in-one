package com.xbw.cache.caffeine;

import com.github.benmanes.caffeine.cache.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * https://github.com/google/guava/wiki/CachesExplained
 */
public class CaffeineCache {

    public Cache<String, Object> getCacheBySize(int initialCapacity, long maximumSize) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build();
        return cache;
    }

    public Cache<String, Object> getCacheByWeight(int initialCapacity, long maximumWeight) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumWeight(maximumWeight)
                .weigher((key, value) -> {
                    return 1;
                })
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build();
        return cache;
    }

    public Cache<String, Object> getCacheExpireAfterWrite(Duration expireAfterWrite) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWrite)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build();
        return getCacheExpireAfterWrite(expireAfterWrite.toNanos(), TimeUnit.NANOSECONDS);
    }

    public Cache<String, Object> getCacheExpireAfterWrite(long expireAfterWrite, TimeUnit unit) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWrite, unit)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build();
        return cache;
    }

    public Cache<String, Object> getCacheExpireAfterAccess(Duration expireAfterAccess) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterAccess(expireAfterAccess)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build();
        return cache;
    }

    public Cache<String, Object> getCacheExpireAfterAccess(long expireAfterAccess, TimeUnit unit) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterAccess(expireAfterAccess, unit)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build();
        return cache;
    }

    public Cache<String, Object> getCache(Duration expireAfterWrite, Duration expireAfterAccess) {
        return getCache(1, 1, expireAfterWrite, expireAfterAccess);
    }

    public Cache<String, Object> getCache(int initialCapacity, long maximumSize, Duration expireAfterWrite, Duration expireAfterAccess) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterWrite(expireAfterWrite) //primary
                .expireAfterAccess(expireAfterAccess)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build();
        return cache;
    }

    public Cache<String, Object> getCache(long expireAfterWrite, TimeUnit expireAfterWriteUnit, long expireAfterAccess, TimeUnit expireAfterAccessUnit) {
        return getCache(1, 1, expireAfterWrite, expireAfterWriteUnit, expireAfterAccess, expireAfterAccessUnit);
    }

    public Cache<String, Object> getCache(int initialCapacity, long maximumSize, long expireAfterWrite, TimeUnit expireAfterWriteUnit, long expireAfterAccess, TimeUnit expireAfterAccessUnit) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterWrite(expireAfterWrite, expireAfterWriteUnit) //primary
                .expireAfterAccess(expireAfterAccess, expireAfterAccessUnit)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build();
        return cache;
    }

    public LoadingCache<String, Object> getLoadingCache(Duration refreshAfterWrite) {
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .refreshAfterWrite(refreshAfterWrite)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build(key -> getValue(key));
        return cache;
    }

    public LoadingCache<String, Object> getLoadingCache(long refreshAfterWrite, TimeUnit unit) {
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .refreshAfterWrite(refreshAfterWrite, unit)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build(getCacheLoader());
        return cache;
    }

    public LoadingCache<String, Object> getLoadingCache(long expireAfterWrite, long refreshAfterWrite, TimeUnit unit) {
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWrite, unit)
                .refreshAfterWrite(refreshAfterWrite, unit)
                .removalListener((key, value, cause) -> customListener(key, value, cause))
                .build(getCacheLoader());
        return cache;
    }

    public AsyncLoadingCache<String, Object> getAsyncLoadingCache(long refreshAfterWrite, TimeUnit unit) {
        return getAsyncLoadingCache(1, 1, refreshAfterWrite, unit);
    }

    public AsyncLoadingCache<String, Object> getAsyncLoadingCache(int initialCapacity, long maximumSize, long refreshAfterWrite, TimeUnit unit) {
        AsyncLoadingCache<String, Object> cache = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .refreshAfterWrite(refreshAfterWrite, unit)
                .removalListener((key, value, cause) -> {
                    System.out.printf("%s key %s was removed (%s)%n", LocalDateTime.now(), key, cause);
                })
                .buildAsync(key -> completableFuture(key));
        return cache;
    }

    private CacheLoader getCacheLoader() {
        CacheLoader<String, Object> cacheLoader = new CacheLoader<String, Object>() {
            @Override
            public Object load(String key) throws Exception {
                return key + ":load";
            }

            @Override
            public Object reload(String key, Object oldValue) throws Exception {
                return key + ":reload";
            }
        };
        return cacheLoader;
    }

    public CompletableFuture<Object> completableFuture(String key) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.supplyAsync(() -> key + ":async");
    }

    private void customListener(Object key, Object value, RemovalCause cause) {
        System.out.printf("%s key %s was removed (%s)%n", LocalDateTime.now(), key, cause);
    }

    private Object getValue(String key) {
        return key + ":function";
    }

    public String get() {
        return now();
    }

    public String get(int id) {
        return now();
    }

    public String put() {
        return now();
    }

    public String put(int id) {
        return now();
    }

    public String evict() {
        return now();
    }

    public String evict(int id) {
        return now();
    }

    private void print(Cache<String, Object> cache) {
        try {
            cache.put("caffeine", "caffeine");
            for (int i = 0; i < 5; i++) {
                Object value = cache.getIfPresent("caffeine");
                System.out.println(LocalDateTime.now().toString() + "," + value);
                Thread.sleep(1 * 1000);
                if (i == 1) {
                    Thread.sleep(1 * 1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String now() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS"));
        System.out.println("now() -> " + now);
        return now;
    }
}
