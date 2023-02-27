package com.xbw.cache.jcs;

import org.apache.commons.jcs.engine.control.CompositeCacheManager;

/**
 * @author xbw
 */
public class JCSCache {
    private static final String DEFAULT_CACHE_NAME = "default";

    public CompositeCacheManager cacheManager() {
        return CompositeCacheManager.getInstance();
    }
}
