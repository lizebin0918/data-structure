package com.lzb.sort;

import java.util.Arrays;

/**
 * 选择排序<br/>
 * Created on : 2021-02-12 17:09
 * @author chenpi 
 */
public class SelectionSort {

    public static void main(String[] args) {

    }

    /**
     * 选择排序
     *
     * @param array
     */
    public static void selectionSort(int[] array) {
        for (int i = 0, length = array.length; i < length; i++) {
            int minIndex = i;
            for (int j = minIndex + 1; j < length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        System.out.println("selection sorted array:" + Arrays.toString(array));
    }

}
