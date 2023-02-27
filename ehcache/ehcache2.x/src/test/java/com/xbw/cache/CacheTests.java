package com.xbw.cache;

import com.xbw.cache.ehcache.EhcacheCache;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheTests {
    CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        EhcacheCache ehcacheCache = new EhcacheCache();
        cacheManager = ehcacheCache.cacheManager("");
    }

    @Test
    void getCacheNames() {
        for (String cacheName : cacheManager.getCacheNames()) {
            System.out.println("cacheName -> " + cacheName);
        }
    }

    @Test
    void getCache() {
        Cache cache = cacheManager.getCache("ehcache");
        Element element = new Element("key", "val");
        cache.put(element);
        System.out.println("Element -> " + element);

        Element result = cache.get("key");
        System.out.println("Element -> " + result);
        cache.flush();
    }
}
