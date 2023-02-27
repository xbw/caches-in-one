package com.xbw.cache;

import com.xbw.cache.ehcache.EhcacheCache;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheTests {
    CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
    }

    @Test
    void getCache() {
        Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);

        preConfigured.put(1L, "da one!");
        Assertions.assertEquals(preConfigured.get(1L), "da one!");
        cacheManager.removeCache("preConfigured");
    }

    @Test
    void createCache() {
        Cache<Long, String> myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)));

        myCache.put(1L, "da two!");
        Assertions.assertEquals(myCache.get(1L), "da two!");
        cacheManager.removeCache("myCache");
    }

    @Test
    void cache() {
        EhcacheCache<String, String> ehcacheCache = new EhcacheCache<>();
        cacheManager = ehcacheCache.cacheManager("");
        Cache<String, String> ehcache = cacheManager.getCache("ehcache", String.class, String.class);

        ehcache.put("1", "da three!");
        Assertions.assertEquals(ehcache.get("1"), "da three!");
    }
}
