package com.lzb.heap;

import com.lzb.heap.itf.Heap;

import java.util.Arrays;

/**
 * 二叉堆<br/>
 * Created on : 2021-02-18 23:14
 * @author lizebin
 */
public class BinaryHeap<E extends Comparable<? super E>> implements Heap<E> {

    private Object[] elements;
    private int size;

    private static final int CAPATITY = 16;

    BinaryHeap() {
        elements = new Object[CAPATITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        Arrays.fill(elements, null);
    }

    @Override
    public void add(E element) {
        ensureCapacity(size + 1);
        if (element == null) {
            throw new IllegalArgumentException("element should not null");
        }

    }

    @Override
    public E get() {
        if (isEmpty()) {
            return null;
        }
        return (E) elements[0];
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E replace(E element) {
        return null;
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        // 新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
}
