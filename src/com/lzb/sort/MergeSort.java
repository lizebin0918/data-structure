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
     * @param leftPrt 左指针
     * @param rightPrt 右指针
     * @param rightBound 右指针边界
     * @param newArray 新数组
     */
    private void merge(int[] array, int leftPrt, int rightPrt, int rightBound, int[] newArray) {
        for (int i=leftPrt, j=rightPrt; i<j; i++) {
            if (array[i] < array[rightPrt]) {
                newArray[i] = array[i];
                leftPrt++;
            } else {
                newArray[rightPrt] = array[rightPrt];
                rightPrt++;
            }
        }
        while (leftPrt <= rightPrt) {
            newArray[leftPrt] = array[leftPrt];
            leftPrt++;
        }
        while (rightPrt <= rightBound) {
            newArray[rightPrt] = array[rightPrt];
            rightPrt++;
        }
    }
}
