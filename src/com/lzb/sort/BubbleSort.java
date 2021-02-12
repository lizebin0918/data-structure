package com.lzb.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 冒泡算法:比较相邻的元素。如果第一个比第二个大，就交换他们两个。
 * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。后续如此类推<br/>
 * Created on : 2021-02-12 14:14
 * @author chenpi 
 */
public class BubbleSort extends Sort {

    @Override
    public int[] sort(int[] array) {
        /*
        //只是单纯的比较交换:第一个元素跟后面的元素比较，每次把较大值保存到第一个元素，后续如此类推知道末尾
        for (int i = 0, ilength = array.length; i < ilength; i++) {
            for (int j = i + 1; j < ilength; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }*/
        for (int i = 0, ilength = array.length; i < ilength; i++) {
            for (int j = 0; j < ilength - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    public static void main(String[] args) {
        new BubbleSort().test();
    }
}
