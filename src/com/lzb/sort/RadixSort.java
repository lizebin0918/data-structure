package com.lzb.sort;

import java.util.Arrays;
import java.util.Objects;

/**
 * 基数排序(低位优先):是一种非比较型整数排序算法，其原理是将整数按位数切割成不同的数字，然后按每个位数分别比较。
 * 由于整数也可以表达字符串（比如名字或日期）和特定格式的浮点数，所以基数排序也不是只能使用于整数
 *
 * 前提：必须是正整数
 *
 * 计数排序的累计技巧:桶的索引值记录的是个数
 * 第一轮：最低位比较（个位）、第二轮：次最低位比较（十位）、第三轮：.....一直到最高位比较，最终结果就是排好序的数组
 * Created on : 2021-02-15 13:36
 * @author lizebin
 */
public class RadixSort extends Sort {

    public static void main(String[] args) {
        new RadixSort().test();
    }

    @Override
    public int[] sort(int[] array) {
        //找到数组最大值
        int max = array[0];
        for (int i=1; i<array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        int length = Objects.toString(max).length();

        //个位、十位、百位、千位遍历...
        for (int i = 0; i <= length ; i++) {
            int[] bucket = new int[10];
            int unit = (int) Math.pow(10, i);

            //bucket 记录个数
            for (int item : array) {
                //对应位的值
                int value = getNumberInUnit(item, unit);
                //对应位的值 + 1
                ++bucket[value];
            }

            //bucket 根据记录个数 计算出 对应数字的索引值
            int lastIndex = 0;
            for (int j=0; j<bucket.length; j++) {
                int count = bucket[j];
                if (count > 0) {
                    lastIndex += count;
                    bucket[j] = lastIndex - 1;
                }
            }

            int[] newArray = new int[array.length];
            //从后往前遍历（不能从前往后，因为桶里面的元素实际是有序的），保证了原来的相对顺序
            for (int j=array.length - 1; j>=0; j--) {
                int value = array[j] / unit % 10;
                newArray[bucket[value]--] = array[j];
            }
            System.arraycopy(newArray,0, array, 0, newArray.length);
        }
        return array;
    }

    /**
     * 获取对应单位的数字
     * @param number 数字
     * @param unit 单位 1:个位;10:十位;100:百位....
     * @return
     */
    private int getNumberInUnit(int number, int unit) {
        return number / unit % 10;
    }
}
