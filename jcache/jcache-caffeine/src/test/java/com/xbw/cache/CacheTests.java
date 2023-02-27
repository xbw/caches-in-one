package com.xbw.cache;

import com.xbw.cache.jcache.JCacheCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CacheTests {

    /**
     * @see com.github.benmanes.caffeine.jcache.CacheManagerImpl
     * @see com.github.benmanes.caffeine.jcache.CacheProxy
     */
    @Test
    void jCache() {
        JCacheCache jCacheCache = new JCacheCache();
        javax.cache.CacheManager cacheManager = jCacheCache.getCacheManager();
        Assertions.assertEquals("com.github.benmanes.caffeine.jcache.CacheManagerImpl", cacheManager.getClass().getName());
        javax.cache.Cache cache = jCacheCache.getCache();
        Assertions.assertEquals("com.github.benmanes.caffeine.jcache.CacheProxy", cache.getClass().getName());
        jCacheCache.run();
    }

}
