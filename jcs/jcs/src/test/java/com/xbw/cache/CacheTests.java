package com.xbw.cache;

import com.xbw.cache.jcs.JCSCache;
import org.apache.commons.jcs3.engine.CacheElement;
import org.apache.commons.jcs3.engine.control.CompositeCache;
import org.apache.commons.jcs3.engine.control.CompositeCacheManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class CacheTests {

    private static final String DEFAULT_CACHE_NAME = "default";

    @Test
    void test() throws IOException {
        CompositeCacheManager cacheManager = new JCSCache().cacheManager();
        CompositeCache<Object, Object> cache = cacheManager.getCache(DEFAULT_CACHE_NAME);
        CacheElement<Object, Object> cacheElement = new CacheElement<>(DEFAULT_CACHE_NAME, "jcs", "value");
        cache.update(cacheElement);
        Assertions.assertEquals(cache.get("jcs").getVal(), "value");
        System.out.println(cache.getCacheAttributes());
    }

}
