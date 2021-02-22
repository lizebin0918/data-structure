package com.lzb.tree;

import java.util.HashMap;

/**
 * 字典树<br/>
 * Created on : 2021-02-22 21:27
 * @author lizebin
 */
public class Trie<V> {

    private int size;
    private Node<V> root = new Node<>();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
    }

    public V get(String key) {
        return null;
    }

    public boolean contains(String key) {
        return false;
    }

    public V add(String key, V value) {
        char[] array = key.toCharArray();
        Node<V> node = root;
        V oldValue = null;
        for (int i = 0, length = array.length; i < length; i++) {
            Character c = array[i];
            HashMap<Character, Node<V>> chidren = node.chidren;
            //孩子节点不存在
            if (chidren == null) {
                System.out.println("c = " + c);
                chidren = new HashMap<>();
                Node<V> newChildNode = new Node<>();
                chidren.put(c, newChildNode);
                node.chidren = chidren;
                if (i == length - 1) {
                    newChildNode.word = true;
                    newChildNode.value = value;
                    break;
                }
            }
            node = chidren.get(c);
            if (node != null && i == length - 1) {
                oldValue = node.value;
                node.value = value;
            }
        }
        return oldValue;
    }

    public V remove(String key) {
        return null;
    }

    public boolean startsWith(String prefix) {
        return false;
    }

    private static class Node<V> {
        /**
         * 存储对应字符，相当于树的连接线
         */
        HashMap<Character, Node<V>> chidren;
        /**
         * 存储对应的值
         */
        V value;
        /**
         * 是否为单词的结尾
         */
        boolean word = false;
    }

    public static void main(String[] args) {
        Trie<String> trie = new Trie<>();
        System.out.println(trie.add("lizebin", "me"));
    }

}
