package com.lzb.tree;

import com.lzb.tree.printer.BinaryTreeInfo;
import com.lzb.tree.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;

/**
 * <br/>
 * Created on : 2021-01-15 11:44
 * @author chenpi 
 */
public class Tree<E> implements BinaryTreeInfo {

    @Override
    public Object root() {
       return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>) node).data;
    }

    public static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        public Node() {

        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public Node(E e) {
            this.data = e;
        }
    }

    private Node<E> root;

    public Tree(E e) {
        this.root = new Node<>();
        this.root.data = e;
    }

    public boolean isComplete() {
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            boolean isLeaf = false;
            for (int i=0;i<queue.size(); i++) {
                //先查看，为空不用出队
                Node<E> current = queue.poll();
                if (isLeaf && !current.isLeaf()) {
                    return false;
                }
                Node<E> left = current.left;
                Node<E> right = current.right;
                if (Objects.nonNull(left)) {
                    queue.add(left);
                } else if (Objects.nonNull(current.right)){
                    return false;
                }
                if (Objects.nonNull(right)) {
                    queue.add(right);
                } else {
                    //后续的节点都必须是叶子节点
                    isLeaf = true;
                }
            }
        }
        return true;
    }

    /**
     * 数组只要符合:前一半true，后一半false，返回true
     * [true,true...*true,false,false...*false] -> true
     * [true,false...*true,false,false...*false] -> false
     * [true,false...*true,false,true...*false] -> false
     * [true] -> true
     * [false] -> true
     * @param array
     * @return
     */
    public boolean isHalf(boolean[] array) {
        int trueIndex = -1, falseIndex = array.length;
        for (int i=0; i<array.length; i++) {
            if (array[trueIndex + 1]) {
                ++trueIndex;
            }
            if (!array[falseIndex - 1]) {
                --falseIndex;
            }
            if (falseIndex - trueIndex == 1) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Tree<String> tree = new Tree<>("root");
        tree.root.left = new Node<>("left");
        tree.root.right = new Node<>("right");
        tree.root.right.left = new Node<>("right-left");
        tree.root.right.right = new Node<>("right-right");
        tree.root.left.left = new Node<>("left-left");
        tree.root.left.right = new Node<>("left-right");
        System.out.println(tree.isComplete());

        //boolean[] array = {true, true, true};
        boolean[] array = {false, true, true, false};
        System.out.println(tree.isHalf(array));

        BinaryTrees.print(tree);
        System.out.println("");
        tree.reverse();
        BinaryTrees.print(tree);
        System.out.println("");
    }

    /**
     * 翻转二叉树:所有节点的左右子树都交换一遍
     *
     * 思路:前序遍历，中序遍历，后序遍历都可以 -- 这道题的本质是遍历一棵树的所有节点即可
     */
    public void reverse() {
        reverse(root);
    }

    private void reverse(Node<E> node) {
        if (Objects.isNull(node)) {
            return;
        }
        Node<E> temp = node.left;
        node.left = node.right;
        node.right = temp;
        reverse(node.left);
        reverse(node.right);
    }

    /**
     * 给定一个节点，返回它的前驱节点:中序遍历之前的一个节点
     * @param node
     * @return
     */
    private Node<E> predecessor(Node<E> node) {
        return null;
    }
}