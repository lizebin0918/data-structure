package com.lzb.map;

import java.util.Map;

public interface DefinitionMap<K, V> {

	public void put(K key, V value);
	
	public V get(K key);
	
	public V remove(K key);
	
	public boolean containsKey(K key);
	
	public boolean containsValue(V value);
	
	public int size();
	
	public boolean isEmpty();
	
	public void putAll(Map<K, V> map);
	
	public void clear();
	
	interface Entry<K, V> {
		
	}
}
