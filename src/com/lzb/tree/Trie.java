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
        if (key == null) {
            throw new IllegalArgumentException("key is not null");
        }
        Node<V> node = node(key);
        if (node != null && node.word) {
            return node.value;
        }
        return null;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public V add(String key, V value) {
        if (key == null) {
            return null;
        }
        char[] array = key.toCharArray();
        Node<V> node = root;
        V oldValue = null;
        for (int i = 0, length = array.length; i < length; i++) {
            Character c = array[i];
            HashMap<Character, Node<V>> chidren = node.getChidren();
            //节点不存在，需要新增
            node = chidren.get(c);
            if (node == null) {
                Node<V> parent = node;
                node = new Node<>();
                node.parent = parent;
                chidren.put(c, node);
            }
        }
        if (!node.word) {
            ++size;
        }
        oldValue = node.value;
        node.value = value;
        node.word = true;
        return oldValue;
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public V remove(String key) {

        Node<V> node = node(key);
        if (node == null) {
            return null;
        }

        V oldValue = node.value;
        node.word = false;

        while (node != null) {
            if (node.getChidren().size() > 0) {
                break;
            }
            node.parent = null;
            node.value = null;
            node.chidren = null;
            node = node.parent;
        }

        return oldValue;
    }

    /**
     * 是否包含前缀
     * @param prefix
     * @return
     */
    public boolean startsWith(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix is not null");
        }
        char[] array = prefix.toCharArray();
        Node<V> node = root;
        boolean match = true;
        for (int i=0, length=array.length; i<length; i++) {
            char c = array[i];
            HashMap<Character, Node<V>> chidren = node.getChidren();
            if (chidren.isEmpty()) {
                match = false;
                break;
            }
            node = chidren.get(c);
            if (node == null) {
                match = false;
                break;
            }
        }
        return match;
    }

    private Node<V> node(String key) {
        char[] array = key.toCharArray();
        V value = null;
        Node<V> node = root;
        for (int i = 0, length = array.length; i < length; i++) {
            HashMap<Character, Node<V>> children = node.getChidren();
            if (children.isEmpty()) {
                return node;
            }
            node = children.get(array[i]);
            if (node == null) {
                return null;
            }
        }
        return node;
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
        /**
         * 父节点
         */
        Node<V> parent;

        public HashMap<Character, Node<V>> getChidren() {
            return chidren == null ? chidren = new HashMap<>() : chidren;
        }
    }

    public static void main(String[] args) {
        Trie<String> trie = new Trie<>();
        System.out.println(trie.add("ab", "me"));
        System.out.println(trie.add("ab1", "me1"));
        System.out.println(trie.add("ab2", "me2"));
        System.out.println(trie.add("abcdef", "me2"));

        System.out.println("----------");

        System.out.println(trie.size());

        System.out.println(trie.startsWith("abcdf"));
        System.out.println(trie.get("ab"));
        System.out.println(trie.get("ab1"));
        System.out.println(trie.get("ab2"));

        System.out.println(trie.remove("ab"));
        System.out.println(trie.get("ab"));

        System.out.println(trie.get("ab1"));
        System.out.println(trie.get("ab2"));
        System.out.println(trie.remove("abcdf"));
        System.out.println(trie.get("abcdef"));

    }

}
