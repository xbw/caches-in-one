package com.xbw.cache.memcached;

import com.whalin.MemCached.MemCachedClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Deprecated
class KeyBean {
    private String server;
    private List<KeyItem> keyItems;

    public KeyBean() {
    }

    public KeyBean(String server) {
        this.server = server;
    }

    public static void main(String[] args) {
        MemcacheManager manager = MemcacheManager.getInstance();
        for (int i = 0; i < 10; i++) {
            manager.merge(i + "", new Random().nextFloat());
        }

        List<KeyBean> list = getAllCache(manager.getCache());
        System.out.println(list);

    }

    /**
     * 缓存遍历
     *
     * @return
     */
    public static List<KeyBean> getAllCache(MemCachedClient cache) {
        List<KeyBean> list = new ArrayList<>();
        // 遍历statsItems 获取items:1:number=?
        Map<String, Map<String, String>> statsItems = cache.statsItems();

        statsItems.forEach((server, statsItem) -> {
//            System.out.printf("server: %s, statsItem: %s%n", server, statsItem);
            KeyBean keyBean = new KeyBean(server);

            List<KeyBean.KeyItem> keyItemList = new ArrayList<>();
            statsItem.forEach((statsItemKey, statsItemValue) -> {
                if (statsItemKey.startsWith("items:") && statsItemKey.endsWith(":number")) {
                    KeyBean.KeyItem keyItem = new KeyBean.KeyItem(statsItemKey);
//                    System.out.printf("statsItemKey: %s, statsItemValue: %s%n", statsItemKey, statsItemValue);
                    // 根据items:1:number=?，调用statsCacheDump，获取每个item中的key
                    Map<String, Map<String, String>> statsCacheDump = cache.statsCacheDump(new String[]{server},
                            Integer.parseInt(statsItemKey.split(":")[1]),
                            Integer.parseInt(statsItemValue.trim()));

                    List<KeyBean.KeyItem.Key> keyList = new ArrayList<>();
                    statsCacheDump.forEach((statsCacheDumpKey, statsCacheDumpValue) -> { // loop 1
//                        System.out.printf("statsCacheDumpKey: %s, statsCacheDumpValue: %s%n", statsCacheDumpKey, statsCacheDumpValue);
                        statsCacheDumpValue.forEach((statsCacheDumpSubKey, statsCacheDumpSubValue) -> {
//                            System.out.printf("statsCacheDumpSubKey: %s, statsCacheDumpSubValue: %s%n", statsCacheDumpSubKey, statsCacheDumpSubValue);
                            KeyBean.KeyItem.Key key = new KeyBean.KeyItem.Key();
                            key.setKey(statsCacheDumpSubKey);
                            key.setProps(statsCacheDumpSubValue.replaceAll("\r|\n", ""));
                            key.setValue(cache.get(statsCacheDumpSubKey));
                            keyList.add(key);
                        });
                    });

                    keyItem.setKeys(keyList);
                    keyItemList.add(keyItem);
                }
            });


            keyBean.setKeyItems(keyItemList);
            list.add(keyBean);
        });

        return list;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public List<KeyItem> getKeyItems() {
        return keyItems;
    }

    public void setKeyItems(List<KeyItem> keyItems) {
        this.keyItems = keyItems;
    }

    static class KeyItem {
        private String keyItem;
        private List<Key> keys;

        public KeyItem(String keyItem) {
            this.keyItem = keyItem;
        }

        public String getKeyItem() {
            return keyItem;
        }

        public void setKeyItem(String keyItem) {
            this.keyItem = keyItem;
        }

        public List<Key> getKeys() {
            return keys;
        }

        public void setKeys(List<Key> keys) {
            this.keys = keys;
        }

        static class Key {
            private String key;
            private Object value;
            private String props;


            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getProps() {
                return props;
            }

            public void setProps(String props) {
                this.props = props;
            }

            public Object getValue() {
                return value;
            }

            public void setValue(Object value) {
                this.value = value;
            }
        }
    }

}
