package com.lzb.mixed;

/**
 * 蛇形打印<br/>
 * Created on : 2021-01-05 21:16
 *
 * @author lizebin
 */
public class SnakePrint {

    private static int[][] array = new int[4][4];

    /**
     * 1 2 3 4
     * 12 13 14 5
     * 11 16 15 6
     * 10 9 8 7
     */
    static {
        array[0][0] = 1;
        array[0][1] = 2;
        array[0][2] = 3;
        array[0][3] = 4;

        array[1][0] = 12;
        array[1][1] = 13;
        array[1][2] = 14;
        array[1][3] = 5;

        array[2][0] = 11;
        array[2][1] = 16;
        array[2][2] = 15;
        array[2][3] = 6;

        array[3][0] = 10;
        array[3][1] = 9;
        array[3][2] = 8;
        array[3][3] = 7;
    }

    public static void main(String[] args) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }


        //蛇形打印
        int sX = 0, sY = 0, eX = array.length - 1, eY = array[eX].length - 1;
        while (sX < eX && sY < eY) {
            for (int i = sY; i < eY; i++) {
                System.out.println(array[sX][i]);
            }
            for (int i = sX; i < eX; i++) {
                System.out.println(array[i][eY]);
            }
            for (int i = eY; i > sY; i--) {
                System.out.println(array[eX][i]);
            }
            for (int i = eX; i > sX; i--) {
                System.out.println(array[i][sY]);
            }
            ++sX;++sY;--eX;--eY;
        }
    }

}
