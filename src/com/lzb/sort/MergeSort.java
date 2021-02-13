package com.lzb.sort;

import java.util.Arrays;

/**
 * 归并排序<br/>
 * Created on : 2021-02-13 15:51
 * @author chenpi 
 */
public class MergeSort extends Sort {

    @Override
    public int[] sort(int[] array) {
        int[] testArray = this.getTestArray();
        System.out.println(Arrays.toString(testArray));
        int[] newArray = new int[testArray.length];
        //test
        sort(testArray, 0, testArray.length - 1, newArray);
        return newArray;
    }

    public static void main(String[] args) {
        MergeSort sort = new MergeSort();
        sort.test();
    }

    /**
     * 分区
     * @param array
     * @param start 数组起始索引
     * @param end 数组结束索引
     */
    private void sort(int[] array, int start, int end, int[] newArray) {
        if (start >= end) {
            return;
        }
        int mid = (end + start) / 2;
        sort(array, start, mid, newArray);
        sort(array, mid + 1, end, newArray);
        merge(array, start, mid, end, newArray);
    }

    /**
     * 合并
     * @param array 原数组
     * @param start
     * @param mid
     * @param end
     * @param newArray 新数组
     */
    private void merge(int[] array, int start, int mid, int end, int[] newArray) {
        for (int i=start, j=mid; i<j; i++) {
            if (array[i] < array[mid]) {
                newArray[i] = array[i];
                start++;
            } else {
                newArray[mid] = array[mid];
                mid++;
            }
        }
        while (start <= mid) {
            newArray[start] = array[start];
            start++;
        }
        while (mid <= end) {
            newArray[mid] = array[mid];
            mid++;
        }
    }
}
