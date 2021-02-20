package com.lzb.sort;

import java.util.Arrays;

/**
 * 堆排序<br/>
 *
 * 最后一个非叶子节点索引(n:数组长度) index = (n + 1)/2 - 1
 * 自下而上heapyDown() 构建最大（小）堆
 * 最后一个元素替换第一个元素，重复heapyDown(0)，直到没有元素
 *
 * Created on : 2021-02-21 00:12
 * @author lizebin
 */
public class MyHeapSort extends Sort {

    public static void main(String[] args) {
        new MyHeapSort().test();
    }

    @Override
    public int[] sort(int[] array) {
        int length = array.length;
        int lastIndex = ((array.length + 1) >> 1) - 1;
        //构建最小堆
        while (lastIndex >= 0) {
            heapifyDown(lastIndex--, array, length);
        }
        int[] newArray = new int[length];
        for (int i=0; length>0; i++) {
            newArray[i] = array[0];
            array[0] = array[--length];
            heapifyDown(0, array, length);
        }
        return newArray;
    }

    /**
     * 向下堆化
     *
     * @param index
     */
    private void heapifyDown(int index, int[] array, int length) {
        int sci = smallerChild(index, array, length);
        int current = index;
        int e = array[current];
        while (sci > 0) {
            int smaller = array[sci];
            if (smaller > e) {
                break;
            }
            array[current] = smaller;
            current = sci;
            sci = smallerChild(current, array, length);
        }

        if (current != index) {
            array[current] = e;
        }
    }

    /**
     * 父亲节点索引
     *
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
     *
     * @param i
     * @return
     */
    private int left(int i) {
        return (i << 1) + 1;
    }

    /**
     * 右孩子索引
     *
     * @param i
     * @return
     */
    private int right(int i) {
        return (i + 1) << 1;
    }

    /**
     * 最后一个非叶子节点索引
     *
     * @param size
     * @return
     */
    private int lastParent(int size) {
        return ((size + 1) >> 1) - 1;
    }

    /**
     * 返回更大值的孩子
     *
     * @param i
     * @param array
     * @param length
     * @return
     */
    public int smallerChild(int i, int[] array, int length) {
        int li = left(i), ri = right(i);
        if (li >= length) {
            return -1;
        }
        if (ri >= length) {
            return li;
        }
        int left = array[li];
        int right = array[ri];
        return left < right ? li : ri;
    }
}