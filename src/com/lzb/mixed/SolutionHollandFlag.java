package com.lzb.mixed;

import java.util.Arrays;

/**
 * 荷兰国旗问题<br/>
 * 一个数组由0,1,2组成，最终把数组排序成:0....1.....2
 * Created on : 2021-02-15 15:35
 * @author lizebin
 */
public class SolutionHollandFlag {

    public static void main(String[] args) {
        int[] array = {0, 2, 2, 1, 1, 0, 1, 2, 0};
        solution(array);
        System.out.println(Arrays.toString(array));
    }

    private static void solution(int[] array) {
        int begin = -1, end = array.length, bound = array.length - 1;
        for (int current = 0; current >=0 && current < bound; current++) {
            if (array[current] == 0 && current > begin) {
                swap(array, ++begin, current);
                //冗余：因为前面的数已经遍历过了，前面换上来的是1;
                //current--;
                continue;
            }
            if (array[current] == 2 && current < end) {
                swap(array, --end, current);
                //a[--end]和a[current]位置互换，需要再比较一次
                current--;
                continue;
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        if (i == j) return;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
