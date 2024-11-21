package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.utils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExpiringMap<K, V> {
    private final Map<K, V> map = new ConcurrentHashMap<>();
    private final long ttl;
    private final long cleanupInterval;

    public ExpiringMap(long ttlInSeconds, long cleanupIntervalInSeconds) {
        this.ttl = ttlInSeconds * 1000;
        this.cleanupInterval = cleanupIntervalInSeconds * 1000;
        startCleanupTask();
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public void remove(K key) {
        map.remove(key);
    }

    private void startCleanupTask() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            map.clear(); // Limpia el cache (simulado).
        }, cleanupInterval, cleanupInterval, TimeUnit.MILLISECONDS);
    }
}
