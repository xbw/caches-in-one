package com.xbw.cache;

import com.xbw.cache.jcs.JCSCache;
import org.apache.jcs.engine.CacheElement;
import org.apache.jcs.engine.control.CompositeCache;
import org.apache.jcs.engine.control.CompositeCacheManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

class CacheTests {

    private static final String DEFAULT_CACHE_NAME = "default";

    @Test
    void test() throws IOException {
        CompositeCacheManager cacheManager = new JCSCache().cacheManager();
        CompositeCache cache = cacheManager.getCache(DEFAULT_CACHE_NAME);
        CacheElement cacheElement = new CacheElement(DEFAULT_CACHE_NAME, "jcs", "value");
        cache.update(cacheElement);
        Assertions.assertEquals(cache.get("jcs").getVal(), "value");
        System.out.println(cache.getCacheAttributes());
    }

}
