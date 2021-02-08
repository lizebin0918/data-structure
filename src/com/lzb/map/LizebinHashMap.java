package com.lzb.map;

import java.util.Map;

/**
 * 修改HashMap： LinkedList[] entrys --》Entry[] entrys
 * 还没有实现：Iterator接口
 * */
public class LizebinHashMap<K, V> implements DefinitionMap<K, V> {

	@SuppressWarnings("rawtypes")
	private transient Entry[] entrys;
	
	private final int INITIAL_CAPACITY = 20;
	
	private static final int MAXIMUM_CAPACITY = 1 << 30;
	
	private int size;
	
	public LizebinHashMap(int capacity) {
		entrys = new Entry[capacity];
	}
	
	public LizebinHashMap() {
		entrys = new Entry[INITIAL_CAPACITY];
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		if(entrys.length + 1 > INITIAL_CAPACITY) {
			//对数组进行扩容
			resize(INITIAL_CAPACITY * 2 + 1);
		}
		int hashCode = key.hashCode() % entrys.length;
		//防止hashCode小于0
		hashCode = hashCode < 0 ? -hashCode : hashCode;
		for (Entry<K,V> e = entrys[hashCode];e != null && e.next != null; e = e.next) {
			if(e.hashCode == hashCode && e.key.equals(key)) {
				e.setValue(value);
				return;
			}
		}
		addEntry(hashCode, key, value);
		size++;
	}

	//扩容
	@SuppressWarnings("unchecked")
	private void resize(int newCapacity) {
        int oldCapacity = entrys.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            return;
        }

        Entry<K, V>[] newTable = new Entry[newCapacity];
        transfer(newTable);
        entrys = newTable;
	}
	
	//entrys copy to newEntrys
	@SuppressWarnings("unchecked")
	private void transfer(Entry<K, V>[] newEntrys) {
		for(int i=0; i<entrys.length; i++) {
			Entry<K, V> entry = entrys[i];
			if(entry != null) {//如果此位置的entry为null,则不取
				do {
					Entry<K, V> next = entry.next;
					int hashCode = entry.key.hashCode() % newEntrys.length;
					//防止hashCode小于0
					hashCode = hashCode < 0 ? -hashCode : hashCode;
					//这个链表的复制，会倒叙复制：entrys[0]:1,2,3,4-->newEntrys:4,3,2,1
                    entry.next = newEntrys[hashCode];
                    newEntrys[hashCode] = entry;
                    entry = next;
                } while (entry != null);
			} 
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addEntry(int hashCode, K key, V value) {
		//把该hashCode的整个链表拿出来
		Entry<K ,V> currentEntry = entrys[hashCode];
		//把新插入的entry作为整个链的header
		Entry<K ,V> entry = new Entry<K, V>(hashCode, key, value, currentEntry);
		entrys[hashCode] = entry;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public V get(K key) {
		int location = key.hashCode() % entrys.length;
		//防止hashCode小于0
		location = location < 0 ? -location : location;
		Entry<K, V> entry = entrys[location];
		Entry<K, V> retEntry = entry;
		if(entry != null) {
			for (Entry<K,V> e = entrys[location]; e.next != null; e = e.next) {
				if(entry != null && entry.hashCode == location && entry.key.equals(key)) {
					retEntry = entry;
					break;
				}
			}
			return retEntry.value;
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public V remove(K key) {
		Entry<K, V> next = null;
		V value = null;
		int location = key.hashCode() % entrys.length;
		//防止hashCode小于0
		location = location < 0 ? -location : location;
		Entry<K, V> pre = entrys[location];
		//pre之前的一个元素
		Entry<K, V> e = pre;
		while(pre.next != null) {
			if(pre.next.hashCode == location && pre.key.equals(key)) {
				value = pre.value;
				next = pre.next;
				break;
			}
			e = pre.next;
			pre = e;
		}
		if(e == pre) {
			value = pre.value;
			entrys[location] = null;
		} else {
			e.next = next;
		}
		size--;
		return value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(K key) {
		int location = key.hashCode() % entrys.length;
		//防止hashCode小于0
		location = location < 0 ? -location : location;
		Entry<K, V> entry=entrys[location];
		if(entry != null && entry.key.equals(key)) {
			return true;
		}
		for(; entry != null && entry.next!=null; entry=entry.next) {
			if(entry != null && entry.key.equals(key) && entry.hashCode == location) {
				return true;
			}
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean containsValue(V value) {
		for(Entry<K, V> entry : entrys) {
			for(Entry<K, V> next=entry; entry != null && next.next!=null; next=next.next) {
				if(entry != null && entry.value.equals(value)) {
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
		Entry<K, V>[] tab = entrys;
		for (int i = 0; i < tab.length; i++)
			tab[i] = null;
		size = 0;
	}

    static class Entry<K, V> implements DefinitionMap.Entry<K, V> {
    	private int hashCode;
    	private Entry<K, V> next;
		private K key;
		private V value;
		
		Entry() {}
		
		Entry(int hashCode, K key, V value, Entry<K, V> next) {
			this.hashCode = hashCode;
			this.next = next;
			this.key = key;
			this.value = value;
		}
		
		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}
	}
}
