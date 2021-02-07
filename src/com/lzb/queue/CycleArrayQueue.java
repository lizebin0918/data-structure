package com.lzb.queue;

import java.util.Scanner;

/**
 * 循环队列<br/>
 * 1.head 取某一个值，如 0
 * 2.tail 取值[0, n-1] = n 种取值
 * 3.队列状态 n+1:队空,1,2,3,4,5,6..n,队满:一共是 n+1 个状态，必然导致一个状态有两个取值
 * 针对3的解决方案：
 *  1.记录当前数组的count
 *  2.留意空元素作为标记
 * Created on : 2020-12-16 22:26
 * @author lizebin
 */
public class CycleArrayQueue<E> implements Queue<E> {

    private Object[] array;

    /**
     * 头指针:表示队列的第一个元素的下标
     */
    private int head;
    /**
     * 尾指针:表示队列的最后一个元素的后一个位置的下标，预留一个空间作为约定，tail就是这个空格
     */
    private int tail;

    public CycleArrayQueue(int size) {
        array = new Object[size];
    }

    @Override
    public int size() {
        return (tail + array.length - head) % array.length;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean offer(E e) {
        if (isFull()) {
            return false;
        }
        array[tail] = e;
        tail = (tail + 1) % array.length;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        Object value = array[head];
        head = (head + 1) % array.length;
        return (E) value;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return (E) array[head];
    }

    @Override
    public boolean isFull() {
        //预留一个元素
        return head == (tail + 1) % array.length;
    }

    @Override
    public void show() {
        System.out.print("队列:");
        for (int i=head, size= head + size(); i<size; i++) {
            System.out.print(array[i]);
            System.out.print(",");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CycleArrayQueue<String> queue = new CycleArrayQueue<>(5);
        /*boolean running = true;
        while(running) {
            System.out.println("输入操作");
            char c = input.nextLine().charAt(0);
            switch (c) {
                case 'a':
                    System.out.println("添加元素:");
                    queue.offer(input.nextLine());
                    break;
                case 'e':
                    System.out.println("读取退出");
                    running = false;
                    break;
                default:
                    running = false;
                    break;
            }
        }*/
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        System.out.println("size = " + queue.size());
        System.out.println("isFull = " + queue.isFull());

        System.out.println(queue.poll());
        System.out.println(queue.poll());

        queue.offer("z");
        queue.offer("y");

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.size());

        queue.offer("a");
        queue.offer("b");
        queue.offer("c");

        System.out.println(queue.size());
        queue.show();
        System.out.println(queue.poll());
        queue.show();
        System.out.println(queue.size());


        System.out.println("isEmpty = " + queue.isEmpty());
        System.out.println("isFull = " + queue.isFull());
    }
}
