package com.lzb.sort;

import java.util.*;

/**
 * 定长字符串的基数排序<br/>
 * Created on : 2021-03-30 10:39
 * @author chenpi 
 */
public class RadixSortForString {

    public static void main(String[] args) {
        List<String> sourceList = Arrays.asList("919a", "b820", "c731", "e551", "d99f", "k664", "l477", "zfsz", "aabb", "ccdd");
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
            int[] buckets = new int[126];

            for (String s : list) {
                char[] chars = s.toCharArray();
                int bucket = (int)chars[i];
                buckets[bucket] = buckets[bucket] + 1;
            }

            //计算位置，对应位置的数字有几个
            int lastIndex = 0;
            for (int j=0; j<buckets.length; j++) {
                int count = buckets[j];
                if (count > 0) {
                    lastIndex += count;
                    buckets[j] = lastIndex - 1;
                }
            }

            //遍历桶元素，从后往前遍历，保持上次排序的顺序
            String[] array = new String[list.size()];
            for (int j=list.size()-1; j>=0; j--) {
                String s = list.get(j);
                char[] chars = s.toCharArray();
                int bucket = (int)chars[i];
                int index = buckets[bucket];
                array[index] = s;
                buckets[bucket] = index - 1;
            }
            list = Arrays.asList(array);
        }
        return list;
    }

}
