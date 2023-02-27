package com.xbw.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.xbw.cache.caffeine.CaffeineCache;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.ref.ReferenceQueue;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.MethodName.class)
class CacheTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void cache() {
        Cache<String, Object> cache = new CaffeineCache().getCache(Duration.ofSeconds(3), Duration.ofSeconds(2));
        try {
            cache.put("caffeine", "Cache");
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                if (i == 1) {
                    Thread.sleep(1000);
                }
                System.out.printf("%s, getIfPresent() -> %s, get() -> %s%n", LocalDateTime.now(), cache.getIfPresent("caffeine"), cache.get("caffeine", key -> key + ":lambda"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadingCache() {
        LoadingCache<String, Object> cache = new CaffeineCache().getLoadingCache(2, TimeUnit.SECONDS);

        try {
            cache.put("caffeine", "LoadingCache");
            for (int i = 0; i < 6; i++) {
                Thread.sleep(1000);
                if (i == 1 || i == 4) {
                    Thread.sleep(1000);
                }
                if (i == 3) {
                    cache.invalidate("caffeine");
                }
//                cache.refresh("caffeine");
                System.out.printf("%s, getIfPresent() -> %s, get() -> %s%n", LocalDateTime.now(), cache.getIfPresent("caffeine"), cache.get("caffeine"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void asyncLoadingCache() {
        AsyncLoadingCache<String, Object> cache = new CaffeineCache().getAsyncLoadingCache(2, TimeUnit.SECONDS);
        try {
            CompletableFuture<Object> valueFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "AsyncLoadingCache";
            });
            cache.put("caffeine", valueFuture);
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                if (i == 1) {
                    Thread.sleep(1000);
                }
                System.out.printf("%s, getIfPresent() -> %s, get() -> %s%n", LocalDateTime.now(), cache.getIfPresent("caffeine").get(), cache.get("caffeine").get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
