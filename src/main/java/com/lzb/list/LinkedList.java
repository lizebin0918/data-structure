package com.lzb.list;

import java.util.Comparator;
import java.util.Objects;

/**
 * 单向链表实现<br/>
 * Created on : 2020-12-20 10:32
 * @author lizebin
 */
public class LinkedList<E> {

    private Node<E> head = new Node<>(null, null);

    private static class Node<E> {
        E data;
        Node<E> next;

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
        list.reverse3();
        System.out.println("-----------排序之后------------");
        list.show();


        Comparator<Integer> comparator1 = Comparator.naturalOrder();
        LinkedList<Integer> list1 = new LinkedList<>();
        list1.addLast(1);
        list1.addLast(2);
        list1.addLast(5);
        list1.addLast(6);
        list1.addLast(20);
        LinkedList<Integer> list2 = new LinkedList<>();
        list2.addLast(3);
        list2.addLast(4);
        list2.addLast(6);
        list2.addLast(7);
        list2.addLast(8);
        list2.addLast(9);
        list2.addLast(21);

        LinkedList<Integer> list3 = new LinkedList<>();
        list3.merge(list1, list2, comparator1);
        list3.show();
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
