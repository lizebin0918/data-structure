package com.lzb.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 堆排序<br/>
 * 1.构建最大堆或者最小堆:从最后一个非叶子节点开始(array.length/2 - 1)，从右到左，从下往上遍历，直到根节点
 * 2.根节点和末尾元素交换
 * 3.因为只有根元素不符合堆要求，根节点开始，从上往下构建最大堆即可，本来已经是最大堆的子树不用变化
 *
 * Created on : 2021-01-04 21:28
 * @author lizebin
 */
public class MyHeapSort {

    public static void main(String[] args) {
        int size = 100;
        int[] array = new int[size];
        int[] sortedArray = new int[size];
        Random random = new Random();
        for (int i=0; i<size; i++) {
            int v = random.nextInt(1000);
            array[i] = v;
            sortedArray[i] = v;
        }
        int[] array1 = {4, 10, 3, 5, 1, 2, 9, 6, 7, 8};
        heapSort(array1, 0, array1.length - 1);
        //Arrays.sort(sortedArray);
        //System.out.println(Arrays.equals(array, sortedArray));
        System.out.println(Arrays.toString(array1));
    }

    public static void heapSort(int[] array, int start, int end) {
        //构建大顶堆
        //最后一个非叶子节点:left > 0:递减遍历
        for (int left = (end - start + 1) / 2 - 1; left >= 0; --left) {
            heapfyMax(array, left, end - start + 1);
        }

        System.out.println("---------------------");

        while(end > 0) {
            swap(array, start, end);
            heapfyMax(array, start, end);
            --end;
        }
    }

    /**
     * 构建最大堆
     * @param array
     * @param index
     * @param length
     */
    public static void heapfyMax(int[] array, int index, int length) {
        System.out.println("before heapfyMax:" + Arrays.toString(array));
        System.out.println("index = " + index);
        if (index > length) {
            return;
        }
        int c1 = index * 2 + 1, c2 = index * 2 + 2;
        int max = index;
        if (c1 < length && array[index] < array[c1]) {
            max = c1;
        }
        if (c2 < length && array[max] < array[c2]) {
            max = c2;
        }
        if (index != max) {
            swap(array, index, max);
            heapfyMax(array, max, length);
        }
        System.out.println("heapfyMax:" + Arrays.toString(array));
    }

    /*public static void heapfyMax(int[] array, int index, int length) {
        int c1 = index * 2 + 1, c2 = index * 2 + 2;
        if (c1 > length - 1) {
            return;
        }
        int max = index;
        if (array[index] < array[c1]) {
            max = c1;
        }
        if (c2 > length - 1) {
            swap(array, index, max);
            return;
        }
        if (array[max] < array[c2]) {
            max = c2;
        }
        if (index != max) {
            swap(array, index, max);
        }
    }*/

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
