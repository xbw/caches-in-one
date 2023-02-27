package com.xbw.cache.jcache;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;

/**
 * https://www.ehcache.org/documentation/3.8/107.html
 */
public class JCacheEhcacheCache extends JCacheCache {

    public CacheManager cacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        try {
            cacheManager = provider.getCacheManager(getClass().getResource("/ehcache.xml").toURI(), getClass().getClassLoader());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return cacheManager;
    }

    public Cache cache() {
        return cache("ehcache");
    }

    public Cache cache(String cacheName) {
        Cache cache = cacheManager().getCache(cacheName);
        return cache;
    }
}
