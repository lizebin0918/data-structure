package com.lzb.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点<br/>
 * Created on : 2017-01-12 16:03
 * @author lizebin
 */
public class BinaryTree<T> {

    /**
     * 根据数据创建二叉树，返回根节点
     * @param array
     * @return
     */
    public TreeNode<T> createFromArray(T[] array) {
        List<TreeNode> nodes = new ArrayList<>(array.length);
        for(int i=0,length=array.length; i<length; i++) {
            TreeNode<T> node = new TreeNode<T>();
            node.value = array[i];
            nodes.add(node);
        }
        //遍历树节点，构造树
        int height = (int)(Math.log(array.length)/Math.log(2));
        for(int i=0; i<height; i++) {
            //下一层
            int start = (int)Math.pow(2, height);
            //for(int i=)

        }
        return nodes.get(0);
    }

    public static void main(String[] args) {
        System.out.println((int)Math.pow(2, 2));
        System.out.println((int)(Math.log(4)/Math.log(2)));
    }

    private static class TreeNode<T> {
        //左节点
        private TreeNode<T> left;
        //右节点
        private TreeNode<T> right;
        //值
        private T value;
    }

}
