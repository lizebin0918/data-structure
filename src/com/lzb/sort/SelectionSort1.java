package com.lzb.sort;

import java.util.Arrays;
import java.util.Random;

public class SelectionSort1 {

	public static void main(String[] args) {
		int size = 15;
		int[] array = data(size);
		//int[] array = {8, 6, 7, 9, 3, 4, 5, 1, 2};

		int[] arrayBackup = new int[size];
		System.arraycopy(array, 0, arrayBackup, 0, size);

		sort(array);

		System.out.println(Arrays.toString(array));
		Arrays.sort(arrayBackup);
		System.out.println(Arrays.equals(array, arrayBackup));

		//ClassUtils.forName("java.lang.int")
		assert 0 > 1 : ("判断有误");

	}

	public static void sort(int[] array) {
		//选择排序：从第0个元素开始，该索引视为最小索引，递增跟后面的数比较，只要发现比后面的数大，则最小索引改成对应索引值，遍历完成把0元素和最小索引元素交互。后面同理
		/*
		int minIndex = -1;
		for (int i=0; i<array.length; i++) {
			minIndex = i;
			for (int j=i+1; j<array.length; j++) {
				if (array[minIndex] > array[j]) {
					minIndex = j;
				}
			}
			swap(array, i, minIndex);
		}
		 */

		//冒泡排序：第一轮先找到一个最大的数，从第0个开始，两两比较大小，一直挪到最后一位
		/*for (int j=0; j<array.length; j++) {
			int maxIndex = 0;
			for (int i = 0; i < array.length - j - 1; i++) {
				if (array[maxIndex] > array[i + 1]) {
					swap(array, maxIndex, i + 1);
				}
				maxIndex = i + 1;
			}
		}*/

		//插入排序(还可以写成二分插入)：从第0个元素开始，往后递增，再往前比较有序的"子数组"元素，只要发现比前面的元素小，进行替换
		//不稳定
		/*for (int j=1; j<array.length; j++) {
			for (int i=j; i>0; i--) {
				int lastIndex = i - 1;
				if (array[i] < array[lastIndex]) {
					swap(array, i, lastIndex);
				}
			}
		}*/

		//稳定
		/*for (int j=0; j<array.length-1; j++) {
			int i = j + 1;
			int temp = array[i];
			while (--i >= 0) {
				int lv = array[i];
				if (lv > temp) {
					array[i+1] = lv;
				} else {
					break;
				}
			}
			array[i + 1] = temp;
		}*/

		//希尔排序：有间隔的插入排序，间隔逐渐缩写
		//视频代码:
		/*
		* //这个间隔的意思是：gap==4，从第4个元素跟第0个元素比较，再从第5个元素跟第1个元素比较，一轮过后，gap==2，用第2个元素和第0个元素比较，第3个元素和第1个元素比较，直到 gap/2 == 0 结束.....并不是先取出索引等于0,4,8的元素来比较
		int gap = 4;
		for (int k = gap; k < array.length; k++) {
			for (int j = k; j > gap - 1; j = j - gap) {
				int lastIndex = j - gap;
				if (array[j] < array[lastIndex]) {
					swap(array, j, lastIndex);
				}
			}
		}
		* */
		/*for (int gap = array.length / 2; gap > 0; gap /= 2) {
			for (int k = gap; k < array.length; k++) {
				for (int j = k; j > gap - 1; j = j - gap) {
					int lastIndex = j - gap;
					if (array[j] < array[lastIndex]) {
						swap(array, j, lastIndex);
					}
				}
			}
		}*/

		//归并排序
		//sort(array, 0, array.length - 1);

	}

	public static void sort(int arr[], int left, int right) {
		if (left == right) {
			return;
		};
		int mid =  left + (right - left) / 2;
		sort(arr, left, mid);
		sort(arr, mid + 1, right);

		System.out.println("---------------");
		System.out.println("left = " + left + ", mid = " + mid + ", right = " + right);
		System.out.print("sort begin : ");
		for (int i=left; i<=right; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
		merge(arr, left, mid + 1, right);
		System.out.print("sort after : ");
		for (int i=left; i<=right; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
		System.out.println("---------------");

	}

	/**
	 * @param arr
	 * @param leftPrt 左半截的起始下标
	 * @param rightPrt 右半截的起始下标
	 * @param rightBound 右边边界
	 */
	public static void merge(int[] arr, int leftPrt, int rightPrt, int rightBound) {
		int leftEndPrt = rightPrt, i3 = 0, templeftPrt = leftPrt, tempRightPrt = rightBound;
		int[] newArray = new int[rightBound - leftPrt + 1];
		while (leftPrt < leftEndPrt || rightPrt <= rightBound) {
			if (leftPrt == leftEndPrt) {
				newArray[i3++] = arr[rightPrt++];
				continue;
			}
			if (rightPrt == (rightBound + 1)) {
				newArray[i3++] = arr[leftPrt++];
				continue;
			}
			if (arr[leftPrt] <= arr[rightPrt]) {
				newArray[i3++] = arr[leftPrt++];
			} else {
				newArray[i3++] = arr[rightPrt++];
			}
		}
		for (int i=0; i<=tempRightPrt - templeftPrt; i++) {
			arr[templeftPrt + i] = newArray[i];
		}
		System.out.println("newArray = " + Arrays.toString(newArray));
	}

	public static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static int[] data(int size) {
		Random random = new Random();
		int[] array = new int[size];
		for (int i=0; i<size; i++) {
			array[i] = random.nextInt(10000);
		}
		return array;
	}

}