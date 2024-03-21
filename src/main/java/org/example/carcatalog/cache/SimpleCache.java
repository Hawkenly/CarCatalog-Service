package org.example.carcatalog.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.LinkedHashMap;

@Component
public class SimpleCache<K, V> extends LinkedHashMap<K,V> {

  @Value("${cache.cacheSize}")
  private Long cacheSize;
  @Override
  protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
    return size() > cacheSize;
  }

  @Override
  public boolean equals(Object obj){
    if (!super.equals(obj)) return false;
    return cacheSize.equals (((SimpleCache<V, K>) obj).cacheSize);
  }
}