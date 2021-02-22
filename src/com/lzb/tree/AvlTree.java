package com.lzb.tree;


import com.lzb.utils.printer.BinaryTrees;

/**
 * AVL 树<br/>
 * Created on : 2021-01-21 22:43
 * @author lizebin
 */
public class AvlTree<E extends Comparable<? super E>> extends BalanceBinarySearchTree<E> {

    public static class AvlNode<E> extends Node<E> {

        public AvlNode<E> taller() {
            int leftHeight = left == null ? 0 : left.height();
            int rightHeight = right == null ? 0 : right.height();
            if (leftHeight > rightHeight) return (AvlNode<E>) left;
            if (leftHeight < rightHeight) return (AvlNode<E>) right;
            //高度相同
            return (AvlNode<E>) (isLeft() ? left : right);
        }

        public boolean isBalance() {
            int leftHeight = left == null ? 0 : left.height();
            int rightHeight = right == null ? 0 : right.height();
            return Math.abs(leftHeight - rightHeight) <= 1;
        }

        public AvlNode(E data, Node<E> parent) {
            this.data = data;
            this.parent = parent;
        }
    }

    @Override
    protected AvlNode<E> createNode(E data, Node<E> parent) {
        return new AvlNode<>(data, parent);
    }



    @Override
    protected void addAfter(Node<E> node) {
        super.addAfter(node);

        Node<E> parent = node.parent;
        Node<E> grandparent = null;
        if (parent != null) {
            grandparent = parent.parent;
        }
        int heightFactor = 0;
        while (grandparent != null) {
            int leftHeight = 0, rightHeight = 0;
            if (grandparent.left != null) {
                leftHeight = grandparent.left.height();
            }
            if (grandparent.right != null) {
                rightHeight = grandparent.right.height();
            }
            heightFactor = Math.abs(leftHeight - rightHeight);
            if (heightFactor > 1) {
                break;
            }
            parent = grandparent;
            grandparent = grandparent.parent;
        }

        if (grandparent == null) {
            return;
        }

        if (heightFactor <= 1) {
            return;
        }

        //自底向上判断4种情况
        if (node.isLeft()) {
            if (parent.isLeft()) {
                //LL
                rotateRight(grandparent);
            } else {
                //自底向上:LR --> RL
                rotateRight(parent);
                rotateLeft(grandparent);
            }
        } else {
            if (parent.isRight()) {
                //RR
                rotateLeft(grandparent);
            } else {
                //自底向上:RL --> LR
                rotateLeft(parent);
                rotateRight(grandparent);
            }
        }
    }

    /**
     * 被删除节点的父节点:只会导致父节点失衡，但是失衡会往上传递，极端情况会直到根节点(root)
     * @param node
     */
    @Override
    public void removeAfter(Node<E> node) {
        Node<E> parent = node.parent;
        while(parent != null) {
            if (((AvlNode<E>) parent).isBalance()) {
                break;
            }
            AvlNode<E> taller1 = ((AvlNode<E>) parent).taller();
            AvlNode<E> taller2 = ((AvlNode<E>)taller1).taller();
            //自顶向下判断4种情况
            if (taller1.isLeft()) {
                if (taller2.isLeft()) {
                    //LL
                    rotateRight(parent);
                } else {
                    //LR
                    rotateLeft(taller1);
                    rotateRight(parent);
                }
            } else {
                if (taller2.isRight()) {
                    //RR
                    rotateLeft(parent);
                } else {
                    //RL
                    rotateRight(taller1);
                    rotateLeft(parent);
                }
            }
            parent = parent.parent;
        }
    }

    public static void main(String[] args) {
        AvlTree<Integer> tree = new AvlTree<>();

        tree.add(8);
        tree.add(5);
        tree.add(9);
        tree.add(4);
        //tree.add(13);
        //tree.add(21);

        BinaryTrees.print(tree);
        System.out.println("");
        //LL
        tree.add(3);
        BinaryTrees.print(tree);
        System.out.println("");
        //LR
        tree.add(6);
        tree.add(20);
        tree.add(1);

        BinaryTrees.print(tree);
        System.out.println("");

        tree.add(10);
        tree.add(7);

        BinaryTrees.print(tree);
        System.out.println("");

        tree.remove(9);
        System.out.println("--------remove-9--------");

        BinaryTrees.print(tree);
        System.out.println("");


        tree.remove(4);
        System.out.println("--------remove-4--------");

        BinaryTrees.print(tree);
        System.out.println("");

        tree.remove(8);
        tree.remove(5);
        tree.remove(3);
        tree.remove(10);
        tree.remove(20);
        System.out.println("--------remove-8--------");

        BinaryTrees.print(tree);
        System.out.println("");
    }
}
