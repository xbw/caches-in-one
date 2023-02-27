package com.xbw.cache;

import com.xbw.cache.jcache.CamelJCacheCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CacheTests {

    @Test
    void jCache() {
        CamelJCacheCache jCacheCache = new CamelJCacheCache();
        javax.cache.CacheManager cacheManager = jCacheCache.getCacheManager();
        Assertions.assertEquals("org.infinispan.jcache.embedded.JCacheManager", cacheManager.getClass().getName());
        javax.cache.Cache cache = jCacheCache.getCache();
        Assertions.assertEquals("org.infinispan.jcache.embedded.JCache", cache.getClass().getName());
        jCacheCache.run();
    }

}
