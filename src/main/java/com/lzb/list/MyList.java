package com.lzb.list;

import java.util.Iterator;


public interface MyList<E> {
	
	public boolean isEmpty();
	
	public E get(int i);
	
	public void add(E object);
	
	public void add(int index, E object);
	
	public E remove(int i);
	
	public int size();
	
	public void clear();
	
	public void set(int index, E object);
	
	public Iterator<E> iterator();
}
