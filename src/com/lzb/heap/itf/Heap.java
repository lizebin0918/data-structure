package com.lzb.heap.itf;
/**
 * 堆接口<br/>
 * Created on : 2021-02-18 23:14
 * @author lizebin
 */
public interface Heap<E> {

    int size();
    boolean isEmpty();
    void clear();
    void add(E element);


    E get();

    /**
     * 删除堆顶元素
     * @return
     */
    E remove();

    /**
     * 删除堆顶元素，同时插入一个新元素
     * @param element
     * @return
     */
    E replace(E element);
    
}
