package com.xbw.cache;

import com.xbw.cache.jcache.JCacheCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CacheTests {

    /**
     * @see org.apache.commons.jcs.jcache.JCSCachingManager
     * @see org.apache.commons.jcs.jcache.JCSCache
     */
    @Test
    void jCache() {
        JCacheCache jCacheCache = new JCacheCache();
        javax.cache.CacheManager cacheManager = jCacheCache.getCacheManager();
        Assertions.assertEquals("org.apache.commons.jcs.jcache.JCSCachingManager", cacheManager.getClass().getName());
        javax.cache.Cache cache = jCacheCache.getCache();
        Assertions.assertEquals("org.apache.commons.jcs.jcache.JCSCache", cache.getClass().getName());
        jCacheCache.run();
    }

    @Test
    void run() {
        JCacheCache jCacheCache = new JCacheCache();
        jCacheCache.run();
    }

}
