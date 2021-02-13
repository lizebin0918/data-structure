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
public abstract class Sort {

    private final int[] testArray;

    public int[] getTestArray() {
        return testArray;
    }

    private final int length = 100;

    {
        testArray = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            //随机生成小于100的正整数
            testArray[i] = random.nextInt(100);
        }
    }

    public abstract int[] sort(int[] array);

    public void test() {
        System.out.println("input array : " + Arrays.toString(this.testArray));
        Arrays.sort(testArray);
        int[] newArray = Arrays.copyOf(testArray, length);
        int[] sortArray = sort(newArray);
        System.out.println(Arrays.toString(sortArray));
        System.out.println("result " + Arrays.equals(testArray, sortArray));
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        /*System.out.println("source array:" + Arrays.toString(testArray));
        quickSortRecursion(Arrays.copyOf(testArray, testArray.length));
        insertionSort(Arrays.copyOf(testArray, testArray.length));
        heapSort(Arrays.copyOf(testArray, testArray.length));*/
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
