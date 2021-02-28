package com.lzb.sort;

import java.util.Arrays;

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
            int index = binarySearch(array, start, j, j);
            //临时变量存值
            int value = array[j];
            int length = j - index;
            System.arraycopy(array, index, array, index + 1, length);
            array[index] = value;
        }
        return array;
    }

    /**
     * 二分查找对应位置:第一个大于value的元素位置
     *
     * 例如:
     * 数组={8, 8, 8, 8, 8, 8, 8, 8, 8, 10}，value=8，应该返回索引=9
     * 数组={7, 9, 10}，value=9，应该返回索引=1
     *
     * @param array
     * @param start
     * @param end
     * @param index 目标索引
     * @return
     */
    private static int binarySearch(int[] array, int start, int end, int index) {
        if (start == end) {
            if (array[start] > array[index]) {
                return start > 0 ? start - 1 : start;
            } else {
                return start + 1;
            }
        }
        if (array[index] < array[start]) {
            return start;
        }
        if (array[index] > array[end]) {
            return end;
        }
        //end>=start包括最右的一个数
        for (int mid = (start + (end - start) / 2); start < end; mid = (start + (end - start) / 2)) {
            if (array[mid] > array[index]) {
                end = mid;
            }
            //array[index] >= array[mid]，一直往右走
            else {
                start = mid + 1;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        new InsertionBinarySearchSort().test();
    }
}
