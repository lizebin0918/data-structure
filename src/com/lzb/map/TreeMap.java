package com.lzb.map;

import com.lzb.map.itf.Map;
import com.lzb.tree.BinarySearchTree;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 底层是红黑树的TreeMap<br/>
 * Created on : 2021-02-07 22:27
 * @author lizebin
 */
public class TreeMap<K extends Comparable<? super K>, V> implements Map<K, V> {

    private int size;

    private Node<K, V> root;

    private static class Node<K extends Comparable<? super K>, V> {

        private static final boolean BLACK = true;
        private static final boolean RED = false;

        private boolean color = RED;

        private K key;
        private V value;

        public Node<K, V> left;
        public Node<K, V> right;
        public Node<K, V> parent;

        public int compareTo(Node<K, V> node) {
            return this.key.compareTo(node.key);
        }

        public Node(K key, V value, Node<K, V> parent) {
            if (Objects.isNull(key)) {
                throw new IllegalArgumentException("key 不能为空");
            }
            this.value = value;
            this.key = key;
            this.parent = parent;
        }

        public Node() {
        }

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

        public Node<K, V> sibling() {
            if (isLeft()) {
                return parent.right;
            }

            if (isRight()) {
                return parent.left;
            }

            return null;
        }


        public Node<K, V> uncle() {
            if (parent != null) {
                return parent.sibling();
            }
            return null;
        }

        /**
         * 节点颜色
         * @param node
         * @return
         */
        private boolean colorOf(Node<K, V> node) {
            return node == null ? BLACK : ((Node<K, V>)node).color;
        }

        private boolean isBlack(Node<K, V> node) {
            return colorOf(node) == BLACK;
        }

        private boolean isRed(Node<K, V> node) {
            return colorOf(node) == RED;
        }

        /**
         * 染色
         * @param node
         * @param color
         * @return
         */
        private Node<K, V> color(Node<K, V> node, boolean color) {
            if (node != null) {
                ((Node<K, V>) node).color = color;
            }
            return (Node<K, V>) node;
        }

        /**
         * 染黑
         * @param node
         * @return
         */
        private Node<K, V> black(Node<K, V> node) {
            return color(node, BLACK);
        }

        /**
         * 染红
         * @param node
         * @return
         */
        private Node<K, V> red(Node<K, V> node) {
            return color(node, RED);
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        return false;
    }

    /**
     * 0.找到父节点 parent
     * 1.创建新节点 node
     * 2.parent.left = node 或者 parent.right = node
     *
     * 注意：如果遇到相同的值，需要覆盖替换成新的对象
     * @param key
     * @param value
     * @return 返回被替换的值
     */
    @Override
    public V put(K key, V value) {
            if (key == null) {
                throw new IllegalArgumentException("data not null");
            }
            if (root == null) {
                root = new Node<>(key, value, null);
                addAfter(root);
                size++;
                return null;
            }
            Node<K, V> newNode = null;
            Node<K, V> parent = root;
            while (true) {
                if (compare(parent.key, key) > 0) {
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

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    /**
     * 新增完成，维持平衡
     * @param node
     */
    private void addAfter(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        //根节点染黑返回
        if (node == root || parent == null) {
            black(node);
            return;
        }

        //-父节点为黑色，直接添加 --> 4种情况
        if (isBlack(node.parent)) {
            return;
        }

        BinarySearchTree.Node<E> grand = parent.parent;

        //-红黑红，添加在红的两边，上溢（裂变），向上递归 --> 4种情况
        BinarySearchTree.Node<E> uncle = node.uncle();
        if (uncle != null && isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grand);
            addAfter(grand);
            return;
        }

        //-黑红，添加在红的两边 --> 2种情况
        //-红黑，添加在红的两边 --> 2种情况
        red(grand);
        if (parent.isLeft()) {
            if (node.isRight()) {
                //作为新的根节点应该染黑
                black(node);
                rotateLeft(parent);
            } else {
                black(parent);
            }
            rotateRight(grand);
        } else {
            if (node.isLeft()) {
                //作为新的根节点应该染黑
                black(node);
                rotateRight(parent);
            } else {
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private int compare(K k1, K k2) {
        if (k1 == null) {
            throw new IllegalArgumentException("key is null");
        }
        return k1.compareTo(k2);
    }

    public static void main(String[] args) {
        TreeMap<String, String> tm = new TreeMap<>();
        tm.put("a", "b");


    }
}
