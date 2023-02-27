package com.xbw.cache.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * https://www.ehcache.org/documentation/3.8/getting-started.html
 */
public class EhcacheCache {

    public static void cacheManager() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();

        Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);
        preConfigured.put(1L, "preConfigured");
        System.out.println(preConfigured.get(1L));

        Cache<Long, String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)).build());

        myCache.put(1L, "myCache");
        String value = myCache.get(1L);
        System.out.println(value);

        cacheManager.removeCache("preConfigured");
        cacheManager.close();
    }

    public Cache<?, ?> getCache() {
        Cache<Long, String> cache;
        return null;
    }
}
