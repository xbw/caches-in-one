package com.xbw.cache;

import com.xbw.cache.jcache.JCacheCache;
import com.xbw.cache.jcache.JCacheEhcacheCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CacheTests {

    /**
     * @see org.ehcache.jsr107.Eh107CacheManager
     * @see org.ehcache.jsr107.Eh107Cache
     */
    @Test
    void jCache() {
        JCacheCache jCacheCache = new JCacheCache();
        javax.cache.CacheManager cacheManager = jCacheCache.getCacheManager();
        Assertions.assertEquals("org.ehcache.jsr107.Eh107CacheManager", cacheManager.getClass().getName());
        javax.cache.Cache cache = jCacheCache.getCache();
        Assertions.assertEquals("org.ehcache.jsr107.Eh107Cache", cache.getClass().getName());
        jCacheCache.run();
    }

    @Test
    void ehcache() {
        JCacheEhcacheCache jCacheEhcacheCache = new JCacheEhcacheCache();
        javax.cache.CacheManager cacheManager = jCacheEhcacheCache.getCacheManager();
        Assertions.assertEquals("org.ehcache.jsr107.Eh107CacheManager", cacheManager.getClass().getName());
        javax.cache.Cache cache = jCacheEhcacheCache.getCache();
        Assertions.assertEquals("org.ehcache.jsr107.Eh107Cache", cache.getClass().getName());
        jCacheEhcacheCache.run();
    }
}
