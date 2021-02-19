package com.lzb.heap;

import com.lzb.heap.itf.Heap;
import com.lzb.tree.printer.BinaryTreeInfo;
import com.lzb.tree.printer.BinaryTrees;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 最小二叉堆 <br/>
 *
 * 符合完全二叉树性质
 * 父节点索引:(index + 1) >> 1 - 1
 * 左孩子:(index << 1) + 1
 * 右孩子:(index + 1) << 1
 * 最后一个非叶子节点索引(n:数组长度):(n + 1)/2 - 1
 *
 * Created on : 2021-02-18 23:14
 * @author lizebin
 */
public class BinaryHeap<E extends Comparable<? super E>> implements Heap<E>, BinaryTreeInfo {

    private E[] elements;
    private int size;
    private Class<E> type;

    private static final int CAPATITY = 16;

    public BinaryHeap(Class<E> type) {
        this.type = type;
        this.elements = (E[]) Array.newInstance(type, CAPATITY);
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
        if (element == null) {
            throw new IllegalArgumentException("element should not null");
        }
        ensureCapacity(size + 1);
        elements[size] = element;
        heapifyUp(size);
        ++size;
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
        if (isEmpty()) {
            return null;
        }
        int index = 0;
        E e = elements[index];
        elements[index] = elements[size - 1];
        elements[size - 1] = null;
        --size;
        heapifyDown(index);
        return e;
    }

    @Override
    public E replace(E element) {
        E oldElement = elements[0];
        elements[0] = element;
        heapifyDown(0);
        return oldElement;
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        // 新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) Array.newInstance(type, newCapacity);
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     * 向上堆化
     * @param index
     */
    private void heapifyUp(int index) {
        int parent = parent(index);
        E e = elements[index];
        if (parent < 0 || e == null) {
            return;
        }

        int current = index;
        while (parent >= 0) {
            E pe = elements[parent];
            if (pe.compareTo(e) <= 0) {
                break;
            }
            elements[current] = pe;
            current = parent;
            parent = parent(current);
        }

        if (current != index) {
            elements[current] = e;
        }

    }

    /**
     * 向下堆化
     * @param index
     */
    private void heapifyDown(int index) {
        int sci = smallerChild(index);
        int current = index;
        E e = elements[current];
        while (sci > 0) {
            E smaller = elements[sci];
            if (smaller.compareTo(e) > 0) {
                break;
            }
            elements[current] = smaller;
            current = sci;
            sci = smallerChild(current);
        }

        if (current != index) {
            elements[current] = e;
        }

    }

    /**
     * 父亲节点索引
     * @param i
     * @return -1 表示无根节点
     */
    private int parent(int i) {
        if (i == 0) {
            return -1;
        }
        return ((i + 1) >> 1) - 1;
    }

    /**
     * 左孩子索引
     * @param i
     * @return
     */
    private int left(int i) {
        return (i << 1) + 1;
    }

    /**
     * 右孩子索引
     * @param i
     * @return
     */
    private int right(int i) {
        return (i + 1) << 1;
    }

    /**
     * 最后一个非叶子节点索引
     * @param size
     * @return
     */
    private int lastParent(int size) {
        return ((size + 1) >> 1) - 1;
    }

    /**
     * 返回更大值的孩子
     * @param i
     * @return
     */
    public int smallerChild(int i) {
        int li = left(i), ri = right(i);
        if (li >= size) {
            return -1;
        }
        if (ri >= size) {
            return li;
        }
        E left = elements[li];
        E right = elements[ri];
        return left.compareTo(right) < 0 ? li : ri;
    }

    public static void main(String[] args) {

        BinaryHeap<Integer> heap = new BinaryHeap<>(Integer.class);
        for (int i=20; i>0; --i) {
            heap.add(i);
        }
        System.out.println(Arrays.toString(((BinaryHeap<Integer>) heap).elements));
        System.out.println(heap.size());

        BinaryTrees.print(heap);
        System.out.println("");

        /*for (int i=0, size=heap.size(); i<size; i++) {
            System.out.println(heap.remove());
        }*/

        heap.replace(100);
        BinaryTrees.print(heap);
        System.out.println("");

    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        if (node == null) return null;
        int index = left((int) node);
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        if (node == null) return null;
        int index = right((int) node);
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int)node];
    }
}
