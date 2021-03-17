package com.lzb.sort;
/**
 * 二路双插入排序<br/>
 * 先从较小数开始插入，再从前(较大数的位置)往后遍历插入较大数。
 * Created on : 2021-02-14 22:48
 * @author lizebin
 */
public class InsertionWithDualSort extends Sort {

    @Override
    public int[] sort(int[] array) {
        for (int i1 = 1, i2 = i1 + 1; i1 < array.length; i1 = i2 + 1, i2 = i1 + 1) {
            //数组长度为偶数，末尾元素 a[i] ,前面的元素已经有序，直接插入排序
            if (i2 >= array.length) {
                int v = array[i1];
                while (--i1 >= 0 && array[i1] > v) {
                    array[i1 + 1] = array[i1];
                }
                array[i1 + 1] = v;
                break;
            }

            int v1 = array[i1], v2 = array[i2];

            //先找出较小数
            if (v1 > v2) {
                v1 = v2;
                v2 = array[i1];
            }

            //较小数-v1 插入到对应位置，区间为[0, i]
            while(--i1 >= 0 && array[i1] > v1) {
                array[i1 + 1] = array[i1];
            }
            array[i1 + 1] = v1;

            //较大数-v2 从i+1开始，插入到对应位置，区间为[i + 1, i2 - 1]
            int start = i2;
            while(--start >= (i1 + 2) && array[start] > v2) {
                array[start + 1] = array[start];
            }
            array[start + 1] = v2;
        }
        return array;
    }

    public static void main(String[] args) {
        new InsertionWithDualSort().test();
    }
}
