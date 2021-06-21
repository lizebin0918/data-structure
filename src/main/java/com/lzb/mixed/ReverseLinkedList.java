package com.lzb.mixed;


import com.lzb.list.LinkedList;

import java.util.Comparator;
import java.util.Objects;

/**
 * 链表逆序<br/>
 * Created on : 2021-06-21 15:09
 *
 * @author chenpi
 */
public class ReverseLinkedList<E> extends LinkedList<E> {

    public static void main(String[] args) {
        //测试链表有序合并
        System.out.println("链表合并");
        testMerge();
        //链表逆序
        ReverseLinkedList<Integer> list = new ReverseLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        //基于current/pre指针
        //list.reverse();
        //基于头插法
        //list.reverse1();
        //基于新的逆序节点头插
        //list.reverse2();
        //基于递归逆序
        list.reverse3();

        list.show();

    }

    public static void testMerge() {

        Comparator<Integer> comparator1 = Comparator.naturalOrder();
        ReverseLinkedList<Integer> list1 = new ReverseLinkedList<>();
        list1.addLast(1);
        list1.addLast(2);
        list1.addLast(5);
        list1.addLast(6);
        list1.addLast(20);
        ReverseLinkedList<Integer> list2 = new ReverseLinkedList<>();
        list2.addLast(3);
        list2.addLast(4);
        list2.addLast(6);
        list2.addLast(7);
        list2.addLast(8);
        list2.addLast(9);
        list2.addLast(21);

        ReverseLinkedList<Integer> list3 = new ReverseLinkedList<>();
        list3.merge(list1, list2, comparator1);
        list3.show();

    }

    /**
     * 倒序，头插法(基于头部互换)
     */
    public void reverse1() {
        Node<E> current = head.next;
        //只有一个元素
        if (Objects.isNull(current)) {
            return;
        }
        //第一个元素无需插入
        Node<E> first = head.next;
        current = first.next;
        while (Objects.nonNull(current)) {
            //下一个节点，头插入
            Node<E> next = current.next;
            Node<E> headNext = head.next;
            head.next = current;
            current.next = headNext;
            //固定指向下一个元素
            first.next = next;
            current = next;
        }
    }

    /**
     * 倒序，基于新的逆序节点头插
     */
    public void reverse2() {
        Node<E> reverse = new Node<>(null, null);
        Node<E> current = head.next;
        while (Objects.nonNull(current)) {
            Node<E> next = current.next;
            head.next = next;
            current.next = reverse.next;
            reverse.next = current;
            current = next;
        }
        head.next = reverse.next;
        reverse = null;
    }

    /**
     * 基于递归逆序
     */
    public void reverse3() {
        head.next = reverse3(head.next);
    }

    private Node<E> reverse3(Node<E> head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node<E> newHead = reverse3(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 倒序：利用 pre/current 指针同时向前移动，cur指向pre实现逆序
     */
    public void reverse() {
        Node<E> pre = null;
        Node<E> current = head.next;
        while (Objects.nonNull(current)) {
            Node<E> next = current.next;
            if (Objects.isNull(next)) {
                current.next = pre;
                head.next = current;
                break;
            }
            //指针变化
            current.next = pre;
            //向前移到
            pre = current;
            current = next;
        }
    }

    /**
     * 两个有序链表合并
     *
     * @param list1
     * @param list2
     * @param comparator
     * @return
     */
    public void merge(LinkedList<E> list1, LinkedList<E> list2, Comparator<E> comparator) {
        Node<E> head1 = list1.head, head2 = list2.head, next1 = head1.next, next2 = head2.next;
        while (Objects.nonNull(next1) || Objects.nonNull(next2)) {
            if (Objects.isNull(next1)) {
                addLast(next2.data);
                next2 = next2.next;
                continue;
            }
            if (Objects.isNull(next2)) {
                addLast(next1.data);
                next1 = next1.next;
                continue;
            }
            if (comparator.compare(next1.data, next2.data) >= 0) {
                addLast(next2.data);
                next2 = next2.next;
            } else {
                addLast(next1.data);
                next1 = next1.next;
            }
        }
    }

}
