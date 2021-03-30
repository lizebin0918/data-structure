package com.lzb.list;

import java.util.Objects;

/**
 * 双向链表<br/>
 * Created on : 2020-12-23 21:40
 * @author lizebin
 */
public class DualLinkedList<E> {

    private Node<E> head;

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> pre;

        public Node(Node<E> pre, Node<E> next, E data) {
            this.pre = pre;
            this.next = next;
            this.data = data;
        }
    }

    public DualLinkedList() {
        head = new Node<>(null, null, null);
    }

    public void add(E data) {
        Node<E> pre = head;
        Node<E> next = head.next;
        while (Objects.nonNull(next)) {
            pre = next;
            next = next.next;
        }
        Node<E> n = new Node<>(pre, null, data);
        pre.next = n;
    }

    public void remove(int i) {
        if (i > size()) {
            throw new RuntimeException("out of index");
        }
        Node<E> next = head.next;
        int j = 0;
        while (j++ < i && Objects.nonNull(next)) {
            next = next.next;
        }
        if (Objects.isNull(next)) {
            throw new RuntimeException("找不到元素");
        }
        next.pre.next = next.next;
        next.next.pre = next.pre;
    }

    public long size() {
        long count = 0L;
        Node<E> next = head.next;
        while (Objects.nonNull(next)) {
            next = next.next;
            count++;
        }
        return count;
    }

    public void show() {
        System.out.print("链表遍历:");
        Node<E> next = head.next;
        while(Objects.nonNull(next)) {
            System.out.print(next.data);
            System.out.print(",");
            next = next.next;
        }
        System.out.println("");
    }

    public void set(int i, E data) {
        if (i > size()) {
            throw new RuntimeException("out of index");
        }
        Node<E> next = head.next;
        int j = 0;
        while (j++ < i && Objects.nonNull(next)) {
            next = next.next;
        }
        if (Objects.isNull(next)) {
            throw new RuntimeException("找不到元素");
        }
        next.data = data;
    }

    public static void main(String[] args) {
        DualLinkedList<String> list = new DualLinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.show();
        System.out.println(list.size());
        //list.remove(1);
        //list.remove(2);
        list.set(2, "100");
        list.show();
    }
}
