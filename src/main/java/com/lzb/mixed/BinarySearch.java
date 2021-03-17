package com.lzb.mixed;

/**
 * <br/>
 * Created on : 2017-02-11 13:31
 * @author lizebin
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] array = {4, 6, 13, 13, 14, 16, 22};
        System.out.println(binarySearchRecursion(array, 0, array.length - 1, 22));
        System.out.println(binarySearchNotRecursion(array, 6));
    }

    /**
     * 二分查找返回索引(递归版)
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchRecursion(int[] array, int start, int end, int target) {
        if(start > end) {
            return -1;
        }
        int middle = (start + end) / 2;
        if (target < array[middle]) {
            return binarySearchRecursion(array, start, middle - 1, target);
        }
        if(target > array[middle]) {
            return binarySearchRecursion(array, middle + 1, end, target);
        }
        return middle;
    }

    /**
     * 二分查找（非递归）
     * @param array
     * @param target
     * @return
     */
    public static int binarySearchNotRecursion(int[] array, int target) {
        int start = 0, end = array.length - 1;
        while(end >= start) {
            int middle = (start + end) / 2;
            if(target > array[middle]) {
                start = middle + 1;
            }
            if(target < array[middle]) {
                end = middle - 1;
            }
            if(target == array[middle]) {
                return middle;
            }
        }
        return -1;
    }
}
