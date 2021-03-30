package com.lzb.sort;

import java.util.*;

/**
 * 定长字符串的基数排序<br/>
 * Created on : 2021-03-30 10:39
 * @author chenpi 
 */
public class RadixSortForString {

    public static void main(String[] args) {
        List<String> sourceList = Arrays.asList("11", "22", "33", "55", "99", "66", "77");
        List<String> list1 = new ArrayList<>(sourceList);
        List<String> list2 = new ArrayList<>(sourceList);
        list1.sort(Comparator.naturalOrder());
        System.out.println(list1);
        list2 = sort(list2, list2.get(0).length());
        System.out.println(list2);
    }

    public static List<String> sort(List<String> list, int length) {
        //check
        for (String s : list) {
            if (s == null || s.equals("") || s.length() != length) {
                throw new IllegalArgumentException("参数不合法");
            }
        }

        //低位优先
        for (int i=length-1; i>=0; i--) {
            //bucket 不包含中文
            int[] buckets = new int[10];

            for (String s : list) {
                char[] chars = s.toCharArray();
                int index = Integer.valueOf(String.valueOf(chars[i]));
                System.out.println(index);
                buckets[index]++;
            }
            System.out.println("compare:" + Arrays.toString(buckets));

            //计算位置，对应位置的数字有几个
            int lastIndex = 0;
            for (int j=0; j<buckets.length; j++) {
                int count = buckets[j];
                if (count > 0) {
                    lastIndex += count;
                    buckets[j] = lastIndex - 1;
                }
            }
            System.out.println("cal index:" + Arrays.toString(buckets));
        }
        return null;
    }

}
