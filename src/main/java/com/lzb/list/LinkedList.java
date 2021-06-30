package com.lzb.list;

import java.util.Comparator;
import java.util.Objects;

/**
 * 单向链表实现<br/>
 * Created on : 2020-12-20 10:32
 * @author lizebin
 */
public class LinkedList<E> {

    public Node<E> head = new Node<>(null, null);

    public static class Node<E> {
        public E data;
        public Node<E> next;

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        //list.addLast("a");
        Comparator<String> comparator = Comparator.naturalOrder();
        /*list.addByOrder("a", comparator);
        list.addByOrder("c", comparator);
        list.addByOrder("b", comparator);
        list.addByOrder("e", comparator);
        list.addByOrder("f", comparator);
        list.addByOrder("z", comparator);
        list.addByOrder("g", comparator);
        list.addByOrder("y", comparator);
        list.addByOrder("w", comparator);
        list.addByOrder("k", comparator);*/
        list.addByOrder("1", comparator);
        list.addByOrder("2", comparator);
        list.addByOrder("3", comparator);
        list.addByOrder("4", comparator);
        list.addByOrder("5", comparator);
        list.show();

    }

    /**
     * 尾添加
     * @param e
     * @return
     */
    public boolean addLast(E e) {
        Node<E> temp = head;
        while(Objects.nonNull(temp.next)) {
            temp = temp.next;
            if (Objects.isNull(temp.next)) {
                break;
            }
        }
        temp.next = new Node<>(e, null);
        return true;
    }

    /**
     * 头添加
     * @param e
     * @return
     */
    public boolean addFirst(E e) {
        return true;
    }

    /**
     * 有序添加
     * @param e
     * @param comparator
     * @return
     */
    public boolean addByOrder(E e, Comparator<E> comparator) {
        Node<E> temp = head;
        Node<E> next = null;
        while(Objects.nonNull(temp)) {
            next = temp.next;
            if (Objects.isNull(next)) {
                break;
            }
            E tempValue = temp.data;
            E nextValue = next.data;
            if (comparator.compare(nextValue, e) >= 0 && comparator.compare(tempValue, e) < 0) {
                break;
            }
            temp = next;
        }
        temp.next = new Node<>(e, next);
        return true;
    }

    /**
     * 顺序遍历显示
     */
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
}
