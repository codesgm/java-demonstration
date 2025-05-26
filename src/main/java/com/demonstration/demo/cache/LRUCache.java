package com.demonstration.demo.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity + 1, 1.0f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public synchronized V getValue(K key) {
        return super.get(key);
    }

    public synchronized void putValue(K key, V value) {
        super.put(key, value);
    }

    public synchronized void removeValue(K key) {
        super.remove(key);
    }
}
