package com.lzb.map.itf;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;

public interface Map<K, V> {
    
    int size();

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsValue(V value);
 
    V put(K key, V value);

    V remove(K key);

    void clear();

    Set<K> keySet();

    Collection<V> values();

    public V get(K key);

    void forEach(BiConsumer<? super K, ? super V> action);
    
}