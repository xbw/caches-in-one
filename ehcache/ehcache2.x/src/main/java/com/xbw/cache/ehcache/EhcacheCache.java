package com.xbw.cache.ehcache;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * https://www.ehcache.org/generated/2.10.4/html/ehc-all/#page/Ehcache_Documentation_Set%2Fco-cfgbasics_xml_configuration.html
 */
public class EhcacheCache {

    public CacheManager cacheManager() {
        CacheManager cacheManager = CacheManager.getInstance();
        if (cacheManager.getCache("ehcache") == null) {
            cacheManager.addCache("ehcache");
        }
        return cacheManager;
    }

    public CacheManager cacheManager(String configurationFileName) {
        if (configurationFileName == null || "".equals(configurationFileName)) {
            configurationFileName = "/ehcache.xml";
        }
        return CacheManager.newInstance(getClass().getResource(configurationFileName));
    }

}
