package com.lzb.queue;

import java.util.Arrays;

/**
 * <br/>
 * Created on : 2020-12-14 22:21
 * @author lizebin
 */
public class SequenceArrayQueue<E> implements Queue<E> {

    private Object[] array;

    /**
     * 队头
     */
    private int head;
    /**
     * 队尾
     */
    private int tail;

    public SequenceArrayQueue(int size) {
        array = new Object[size];
    }

    @Override
    public int size() {
        return tail - head;
    }

    @Override
    public boolean isEmpty() {
        return tail == head;
    }

    @Override
    public boolean offer(E e) {
        if (isFull()) {
            return false;
        }
        array[tail++] = e;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        return (E) array[head++];
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
        return (tail - head) == array.length;
    }

    @Override
    public void show() {

    }

    public static void main(String[] args) {
        SequenceArrayQueue<String> queue = new SequenceArrayQueue<>(10);
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        queue.offer("f");
        queue.offer("g");
        queue.offer("h");
        queue.offer("i");
        queue.offer("j");
        System.out.println("isEmpty()" + queue.isEmpty());
        System.out.println("isFull()" + queue.isFull());
        System.out.println(Arrays.toString(queue.array));
        System.out.println("size = " + queue.size());
        System.out.println(queue.peek());
        System.out.println("size = " + queue.size());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        //tail 可以等于 size
        System.out.println("tail=" + queue.tail);
        //head 可以等于 size
        System.out.println("head=" + queue.head);//
        System.out.println("isEmpty()" + queue.isEmpty());
        //"假越界"
        //queue.offer("j");
        System.out.println(Arrays.toString(queue.array));
        System.out.println("size = " + queue.size());
    }
}
