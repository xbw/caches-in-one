package com.xbw.cache.ehcache;


import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import java.net.URL;

/**
 * https://www.ehcache.org/documentation/3.10/xml.html
 */
public class EhcacheCache<K, V> {

    public CacheManager cacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
                .build(true);
    }

    public CacheManager cacheManager(String configurationFileName) {
        if (configurationFileName == null || "".equals(configurationFileName)) {
            configurationFileName = "/ehcache.xml";
        }
        URL url = getClass().getResource(configurationFileName);
        assert url != null;
        Configuration xmlConfig = new XmlConfiguration(url);
        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        cacheManager.init();
        return cacheManager;
    }

}

