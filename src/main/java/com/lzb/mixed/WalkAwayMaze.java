package com.lzb.mixed;
/**
 * 走出迷宫<br/>
 * Created on : 2020-12-28 23:03
 * @author lizebin
 */
public class WalkAwayMaze {

    public final int[][] maze;

    /**
     * 障碍
     */
    public static final int OBSTACLE = 2;

    /**
     * 未走
     */
    public static final int NOT_WALK = 0;

    /**
     * 已走
     */
    public static final int WALKED = 1;

    /**
     * 禁止
     */
    public static final int FORBID = 3;

    /**
     * 出口
     */
    public static final int OUT = 5;

    public WalkAwayMaze(int i, int j) {
        //initial maze
        maze = new int[i][j];
        //initial wall
        for (int w = 0; w < i; w++) {
            maze[w][0] = OBSTACLE;
        }
        for (int w = 0; w < i; w++) {
            maze[w][j - 1] = OBSTACLE;
        }
        for (int w = 1; w < j - 1; w++) {
            maze[0][w] = OBSTACLE;
        }
        for (int w = 1; w < j - 1; w++) {
            maze[i - 1][w] = OBSTACLE;
        }
    }

    /**
     * 设置障碍
     * @param i 行
     * @param j 列
     */
    public void setObstacle(int i, int j) {
        maze[i][j] = OBSTACLE;
    }

    public void show() {
        System.out.println("--------------");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("--------------");
    }

    public void setOut(int i, int j) {
        maze[i][j] = OUT;
    }

    /**
     * 是否为终点
     * @param i
     * @param j
     * @return
     */
    public boolean isOut(int i, int j) {
        return maze[i][j] == OUT;
    }

    /**
     * 是否走过
     * @param i
     * @param j
     * @return
     */
    public boolean isWalked(int i, int j) {
        return maze[i][j] == WALKED;
    }

    /**
     * 是否为障碍
     * @param i
     * @param j
     * @return
     */
    public boolean isObstacle(int i, int j) {
        return maze[i][j] == OBSTACLE;
    }

    /**
     * 禁止
     * @param i
     * @param j
     * @return
     */
    public boolean isForbid(int i, int j) {
        return maze[i][j] == FORBID;
    }

    /**
     * 寻找出口
     * 每次寻找的路径规则如下：
     * ============
     *       ^(4)
     *       |
     * (3)<--*-->(1)
     *       |
     *       v(2)
     * ============
     * @param i
     * @param j
     * @return 是否找到通路
     */
    public boolean walk(int i, int j) {
        if (isOut(i, j)) {
            return true;
        }
        if (isWalked(i, j) || isObstacle(i, j) || isForbid(i, j)) {
            return false;
        }
        maze[i][j] = WALKED;
        //向右
        if (walk(i, j + 1)) {
            System.out.println(String.format("right:i=%s,j=%s", i, j));
            return true;
        }
        //向下
        if (walk(i + 1, j)) {
            System.out.println(String.format("down:i=%s,j=%s", i, j));
            return true;
        }
        //向左
        if (walk(i, j - 1)) {
            System.out.println(String.format("left:i=%s,j=%s", i, j));
            return true;
        }
        //向上
        if (walk(i - 1, j)) {
            System.out.println(String.format("up:i=%s,j=%s", i, j));
            return true;
        }
        maze[i][j] = FORBID;
        return false;
    }

    public static void main(String[] args) {
        WalkAwayMaze maze = new WalkAwayMaze(8, 8);
        maze.setObstacle(4, 1);
        maze.setObstacle(4, 3);
        maze.setObstacle(4, 2);
        maze.setObstacle(4, 4);
        maze.setObstacle(4, 5);
        maze.setObstacle(4, 6);

        maze.setOut(5, 3);

        maze.show();

        System.out.println("---------------------");
        System.out.println(maze.walk(1, 1));
        maze.show();
        System.out.println("---------------------");

    }
}
