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