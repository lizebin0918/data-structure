package com.lzb.list;

import java.util.*;

/**
 * 单向回环链表<br/>
 * Created on : 2020-12-23 22:45
 * @author lizebin
 */
public class CircleLinkedList<E> {

    /**
     * 头指针（实际指向第一个元素）
     */
    private Node<E> head;
    /**
     * 尾指针（实际指向最后一个元素）
     */
    private Node<E> tail;

    private static class Node<E> {
        E data;
        CircleLinkedList.Node<E> next;

        public Node(CircleLinkedList.Node<E> next, E data) {
            this.next = next;
            this.data = data;
        }
    }

    public void add(E e) {
        if (Objects.isNull(head)) {
            head = new Node<>(head, e);
            tail = head;
            return;
        }

        Node<E> current = head.next;
        while (Objects.nonNull(current)) {
            if (current == tail) {
                break;
            }
            current = current.next;
        }
        current = new Node<>(head, e);
        tail.next = current;
        tail = current;
    }

    public void show() {
        if (Objects.isNull(head)) {
            System.out.println("It is a empty linkedlist");
        }
        Node<E> current = head;
        while (current != tail) {
            System.out.print(current.data);
            System.out.print(",");
            current = current.next;
        }
        System.out.print(current.data);
        System.out.println("");
    }

    /**
     * 解决约瑟夫问题
     * @param start 起始位置索引
     * @param count 对应出队数量
     * @param total 总数
     */
    public static void josephusSolution(int start, int count, int total) {
        //构造环形链表
        CircleLinkedList<String> list = new CircleLinkedList<>();
        for (int i = 0; i<total; i++) {
            list.add(Objects.toString(i + 1));
        }
        //新列表
        List<String> result = new ArrayList<>();
        //确定起始、起始上一个位置
        Node<String> pre = null;
        Node<String> current = list.head;
        while (--start >= 0) {
            pre = current;
            current = current.next;
        }
        //计数并移除
        while (true) {
            if (pre == current) {
                break;
            }
            int loop = count;
            while (--loop > 0) {
                pre = current;
                current = current.next;
            }
            //只有一个元素
            if (current == null) {
                current = pre;
                break;
            }
            result.add(current.data);
            pre.next = current.next;
            current = current.next;
        }
        result.add(current.data);
        System.out.println(result);
    }

    /**
     * 删除末尾元素
     */
    public void remove() {

    }

    public static void main(String[] args) {
        CircleLinkedList<String> list = new CircleLinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("d");
        list.add("d");
        list.add("e");
        list.show();

        CircleLinkedList.josephusSolution(0, 2, 1);
    }
}
