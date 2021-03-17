package com.lzb.sort;

import java.util.Arrays;

/**
 * 桶排序<br/>
 * 桶排序:生成一维数组，下标表示对应的值A，元素值B表示值A出现的次数
 * 限制:如果值未知，则很难估算"桶"的个数
 * Created on : 2021-02-13 14:47
 * @author chenpi 
 */
public class BucketSort extends Sort {

    private static final int[] BUCKETS = new int[100];

    @Override
    public int[] sort(int[] array) {
        //由题目可知，
        for (int i = 0, length = array.length; i < length; i++) {
            BUCKETS[array[i]] += 1;
        }
        //回写
        int index = 0;
        for (int i = 0, length = BUCKETS.length; i < length; i++) {
            int bucketValue = BUCKETS[i];
            if (bucketValue > 0) {
                for (int j = index; j < bucketValue + index; j++) {
                    array[j] = i;
                }
                index += bucketValue;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        new BucketSort().test();
    }
    
}
