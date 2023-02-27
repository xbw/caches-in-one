package com.xbw.cache.jcache;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

/**
 * https://www.ehcache.org/documentation/3.8/107.html
 */
public class JCacheCache {
    private String className;
    private CacheManager cacheManager;
    private Cache cache;

    public JCacheCache() {
        this.className = getClass().getSimpleName();
        this.cacheManager = cacheManager();
        this.cache = cache();
    }

    @CacheResult
    public String get() {
        return String.valueOf(cache.get(1L));
    }

    @CachePut
    public void put() {
        cache.put(1L, className + " -> @CachePut");
    }

    @CacheRemove
    public void remove() {
        cache.remove(1L);
    }

    public CacheManager cacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        return cacheManager;
    }

    public Cache cache() {
        return cache("jcache");
    }

    public Cache cache(String cacheName) {
        MutableConfiguration<Long, String> configuration = new MutableConfiguration<Long, String>()
                .setTypes(Long.class, String.class)
                .setStoreByValue(false)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            cache = cacheManager.createCache(cacheName, configuration);
        }
        return cache;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }


    public void run() {
        System.out.println("CacheManager -> " + cacheManager.getClass().getName());
        System.out.println("Cache -> " + cache.getClass().getName());

        cache.put(1L, className + " -> @CacheResult");

        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                put();
            }
            if (i == 3) {
                remove();
            }
            System.out.println("run " + i + " : " + get());
        }
    }
}
