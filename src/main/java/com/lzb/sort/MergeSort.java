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
        int[] newArray = new int[array.length];
        //test
        sort(array, 0, array.length - 1, newArray);
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
        merge(array, start, mid + 1, end, newArray);
    }

    /**
     * 合并
     * @param array 原数组
     * @param leftPt 左指针
     * @param rightPt 右指针
     * @param bound 右指针边界
     * @param newArray 新数组
     */
    public void merge(int[] array, int leftPt, int rightPt, int bound, int[] newArray) {
        int _leftPt = leftPt, _rightPt = rightPt;
        int leftEnd = _rightPt;
        int index = _leftPt;
        while (_leftPt < rightPt && _rightPt <= bound) {
            if (array[_leftPt] < array[_rightPt]) {
                newArray[index++] = array[_leftPt++];
            } else {
                newArray[index++] = array[_rightPt++];
            }
        }
        while (_leftPt < leftEnd) {
            newArray[index++] = array[_leftPt];
            _leftPt++;
        }
        while (_rightPt <= bound) {
            newArray[index++] = array[_rightPt];
            _rightPt++;
        }
        //复制元素
        while(leftPt <= bound){
            array[leftPt] = newArray[leftPt];
            leftPt++;
        }
    }
}
