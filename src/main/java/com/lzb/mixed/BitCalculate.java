package com.lzb.mixed;
/**
 * 位运算例子<br/>
 * Created on : 2021-02-08 22:16
 * @author lizebin
 */
public class BitCalculate {

    public static void main(String[] args) {
        int i = 8;
        System.out.println(Integer.toBinaryString(i));
        int i1 = 5 >> 1;
        System.out.println(Integer.toBinaryString(i1) + " --> i1 = " + i1);

        int i2 = 9 & 5;
        System.out.println(Integer.toBinaryString(i2) + " --> i2 = " + i2);
        //或:相同为1，不相同为0
        i2 = 9 | 5;
        System.out.println(Integer.toBinaryString(i2) + " --> i2 = " + i2);
        //异或:相同为0，不相同为1
        i2 = 9 ^ 5;
        System.out.println(Integer.toBinaryString(i2) + " --> i2 = " + i2);
        i2 = ~(9 ^ 5);
        System.out.println(Integer.toBinaryString(i2) + " --> i2 = " + i2);

        long l = Double.doubleToLongBits(1.2);
        System.out.println(Long.toBinaryString(l));
        System.out.println(Long.highestOneBit(l));
        System.out.println(Long.lowestOneBit(l));
        System.out.println(l);
    }

}
