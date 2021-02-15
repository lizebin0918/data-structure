package com.lzb.sort;
/**
 * 快速排序<br/>
 * Created on : 2021-02-15 12:23
 * @author lizebin
 */
public class QuickSort extends Sort {

    @Override
    public int[] sort(int[] array) {
        quickSort(array, 0, array.length - 1);
        return array;
    }

    /**
     * 单轴快排:以最左边第一个元素为轴--array[start]
     * 1.左指针:left=start，一直left++找到第一个比轴大的数，停下
     * 2.右指针:right=array.length-1，一直right--找到第一个比轴小的数，停下
     * 3.这个时候i和j的位置互换，直到i和j相遇，此位置与轴互换，此时轴在中间，小于的数在轴的左边，大于的数在轴的右边。
     * 同理排序剩余的分区
     * @param arr
     * @param start
     * @param end
     */
    public static void quickSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int left = start, right = end;
        while(left < right) {
            //注意：右边是大于
            if (arr[right] > arr[start]) {
                right--;
                continue;
            }
            //注意：左边是小于或等于
            if (arr[left] <= arr[start]) {
                left++;
                continue;
            }
            swap(arr, left, right);
        }

        swap(arr, start, left);

        //递归
        quickSort(arr, start, left - 1);
        quickSort(arr, left + 1, end);
    }

    public static void main(String[] args) {
        new QuickSort().test();
    }
}
