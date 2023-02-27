package com.xbw.cache;


import com.xbw.cache.caffeine.CaffeineCache;

public class Main {
    public static void main(String[] args) {
//        new JCacheCache().run();
        new CaffeineCache().cache();
        new CaffeineCache().loadingCache();
    }
}
