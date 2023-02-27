package com.xbw.cache;


import com.xbw.cache.jcache.JCacheEhcacheCache;

public class Main {
    public static void main(String[] args) {

//        new JCacheCache().app();
        new JCacheEhcacheCache().run();
    }
}
