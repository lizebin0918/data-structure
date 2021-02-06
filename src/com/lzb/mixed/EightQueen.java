package com.lzb.mixed;

/**
 * 八皇后问题<br/>
 * Created on : 2020-12-30 22:49
 * @author lizebin
 */
public class EightQueen {

    public static final int[][] BOARD = new int[8][8];

    //可以采用一维数组表示:下标表示对应的行，值表示对应的列
    //public static final int[] array = new int[8];

    public static final int PUTED = 1;
    public static final int NOT_PUTED = 0;

    private static int solution = 0;

    public static void main(String[] args) {
        show();
        EightQueen.put(0);
        System.out.println(solution);

        System.out.println("123456".hashCode());
    }

    /**
     * 放置第n个皇后
     * @param i start at 0
     */
    public static void put(int i) {
        if (i == BOARD.length) {
            solution++;
            show();
            return;
        }
        for (int j=0; j<BOARD[i].length; j++) {
            if (!isOnLine(i, j)) {
                BOARD[i][j] = PUTED;
                put(i + 1);
                BOARD[i][j] = NOT_PUTED;
            }
        }
    }

    public static void show() {
        System.out.println("--------------");
        for (int i = 0; i < BOARD.length; i++) {
            for (int j = 0; j < BOARD[i].length; j++) {
                System.out.print(BOARD[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("--------------");
    }

    /**
     * 是否在同一条线上
     * @param i
     * @param j
     * @return
     */
    public static boolean isOnLine(int i, int j) {
        //匹配【-】
        for (int m = 0, size = BOARD[i].length; m < size ; m++) {
            if (BOARD[i][m] == PUTED) {
                return true;
            }
        }
        //匹配【|】
        for (int m = 0, size = BOARD.length; m < size; m++) {
            if (BOARD[m][j] == PUTED) {
                return true;
            }
        }
        for (int m = 0; m < BOARD.length; m++) {
            for (int n = 0; n < BOARD[m].length; n++) {
                //匹配【/】
                if ((m + n) == (i + j) && BOARD[m][n] == PUTED) {
                    return true;
                }
                //匹配【\】
                if ((m - i) == (n - j) && BOARD[m][n] == PUTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
