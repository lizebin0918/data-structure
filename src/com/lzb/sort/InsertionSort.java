package com.lzb.sort;
/**
 * 插入排序(还可以写成二分插入)：从第0个元素开始，往后递增，再往前比较有序的"子数组"元素，只要发现比前面的元素小，进行替换<br/>
 * Created on : 2021-02-14 12:45
 * @author lizebin
 */
public class InsertionSort extends Sort {


    @Override
    public int[] sort(int[] array) {
        //每次比较元素，判断比自己大的元素，则交换元素，直到遇到比自己小的元素就停下
		/*for (int j=1; j<array.length; j++) {
			for (int i=j; i>0; i--) {
				int lastIndex = i - 1;
				if (array[i] < array[lastIndex]) {
					swap(array, i, lastIndex);
				}
			}
		}*/

		//基于上面版本，无需每次比较都swap()，swap()需要三行代码，而每次只要把较大值替换，一行代码即可
        for (int i = 1, length = array.length; i < length; i++) {
            int loop = i;
            while (--loop > 0) {
                if (array[loop] <= array[loop + 1]) {
                    break;
                }
                array[loop + 1] = array[loop];
            }
        }
        return array;
    }

    public static void main(String[] args) {
        new InsertionSort().test();
    }
}
