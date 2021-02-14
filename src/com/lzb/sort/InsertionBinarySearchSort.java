package com.lzb.sort;

/**
 * 插入排序(还可以写成二分插入)：从第0个元素开始，往后递增，再往前比较有序的"子数组"元素，只要发现比前面的元素小，进行替换<br/>
 * Created on : 2021-02-14 12:45
 *
 * @author lizebin
 */
public class InsertionBinarySearchSort extends Sort {


    @Override
    public int[] sort(int[] array) {
        //插入排序(还可以写成二分插入)：从第0个元素开始，递增往前排好序的"子数组"比较，只要发现比前面的元素小，进行替换
        int start = 0;
        for (int j = 1; j < array.length; j++) {
            int index = binarySearch(array, start, j, array[j]);
            //临时变量存值
            int value = array[j];
            int length = j - index;
            System.arraycopy(array, index, array, index + 1, length);
            array[index] = value;
        }
        return array;
    }

    /**
     * 二分查找对应位置
     *
     * @param array
     * @param start
     * @param end
     * @param value 目标值
     * @return
     */
    private static int binarySearch(int[] array, int start, int end, int value) {
        if (start == end) {
            if (array[start] > value) {
                return start > 0 ? start - 1 : start;
            } else {
                return start + 1;
            }
        }
        if (value < array[start]) {
            return start;
        }
        if (value > array[end]) {
            return end;
        }
        //end>=start包括最右的一个数
        for (int mid = (start + (end - start) / 2); end >= start; mid = (start + (end - start) / 2)) {
            if (array[mid] > value) {
                end = mid - 1;
            } else if (array[mid] < value) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        new InsertionBinarySearchSort().test();
    }
}
