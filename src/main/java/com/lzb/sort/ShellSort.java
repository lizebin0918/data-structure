package com.lzb.sort;

import java.util.Arrays;

/**
 * 希尔排序<br/>
 *
 * 有间隔的插入排序，间隔逐渐缩写
 * 这个间隔的意思是：gap==4，从第4个元素跟第0个元素比较，再从第5个元素跟第1个元素比较，一轮过后，gap==2，用第2个元素和第0个元素比较，第3个元素和第1个元素比较，直到 gap/2 == 0 结束.....并不是先取出索引等于0,4,8的元素来比较
 *
 * 简单插入排序很循规蹈矩，不管数组分布是怎么样的，依然一步一步的对元素进行比较，移动，插入，比如[5,4,3,2,1,0]这种倒序序列，
 * 数组末端的0要回到首位置很是费劲，比较和移动元素均需n-1次。而希尔排序在数组中采用跳跃式分组的策略，通过某个增量将数组元素划分为若干组，
 * 然后分组进行插入排序，随后逐步缩小增量，继续按组进行插入排序操作，直至增量为1。
 *
 * Created on : 2021-02-14 22:01
 * @author lizebin
 */
public class ShellSort extends Sort {

    @Override
    public int[] sort(int[] array) {
        int gap = 4;
        //按gap分组
        //i=0;next=4
        //i=1;next=5
        //i=2;next=6
        //i=3;next=7
        //i=4;next=8
        //i=5;next=9
        //i=6;next=10
        //....
        while (gap >= 1) {
            for (int i = 0, next = i + gap; next < array.length; i++, next = i + gap) {
                //根据分组从后往前比较:(0,3);(3,6);(6,9).....next = next - gap + 1 直到等于i
                while (next >= gap) {
                    if (array[next] >= array[next - gap]) {
                        break;
                    }
                    swap(array, next, next - gap);
                    next = next - gap;
                }
            }
            gap = gap / 2;
        }
        return array;
    }

    public static void main(String[] args) {
        new ShellSort().test();
    }
}
