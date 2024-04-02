package org.example.carcatalog.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public final class SimpleCache<K, V> extends LinkedHashMap<K, V> {
    /**
     * Поле размер кэша.
     */
    @Value("${cache.cacheSize}")
    private Long cacheSize;
    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
        return size() > cacheSize;
    }
    @Override
    public boolean equals(final Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        return cacheSize.equals(((SimpleCache<V, K>) obj).cacheSize);
    }
    @Override
    public int hashCode() {
        return  super.hashCode() + cacheSize.hashCode();
    }
}
