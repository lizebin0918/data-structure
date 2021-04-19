package com.lzb.mixed;

import java.util.*;

/**
 * 给定一个整数和整数池，寻找到两个数相加等于目标整数的索引<br/>
 * Created on : 2021-04-19 19:30
 * @author lizebin
 */
public class FindNumber {

    private static final int TARGET = 88;

    public static void main(String[] args) {
        int size = 1000;
        int[] array = new int[size];
        Random random = new Random();
        for (int i=0; i<size; i++) {
            array[i] = random.nextInt(60);
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int v : array) {
            int i = TARGET - v;
            if (map.containsValue(i)) {
                System.out.println(i + "," + v);
            } else {
                map.put(i, v);
            }
        }
    }

}
