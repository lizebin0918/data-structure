package com.lzb.tree;

import java.util.HashMap;

/**
 * 字典树<br/>
 * Created on : 2021-02-22 21:27
 * @author lizebin
 */
public class Trie<V> {

    private int size;

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
        return null;
    }

    public V remove(String key) {
        return null;
    }

    public boolean startsWith(String prefix) {
        return false;
    }

    private static class Node<V> {
        HashMap<Character, Node<V>> chidren;
        /**
         * 存储对应的值
         */
        V value;
        /**
         * 是否为单词的结尾
         */
        boolean word;
    }


}
