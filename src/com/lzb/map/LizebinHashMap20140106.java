package com.lzb.map;


import java.util.LinkedList;
import java.util.Map;

public class LizebinHashMap20140106<K, V> implements DefinitionMap<K, V> {

	@SuppressWarnings("rawtypes")
	private transient LinkedList[] entrys;
	
	private int size;
	
	public LizebinHashMap20140106(int capacity) {
		entrys = new LinkedList[capacity];
	}
	
	public LizebinHashMap20140106() {
		entrys = new LinkedList[1000];
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		Entry<K, V> e = new Entry<K, V>();
		e.key = key;
		e.value = value;
		int location = key.hashCode() % entrys.length;
		//防止hashCode小于0
		location = location < 0 ? -location : location;
		LinkedList<Entry<K, V>> list = entrys[location];
		if(list == null) {
			list = new LinkedList<Entry<K, V>>();
			list.add(e);
		} else {
			for(Entry<K, V> entry : list) {
				if(entry.key.equals(key)) {
					entry.value = value;
					return;
				}
			}
			list.add(e);
		}
		entrys[location] = list;
		size++;
	}

	@Override
	@SuppressWarnings("unchecked")
	public V get(K key) {
		int location = key.hashCode() % entrys.length;
		//防止hashCode小于0
		location = location < 0 ? -location : location;
		LinkedList<Entry<K, V>> list = entrys[location];
		if(list != null) {
			for(Entry<K, V> entry : list) {
				if(entry != null && entry.key.equals(key)) {
					return entry.value;
				}
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public V remove(K key) {
		Entry<K, V> oldEntry = null;
		V value = null;
		int location = key.hashCode() % entrys.length;
		//防止hashCode小于0
		location = location < 0 ? -location : location;
		LinkedList<Entry<K, V>> list = entrys[location];
		for(Entry<K, V> entry : list) {
			if(entry.key.equals(key)) {
				oldEntry = entry;
				value = entry.value;
				break;
			}
		}
		list.remove(oldEntry);
		size--;
		return value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(K key) {
		int location = key.hashCode() % entrys.length;
		//防止hashCode小于0
		location = location < 0 ? -location : location;
		LinkedList<Entry<K, V>> list = entrys[location];
		if(list != null) {
			for(Entry<K, V> entry : list) {
				if(entry != null && entry.key.equals(key)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean containsValue(V value) {
		for(LinkedList<Entry<K, V>> list : entrys) {
			for(Entry<K, V> entry : list) {
				if(entry.value.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void putAll(Map<K, V> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public void clear() {
		for(LinkedList<Entry<K, V>> list :entrys) {
			if(list != null && list.size() != 0) {
				for(Entry<K, V> entry : list) {
					entry.key = null;
					entry.value = null;
					entry = null;
				}
				list.remove();
				list = null;
			}
		}
		size = 0;
	}

    static class Entry<K, V> implements DefinitionMap.Entry<K, V> {
		private K key;
		private V value;
		
		Entry() {}
		
		Entry(int hashCode, K key, V value, Entry<K, V> next) {
			this.key = key;
			this.value = value;
		}
	}
}
