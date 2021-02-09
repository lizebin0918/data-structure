package com.lzb.map.itf;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;

public interface Map<K extends Comparable<? super K>,V> {
    
    int size();

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsValue(V value);
 
    V put(K key, V value);

    V remove(Object key);

    void putAll(Map<? extends K, ? extends V> m);

    void clear();

    Set<K> keySet();

    Collection<V> values();

    public V getOrDefault(Object key, V defaultValue);

   
    void forEach(BiConsumer<? super K, ? super V> action);
    
}