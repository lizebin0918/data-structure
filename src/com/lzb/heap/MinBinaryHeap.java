package com.lzb.heap;

import com.lzb.heap.itf.Heap;
import com.lzb.utils.printer.BinaryTreeInfo;

import java.util.*;

/**
 * 最小二叉堆实现（可通过compare()实现最大二叉堆）<br/>
 *
 * 符合完全二叉树性质
 * 父节点索引:(index + 1) >> 1 - 1
 * 左孩子:(index << 1) + 1
 * 右孩子:(index + 1) << 1
 * 最后一个非叶子节点索引(n:数组长度):(n + 1)/2 - 1
 * 在当前的这个实现代码中，根据 compare() 的结果找出"更小"的元素放在树顶，怎么通过判断"更小"，就是 compare() 自己的判断逻辑。但是这个更小的元素实际的值更大，这样有可能会构建一个最大堆
 *
 * 向上堆化-heapyUp(index):元素一直跟parent比较，直到找到对应的位置
 * 向下堆化-heapyDown(index):元素跟更小的子节点比较，如果比它大则交互位置，一直到底
 *
 * 构建堆的方法：
 * 1.自上而下的heapfyUp()，从 1....n 个元素遍历一直heapfyUp()
 * 2.自下而上的heapfyDown()，从 最后一个非叶子-n, --n 个元素遍历一直heapfyDown()
 *
 * Created on : 2021-02-18 23:14
 * @author lizebin
 */
public class MinBinaryHeap<E> implements Heap<E>, BinaryTreeInfo {

    private E[] elements;
    private int size;
    private Comparator<E> comparator;

    private static final int CAPATITY = 16;

    public MinBinaryHeap() {
        this(null, null);
    }

    public MinBinaryHeap(Comparator<E> comparator) {
        this(null, comparator);
    }

    public MinBinaryHeap(E[] elements, Comparator<E> comparator) {
        this.comparator = comparator;
        if (elements == null) {
            this.elements = (E[]) new Object[CAPATITY];
        } else {
            int capacity = Math.max(elements.length, CAPATITY);
            size = elements.length;
            this.elements = Arrays.copyOf(elements, elements.length);
            heapfy();
        }

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
        if (isEmpty()) {
            elements[0] = element;
            ++size;
            return null;
        }
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
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     * 向上堆化:元素一直跟parent比较，直到找到对应的位置
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
            if (compareTo(pe, e) <= 0) {
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
     * 向下堆化:元素跟更小的子节点比较，如果比它大则交互位置，一直到底
     * @param index
     */
    private void heapifyDown(int index) {
        int sci = smallerChild(index);
        int current = index;
        E e = elements[current];
        while (sci > 0) {
            E smaller = elements[sci];
            if (compareTo(smaller, e) > 0) {
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
        return compareTo(left, right) < 0 ? li : ri;
    }

    public int compareTo(E e1, E e2) {
        if (comparator == null) {
            return ((Comparable<E>)e1).compareTo(e2);
        } else {
            return comparator.compare(e1, e2);
        }
    }

    /**
     * 构建最小(大)堆
     */
    public void heapfy() {
        int lastIndex = ((size + 1) >> 1) - 1;
        //构建最小堆
        while (lastIndex >= 0) {
            heapifyDown(lastIndex--);
        }
    }

    /*------------------------------------*/

    public static void main(String[] args) {

        /*MinBinaryHeap<Integer> heap = new MinBinaryHeap<>();
        for (int i=20; i>0; --i) {
            heap.add(i);
        }
        System.out.println(Arrays.toString(((MinBinaryHeap<Integer>) heap).elements));
        System.out.println(heap.size());

        BinaryTrees.print(heap);
        System.out.println("");

        *//*for (int i=0, size=heap.size(); i<size; i++) {
            System.out.println(heap.remove());
        }*//*

        heap.replace(100);
        BinaryTrees.print(heap);
        System.out.println("");

        Integer[] elements = new Integer[]{5, 3, 4, 2, 1, 0, 6};
        MinBinaryHeap<Integer> h1 = new MinBinaryHeap<>(elements, Comparator.<Integer>naturalOrder().reversed());
        BinaryTrees.print(h1);
        System.out.println("");*/

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        System.out.println(map.entrySet().size());

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
