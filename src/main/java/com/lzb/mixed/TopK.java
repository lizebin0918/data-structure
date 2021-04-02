package com.lzb.mixed;

import com.lzb.tree.heap.MinBinaryHeap;
import com.lzb.util.printer.BinaryTrees;

import java.util.Arrays;
import java.util.Random;

/**
 * 前最大的k个数<br/>
 *
 * 时间复杂度:nlogk
 * 维护一个只有k个元素的最小堆
 * 遍历所有元素，只要发现比堆顶大就替换
 *
 * Created on : 2021-02-21 22:44
 * @author lizebin
 */
public class TopK {

    public static void main(String[] args) {
        int size = 100;
        int[] array = new int[size];
        Random random = new Random();
        for (int i=0; i<size; i++) {
            array[i] = random.nextInt(100);
        }

        System.out.println("input array:" + Arrays.toString(array));

        int top = 5;

        MinBinaryHeap<Integer> heap = new MinBinaryHeap<>();
        for (int i = 0, length = array.length; i < length; i++) {
            int v = array[i];
            if (heap.size() < top) {
                heap.add(v);
            } else if (heap.get() < v) {
                heap.replace(v);
            }
        }
        BinaryTrees.print(heap);
        System.out.println("");
    }


}
