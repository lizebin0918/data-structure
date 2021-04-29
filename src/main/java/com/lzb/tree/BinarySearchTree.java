package com.lzb.tree;


import com.lzb.util.printer.BinaryTreeInfo;
import com.lzb.util.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 二叉排序树:左子树 < 根 < 右子树，子树符合二叉搜索树
 * 此处泛型的意思：类型 E 必须实现 Comparable 接口，并且这个接口的类型是 E 或 E 的任一父类。
 * 二叉搜索树的遍历作用：
 * 前序遍历的结果是一棵树
 * 中序遍历的结果是升序或者降序
 * 后序遍历适用于一些先子后父的操作
 * 层序遍历
 *  计算二叉树高度
 *  判断一棵树是否为完全二叉树
 * 前序、中序、后序遍历只会跟根节点位置有关，跟左右无关
 * 从数组构建一棵二叉树，按照层序遍历思路遍历
 *
 * 前驱节点:中序遍历时的前一个节点
 * Created on : 2021-01-05 23:15
 * @author lizebin
 */
public class BinarySearchTree<E extends Comparable<? super E>> implements BinaryTreeInfo {

    /**
     * 查找节点
     * @param data
     * @return
     */
    public Node<E> node(E data) {
        Node<E> node = root;
        while (node != null) {
            if (compare(node.data, data) == 0) {
                return node;
            } else if (compare(node.data, data) > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 0.找到父节点 parent
     * 1.创建新节点 node
     * 2.parent.left = node 或者 parent.right = node
     *
     * 注意：如果遇到相同的值，需要覆盖替换成新的对象
     * @param data
     */
    public void add(E data) {
        if (data == null) {
            throw new RuntimeException("data not null");
        }
        if (root == null) {
            root = createNode(data, null);
            addAfter(root);
            size++;
            return;
        }
        Node<E> newNode = null;
        Node<E> parent = root;
        while (true) {
            if (compare(parent.data, data) > 0) {
                Node<E> left = parent.left;
                if (left == null) {
                    newNode = createNode(data, parent);
                    parent.left = newNode;
                    break;
                }
                parent = left;
                continue;
            } else if (compare(parent.data, data) < 0) {
                Node<E> right = parent.right;
                if (right == null) {
                    newNode = createNode(data, parent);
                    parent.right = newNode;
                    break;
                }
                parent = right;
                continue;
            } else {
                parent.data = data;
                return;
            }
        }
        size++;

        if (newNode != null) {
            addAfter(newNode);
        }
    }

    protected void addAfter(Node<E> node) {

    }

    protected void removeAfter(Node<E> node) {

    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(4);
        tree.add(2);
        tree.add(6);
        tree.add(1);
        tree.add(3);
        tree.add(5);
        tree.add(7);
        tree.add(8);
        tree.levelTraverse();
        BinaryTrees.print(tree);
        System.out.println("");
        System.out.println("height:" + tree.height());

        System.out.print("中序遍历:");
        tree.inorderTraverse();
        System.out.println("");

        //Node<Integer> node = tree.predecessor(2);
        //Node<Integer> node = tree.successor(1);
        //Node<Integer> node = tree.node(0);
        //System.out.println("node = " + (node == null ? node : node.data));

        tree.remove(7);
        tree.remove(4);
        tree.remove(3);
        tree.remove(2);
        tree.remove(6);
        tree.remove(1);
        tree.remove(5);
        tree.remove(8);
        tree.inorderTraverse();
        BinaryTrees.print(tree);
        System.out.println("");
        System.out.println(tree.size);

        //tree.clear();
        System.gc();

        System.out.println("done");

    }

    /**
     * 两个元素比较
     * @param e1
     * @param e2
     * @return
     */
    private int compare(E e1, E e2) {
        return e1.compareTo(e2);
    }

    public void remove(E data) {
        Node<E> node = node(data);

        //度为2的节点：找到前驱或者后继节点，替换该节点为被删除的节点（度肯定为0或者1），再走后续逻辑
        if (Objects.nonNull(node.left) && Objects.nonNull(node.right)) {
            Node<E> removeNode = predecessor(data);
            removeNode = Objects.isNull(removeNode) ? successor(data) : removeNode;
            //删除root
            if (Objects.isNull(removeNode)) {
                --size;
                root = null;
                return;
            }
            node.data = removeNode.data;
            node = removeNode;
        }

        //叶子（度为零）节点
        if (node.left == null && node.right == null) {
            Node<E> parent = node.parent;
            if (parent == null) {
                root = null;
                return;
            }
            if (parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            node.data = null;
            --size;
            removeAfter(node);
            return;
        }

        //度为1的节点
        Node<E> parent = node.parent;
        Node<E> nextNode = Objects.nonNull(node.left) ? node.left : node.right;
        if (parent == null) {
            root = nextNode;
            nextNode.parent = null;
            removeAfter(node);
            return;
        }
        nextNode.parent = parent;
        if (parent.left == node) {
            parent.left = nextNode;
        } else {
            parent.right = nextNode;
        }
        node.data = null;
        --size;
        removeAfter(nextNode);

    }

    public boolean contains(E data) {
        return node(data) != null;
    }

    protected Node<E> root;

    @Override
    public Object root() {
        return this.root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>)node).data;
    }

    public static class Node<E> {
        public E data;
        public Node<E> left;
        public Node<E> right;
        public Node<E> parent;
        public boolean isLeaf;

        public Node(E data, Node<E> parent) {
            this.data = data;
            this.parent = parent;
        }

        public Node() {}

        public boolean isLeft() {
            if (parent == null) {
                return false;
            }
            return parent.left == this;
        }

        public boolean isRight() {
            if (parent == null) {
                return false;
            }
            return parent.right == this;
        }

        public int height() {
            LinkedList<Node<E>> queue = new LinkedList<>();
            queue.add(this);
            int height = 0;
            while (!queue.isEmpty()) {
                ++height;
                for (int i=0; i<queue.size(); i++) {
                    Node<E> current = queue.poll();
                    Node<E> left = current.left;
                    if (Objects.nonNull(left)) {
                        queue.add(left);
                    }
                    Node<E> right = current.right;
                    if (Objects.nonNull(right)) {
                        queue.add(right);
                    }
                }
            }
            return height;
        }

        public Node<E> sibling() {
            if (isLeft()) {
                return parent.right;
            }

            if (isRight()) {
                return parent.left;
            }

            return null;
        }


        public Node<E> uncle() {
            if (parent != null) {
                return parent.sibling();
            }
            return null;
        }

    }

    /**
     * 层遍历
     */
    public void levelTraverse() {
        if (Objects.isNull(this.root)) {
            return;
        }
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            for (int i = queue.size(); i > 0; i--) {
                Node<E> node = queue.poll();
                if (Objects.isNull(node)) {
                    System.out.print("[null]");
                    continue;
                }
                System.out.print("[" + node.data + "]");
                queue.add(node.left);
                queue.add(node.right);
            }
            System.out.println("");
        }
    }

    /**
     * 前序遍历
     */
    public void preorderTraverse() {

    }

    /**
     * 中序遍历
     */
    public void inorderTraverse() {
        inorderTraverse(root);
        System.out.println("");
    }

    public void inorderTraverse(Node<E> node) {
        if (node == null) {
            return;
        }
        inorderTraverse(node.left);
        System.out.print(" " + node.data);
        inorderTraverse(node.right);
    }

    /**
     * 后序遍历
     */
    public void postorderTraverse() {

    }

    /**
     * 树的高度
     * @return
     */
    public int height() {
        if (Objects.isNull(this.root)) {
            return 0;
        }
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.add(this.root);
        int height = 0;
        while (!queue.isEmpty()) {
            ++height;
            for (int i=0; i<queue.size(); i++) {
                Node<E> current = queue.poll();
                Node<E> left = current.left;
                if (Objects.nonNull(left)) {
                    queue.add(left);
                }
                Node<E> right = current.right;
                if (Objects.nonNull(right)) {
                    queue.add(right);
                }
            }
        }
        return height;
    }

    public int height(Node<E> node) {
        if (Objects.isNull(node)) {
            return 0;
        }
        //1 + 左右子树最大的高度 = 当前节点的高度
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * 判断是否为完全二叉树，从上往下从左到右，最后一层的叶子节点是往左对齐
     * @return
     */
    public boolean isComplete() {
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            boolean isLeaf = false;
            for (int i=0;i<queue.size(); i++) {
                //先查看，为空不用出队
                Node<E> current = queue.poll();
                if (isLeaf && !current.isLeaf) {
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
     * 前驱节点
     * @return
     */
    public Node<E> predecessor(E data) {
        Node<E> node = node(data);
        if (node == null) {
            return null;
        }
        Node<E> left = node.left;
        //左子树最大的节点（一直往右找）
        if (Objects.nonNull(left)) {
            Node<E> preNode = left.right;
            if (Objects.isNull(preNode)) {
                return left;
            }
            while (true) {
                if (Objects.isNull(preNode.right)) {
                    return preNode;
                }
                preNode = preNode.right;
            }
        }
        //往上node.parent.... = parent.right
        Node<E> parent = node.parent;
        while (Objects.nonNull(parent)) {
            if (parent.right == node) {
                return parent;
            }
            node = parent;
            parent = node.parent;
        }
        return null;
    }

    /**
     * 后继节点
     * @return
     */
    public Node<E> successor(E data) {
        Node<E> node = node(data);
        if (node == null) {
            return null;
        }
        Node<E> right = node.right;
        //右子树最大的节点（一直往左找）
        if (Objects.nonNull(right)) {
            Node<E> preNode = right.left;
            if (Objects.isNull(preNode)) {
                return right;
            }
            while (true) {
                if (Objects.isNull(preNode.left)) {
                    return preNode;
                }
                preNode = preNode.left;
            }
        }
        //往上node.parent.... = parent.left
        Node<E> parent = node.parent;
        while (Objects.nonNull(parent)) {
            if (parent.left == node) {
                return parent;
            }
            node = parent;
            parent = node.parent;
        }
        return null;
    }

    protected Node<E> createNode(E data, Node<E> parent) {
        return new Node<>(data, parent);
    }

}
