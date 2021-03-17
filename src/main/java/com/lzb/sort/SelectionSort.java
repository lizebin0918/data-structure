package com.lzb.sort;

import java.util.Arrays;

/**
 * 选择排序:
 * 选择第一个元素看作最小值，依次跟后续元素比较找到最小（大）值；后面的元素依次类推<br/>
 *
 * 理解：从第0个元素开始，该索引视为最小索引，递增跟后面的数比较，只要发现比后面的数大，则最小索引改成对应索引值，遍历完成把0元素和最小索引元素交互。后面同理
 * Created on : 2021-02-12 17:09
 * @author chenpi 
 */
public class SelectionSort extends Sort {

    @Override
    public int[] sort(int[] array) {
        for (int i = 0, length = array.length; i < length; i++) {
            int minIndex = i;
            for (int j = minIndex + 1; j < length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        return array;
    }

    public static void main(String[] args) {
        new SelectionSort().test();
    }

}
