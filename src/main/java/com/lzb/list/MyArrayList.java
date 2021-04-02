package com.lzb.list;


import java.util.Arrays;
import java.util.Iterator;

public class MyArrayList<E> implements MyList<E>, Iterable<E> {
	
	private Object[] elementData = null;
	
	private int size = 0;
	
	public MyArrayList(int capacity) {
		if(capacity < 0) {
			throw new IllegalArgumentException("Illegal Capacity: " + capacity);
		}
		
		elementData = new Object[capacity];
	} 
	
	public MyArrayList() {
		elementData = new Object[10];
	}
	
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	};
	
	@SuppressWarnings("unchecked")
	public E get(int index) {
		rangeCheck(index);
		return (E)elementData[index];
	};
	
	public void add(E object) {
		ensureAdd(size + 1);
		elementData[size++] = object;
	};
	
	public void add(int index, E object) {
		rangeCheck(index);
		ensureAdd(size + 1);
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = object;
		size++;
	};
	
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		rangeCheck(index);
		
		Object removeObject = elementData[index];
		int copyLength = size-index-1;
		if(copyLength > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, copyLength);
		}
		elementData[--size] = null;
		return (E)removeObject;
	};
	
	public int size() {
		return size;
	};
	
	public void clear() {
		for(int i=0; i<elementData.length; i++) {
			elementData[i] = null;
		}
		elementData = null;
		size = 0;
	};
	
	public void set(int index, E object) {
		rangeCheck(index);
		
		elementData[index] = object;
	};
	
	/*
	确保可以添加元素
	*/
	private void ensureAdd(int newLength) {
		if(newLength < 0) {
			throw new IllegalArgumentException("Illegal Index: " + newLength);
		}
		
		int oldLength = elementData.length;
		if(newLength > oldLength) {
			//Java源码的意思是一旦传进来的newLength大于elementData的长度，
			//就会对elementData扩容,是针对现在数组的长度扩容，而不是对于数组的实际长度
			int _newLength = (3 * oldLength)/2 + 1;
			if(_newLength < newLength) {_newLength = newLength;}
			elementData = Arrays.copyOf(elementData, _newLength);
		}
	}
	
	private void rangeCheck(int index) {
		if(index < 0) {
			throw new IllegalArgumentException("Illegal Index: " + index);
		}
		
		if(size < index) {
			throw new IndexOutOfBoundsException("Out of bounds: " + index);
		}
	}
	
	private class Itr<E> implements Iterator<E> {

		private int lastIndex = -1;
		private int cursor;
		@Override
		public boolean hasNext() {
			return cursor != size;
		}

		@Override
		public E next() {
			lastIndex = cursor;
			return(E)elementData[cursor++];
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new Itr<E>();
	} 
}