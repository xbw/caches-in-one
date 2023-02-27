package com.xbw.cache;

import com.xbw.cache.jcache.JCacheCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CacheTests {


    /**
     * javax.cache.CacheException: No CachingProviders have been configured
     */
    @Test
    void jCache() {
        Assertions.assertThrows(javax.cache.CacheException.class, JCacheCache::new);
    }

}
