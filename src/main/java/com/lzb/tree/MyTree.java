package com.lzb.tree;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 树<br/>
 * 名词：节点、根节点、父节点、子节点、叶子节点（没有子节点的节点）、节点的权（节点值）、路径（从root节点找到该节点的路线）、层、子树、树的高度（最大层数）、节点的度-某个节点的子树个数、树的度-所有节点中，度的最大值
 * 前序遍历：先输出当前节点(root)，再输出左子节点，再遍历又子节点
 * 中序遍历:
 * 后续遍历:
 *
 * 1.二叉树的增删查改
 * 2.给定一个根节点，返回该树的高度
 *
 * Created on : 2021-01-01 11:19
 * @author lizebin
 */
public class MyTree {

    public static class TreeNode<String> {
        public TreeNode<String> left;
        public TreeNode<String> right;
        public String data;

        public TreeNode(TreeNode<String> left, TreeNode<String> right, String data) {
            this.left = left;
            this.right = right;
            this.data = data;
        }

        /**
         * 树的高度(深度优先)
         * @return
         */
        public int height() {
            int leftHeight = left == null ? 0 : left.height();
            int rightHeight = right == null ? 0 : right.height();
            return Math.max(leftHeight, rightHeight) + 1;
        }

        public int leftHeight() {
            if (Objects.isNull(left)) {
                return 0;
            }
            return left.height();
        }

        public void preTraverse() {
            System.out.println(this.data);
            if (left == null) {
                return;
            }
            left.preTraverse();
            if (right == null) {
                return;
            }
            right.preTraverse();
        }
    }

    /**
     * 返回一棵【满】二叉树
     * @param root
     * @param currentLength
     * @param maxLength
     * @return
     */
    public void createBinaryTree(TreeNode<String> root, int currentLength, int maxLength) {
        if (currentLength == maxLength) {
            return;
        }
        String rootData = root.data;
        root.left = new TreeNode<>(null, null, rootData + "-" + (1 + currentLength) + "-left");
        root.right = new TreeNode<>(null, null, rootData + "-" + (1 + currentLength) + "-right");
        createBinaryTree(root.left, currentLength + 1, maxLength);
        createBinaryTree(root.right, currentLength + 1, maxLength);
    }

    /**
     * 根据数组生成树，前序遍历读取
     * @param array
     */
    public TreeNode<String> createBinaryTree(String[] array) {
        if (Objects.isNull(array) || array.length == 0) {
            return null;
        }
        TreeNode<String> root = new TreeNode<>(null, null, array[0]);
        createSubBinaryTree(root, 0, array);
        return root;
    }

    /**
     * 创建子树
     * @param parent 父节点
     * @param i 父节点索引
     * @param array 数组
     */
    public void createSubBinaryTree(TreeNode<String> parent, int i, String[] array) {
        if (i >= array.length) {
            return;
        }
        int leftIndex = 2 * i + 1, rightIndex = leftIndex + 1;
        if (leftIndex >= array.length) {
            return;
        }
        TreeNode<String> left = new TreeNode<>(null, null, array[leftIndex]);
        parent.left = left;
        createSubBinaryTree(left, leftIndex, array);
        if (rightIndex >= array.length) {
            return;
        }
        TreeNode<String> right = new TreeNode<>(null, null, array[rightIndex]);
        parent.right = right;
        createSubBinaryTree(right, rightIndex, array);
    }

    /**
     * （深度遍历）前序遍历
     * @param root
     */
    public void preOrderTraverse(TreeNode<String> root) {
        if (Objects.isNull(root)) {
            return;
        }
        System.out.println(root.data);
        TreeNode<String> left = root.left;
        TreeNode<String> right = root.right;
        preOrderTraverse(left);
        preOrderTraverse(right);
    }

    /**
     * （深度遍历）中序遍历（二叉搜索树用中序遍历可实现排序）
     * @param root
     */
    public void midOrderTraverse(TreeNode<String> root) {
        if (Objects.isNull(root)) {
            return;
        }
        midOrderTraverse(root.left);
        System.out.println(root.data);
        midOrderTraverse(root.right);
    }

    /**
     * 递归查询树的高度
     * @param root
     * @return
     */
    public int height(TreeNode<String> root) {
        int depth = 0;
        LinkedList<TreeNode<String>> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            ++depth;
            for (int i = 0, n = q.size(); i < n; ++i) {
                TreeNode<String> p = q.poll();

                if (p.left != null) {
                    q.offer(p.left);
                }
                if (p.right != null) {
                    q.offer(p.right);
                }
            }
        }
        return depth;
    }

    /**
     * 层遍历
     * @param root
     */
    public static void layerTraverse(TreeNode<String> root) {
        if (Objects.isNull(root)) {
            return;
        }
        LinkedList<TreeNode<String>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            for (int i=queue.size(); i>0; i--) {
                TreeNode<String> node = queue.poll();
                System.out.print(node.data);
                TreeNode<String> left = node.left;
                if (Objects.nonNull(left)) {
                    queue.add(left);
                }
                TreeNode<String> right = node.right;
                if (Objects.nonNull(right)) {
                    queue.add(right);
                }
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        MyTree myTree = new MyTree();
        TreeNode<String> root = new TreeNode<>(null, null, "root");
        myTree.createBinaryTree(root, 0, 3);

        //System.out.println(root.height());
        root.preTraverse();

        System.out.println("----------------");

        String[] array = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
        root = myTree.createBinaryTree(array);
        System.out.println("height = " + root.height());
        //myTree.preOrderTraverse(root);

        System.out.println("height = " + myTree.height(root));
        myTree.preOrderTraverse(root);

        System.out.println("------------中序遍历-----------");
        myTree.midOrderTraverse(root);

        layerTraverse(root);
    }

}
