package com.lzb.stack;

/**
 * 栈<br/>
 * 1.中缀表达式转后缀表达式与求值（实际解决）
 * 2.二叉树的遍历
 * 3.图形的深度优先
 *
 * Created on : 2020-12-25 22:22
 * @author lizebin
 */
public class MyStack<E> {

    private Object[] array;
    private int top = -1;
    private int size;

    /**
     * 最小值索引栈
     */
    private int[] minStack;
    /**
     * 最小值栈顶
     */
    private int minTop = -1;
    private int[] maxStack;
    private int maxTop = -1;


    public MyStack(int size) {
        this.array = new Object[size];
        this.minStack = new int[size];
        this.maxStack = new int[size];
    }

    /**
     * 出栈
     * @return
     */
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        E returnValue = (E) array[top--];
        if ((top+1) == minTop) {
            --minTop;
        }
        if ((top+1) == maxTop) {
            --maxTop;
        }
        return returnValue;
    }

    /**
     * 入栈
     * @return
     */
    public <E extends Comparable<? super E>> boolean push(E e) {
        if (isFull()) {
            return false;
        }
        {
            if (isEmpty()) {
                minStack[++minTop] = 0;
                maxStack[++maxTop] = 0;
            } else {
                //入栈的元素小于当前最小值，需要进入minStack
                if (e.compareTo((E)array[minStack[minTop]]) < 0) {
                    minStack[++minTop] = top + 1;
                }
                if (e.compareTo((E)array[maxStack[maxTop]]) > 0) {
                    maxStack[++maxTop] = top + 1;
                }
            }
        }
        array[++top] = e;
        return true;
    }

    public void show() {
        System.out.println("");
        int length = top + 1;
        while (--length >= 0) {
            System.out.print(array[length]);
        }
        System.out.println("");
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == array.length - 1;
    }

    public E peek() {
        return (E) array[top];
    }

    /**
     * 栈中最小值
     * @return
     */
    public E min() {
        if (isEmpty()) {
            return null;
        }
        return (E) array[minStack[minTop]];
    }

    /**
     * 栈中最大值
     * @return
     */
    public E max() {
        if (isEmpty()) {
            return null;
        }
        return (E) array[maxStack[maxTop]];
    }

    public static void main(String[] args) {
        MyStack<String> stack = new MyStack<>(100);
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("z");
        stack.show();
        System.out.println("min = " + stack.min());
        System.out.println("max = " + stack.max());
        System.out.println(stack.isFull());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.isEmpty());
        stack.push("b");
        stack.push("c");
        System.out.println("min = " + stack.min());
        System.out.println("max = " + stack.max());
        stack.show();

    }

}
