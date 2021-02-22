package com.lzb.tree;


import com.lzb.utils.printer.BinaryTreeInfo;

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
public class BalanceBinarySearchTree<E extends Comparable<? super E>> extends BinarySearchTree<E> implements BinaryTreeInfo {

    /**
     * 右旋
     * @param node
     */
    protected void rotateRight(Node<E> node) {
        if (Objects.isNull(node)) {
            return;
        }

        Node<E> newParent = node.left;
        Node<E> parent = node.parent;

        //先旋转
        node.left = newParent.right;
        newParent.right = node;

        //修改 parent
        {
            newParent.parent = parent;
            if (node.isLeft()) {
                node.parent.left = newParent;
            } else if (node.isRight()) {
                node.parent.right = newParent;
            } else {
                root = newParent;
            }
        }

        {
            node.parent = newParent;
        }

        {
            if (node.left != null) {
                node.left.parent = node;
            }
        }

    }

    /**
     * 左旋
     * @param node
     */
    protected void rotateLeft(Node<E> node) {

        if (Objects.isNull(node)) {
            return;
        }

        Node<E> newParent = node.right;
        Node<E> parent = node.parent;

        //旋转
        node.right = newParent.left;
        newParent.left = node;

        //修改 parent
        {
            newParent.parent = parent;
            if (node.isLeft()) {
                node.parent.left = newParent;
            } else if (node.isRight()) {
                node.parent.right = newParent;
            } else {
                root = newParent;
            }
        }

        {
            node.parent = newParent;
        }

        {
            if (node.right != null) {
                node.right.parent = node;
            }
        }


    }
}
