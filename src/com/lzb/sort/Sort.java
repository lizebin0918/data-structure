package com.lzb.sort;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;

/**
 * <br/>
 * Created on : 2017-01-30 10:50
 *
 * @author lizebin
 */
public class Sort {

    private static final int[] ARRAY;

    static {
        int length = 10;
        ARRAY = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            //随机生成小于100的正整数
            ARRAY[i] = random.nextInt(100);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("source array:" + Arrays.toString(ARRAY));
        bucketSort(Arrays.copyOf(ARRAY, ARRAY.length));
        bubbleSort(Arrays.copyOf(ARRAY, ARRAY.length));
        selectionSort(Arrays.copyOf(ARRAY, ARRAY.length));
        quickSortRecursion(Arrays.copyOf(ARRAY, ARRAY.length));
        insertionSort(Arrays.copyOf(ARRAY, ARRAY.length));
        heapSort(Arrays.copyOf(ARRAY, ARRAY.length));
    }

    /**
     * 桶排序:生成一维数组，下标表示对应的值A，元素值B表示值A出现的次数
     * 限制:如果值未知，则很难估算"桶"的个数
     *
     * @param array
     */
    public static void bucketSort(int[] array) {
        //由题目可知，桶最大数为100
        int[] buckets = new int[100];
        for (int i = 0, length = array.length; i < length; i++) {
            buckets[array[i]] += 1;
        }
        //回写
        int index = 0;
        for (int i = 0, length = buckets.length; i < length; i++) {
            int bucketValue = buckets[i];
            if (bucketValue > 0) {
                for (int j = index; j < bucketValue + index; j++) {
                    array[j] = i;
                }
                index += bucketValue;
            }
        }
        System.out.println("bucket sorted array:" + Arrays.toString(array));
    }

    /**
     * 冒泡排序（只是单纯的交互算法）
     *
     * @param array
     */
    public static void bubbleSort(int[] array) {
        /*
        //只是单纯的比较交换
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
        System.out.println("bubble sorted array:" + Arrays.toString(array));
    }

    /**
     * 选择排序
     *
     * @param array
     */
    public static void selectionSort(int[] array) {
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
        System.out.println("selection sorted array:" + Arrays.toString(array));
    }

    /**
     * 快速排序(递归)
     *
     * @param array
     */
    public static void quickSortRecursion(int[] array) {
        quickSortPartion(array, 0, array.length - 1);
        System.out.println("quick sorted recursion array:" + Arrays.toString(array));
    }

    /**
     * 递归分区
     *
     * @param array
     */
    public static void quickSortPartion(int[] array, int start, int end) {
        if (start < end) {
            int i = quickSortAdjust(array, start, end);
            quickSortPartion(array, 0, i - 1);
            quickSortPartion(array, i + 1, end);
        }
    }

    /**
     * 以第一个数为基准，小于基数的数移到左边，大于基数的移动到右边
     *
     * @param array
     * @return 返回对应基数索引位置
     */
    public static int quickSortAdjust(int[] array, int start, int end) {
        int i = start, x = array[i], j = end;
        while (i != j) {
            for (; j > i; j--) {
                if (array[j] < x) {
                    array[i] = array[j];
                    i++;
                    break;
                }
            }
            for (; i < j; i++) {
                if (array[i] > x) {
                    array[j] = array[i];
                    j--;
                    break;
                }
            }
        }
        array[i] = x;
        return i;
    }

    /**
     * 插入排序
     *
     * @param array
     */
    public static void insertionSort(int[] array) {
        //把第一个元素看成有序集，向后遍历，插入元素
        for (int i = 1, length = array.length; i < length; i++) {
            //相当于在i位置挖一个坑
            int temp = array[i];
            for (int j = 0; j < i; j++) {
                //1.元素向后平移
                /*if(array[j] > array[i]) {
                    int temp = array[i];
                    System.arraycopy(array, j, array, j + 1, i - j);
                    array[j] = temp;
                }*/
                //2.元素一直swap
                if (array[j] > temp) {
                    temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
        System.out.println("insertion sort array:" + Arrays.toString(array));
    }


    /**
     * 堆排序
     *
     * @param array
     */
    public static void heapSort(int[] array) {
        int length = array.length;
        while(length > 0) {
            maxHeapfy(array, length);
            swap(array, 0, --length);
        }
        System.out.println("heap sort array:" + Arrays.toString(array));
    }

    /**
     * 最大堆插入，插入的元素放在末尾，根据堆的特性，一直与父节点比较交换即可
     *
     * @param a         堆化的数组
     * @param insertNum
     */
    private static int[] insertMaxHeap(int[] a, int insertNum) {
        int length = a.length;
        int[] newArray = Arrays.copyOf(a, length + 1);
        newArray[length] = insertNum;
        int i = newArray.length - 1;
        int temp = insertNum;
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (parent >= 0 && a[parent] < temp) {
                swap(newArray, i, parent);
                i = parent;
            } else {
                break;
            }
        }
        return newArray;
    }

    /**
     * 自底向上遍历，从最后一层非叶子节点开始
     *
     * @param a
     * @param parent
     */
    private static void maxHeapfy(int[] a, int length) {
        int maxIndex = length - 1;
        for (int parent = maxIndex / 2; parent >= 0; parent--) {
            for (int i = parent; i >= 0; i--) {
                int max = i;
                int l = i * 2 + 1;
                int r = i * 2 + 2;
                if (l <= maxIndex && a[max] < a[l]) {
                    max = l;
                }
                if (r <= maxIndex && a[max] < a[r]) {
                    max = r;
                }
                if (i != max) {
                    swap(a, i, max);
                    //位置交换之后,有可能max的子节点会比i的大，所以需要重新遍历
                    i = max + 1;
                }
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}