package com.lzb.map;

import com.lzb.map.itf.Map;

import java.util.*;
import java.util.function.*;

/**
 * 底层是红黑树的TreeMap<br/>
 * Created on : 2021-02-07 22:27
 *
 * @author lizebin
 */
public class TreeMap<K extends Comparable<? super K>, V> implements Map<K, V> {

    private int size;

    private Node<K, V> root;

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    private static class Node<K extends Comparable<? super K>, V> {

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
        return get(key) != null;
    }

    /**
     * 层遍历
     * @param value
     * @return
     */
    @Override
    public boolean containsValue(V value) {
        if (Objects.isNull(root)) {
            return false;
        }
        LinkedList<Node<K, V>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (Objects.equals(node.value, value)) {
                return true;
            }
            Optional.ofNullable(node.left).ifPresent(queue::add);
            Optional.ofNullable(node.right).ifPresent(queue::add);
        }
        return false;
    }

    /**
     * 0.找到父节点 parent
     * 1.创建新节点 node
     * 2.parent.left = node 或者 parent.right = node
     * <p>
     * 注意：如果遇到相同的值，需要覆盖替换成新的对象
     *
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
                Node<K, V> left = parent.left;
                if (left == null) {
                    newNode = new Node<>(key, value, parent);
                    parent.left = newNode;
                    break;
                }
                parent = left;
                continue;
            } else if (compare(parent.key, key) < 0) {
                Node<K, V> right = parent.right;
                if (right == null) {
                    newNode = new Node<>(key, value, parent);
                    parent.right = newNode;
                    break;
                }
                parent = right;
                continue;
            } else {
                parent.key = key;
                parent.value = value;
                return parent.value;
            }
        }
        size++;

        if (newNode != null) {
            addAfter(newNode);
        }

        return null;
    }

    @Override
    public V remove(K key) {
        Node<K, V> node = getNode(key);
        if (Objects.isNull(node)) {
            return null;
        }

        --size;
        V value = node.value;

        //度为2的节点
        if (Objects.nonNull(node.left) && Objects.nonNull(node.right)) {
            Node<K, V> removeNode = predecessor(key);
            removeNode = Objects.isNull(removeNode) ? successor(key) : removeNode;
            node.key = removeNode.key;
            node.value = removeNode.value;
            node = removeNode;
        }

        //叶子（度为零）节点
        if (node.left == null && node.right == null) {
            Node<K, V> parent = node.parent;
            if (parent == null) {
                root = null;
                return value;
            }
            if (parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            node.key = null;
            node.value = null;
            removeAfter(node);
            return value;
        }

        //度为1的节点
        Node<K, V> parent = node.parent;
        Node<K, V> nextNode = Objects.nonNull(node.left) ? node.left : node.right;
        if (parent == null) {
            root = nextNode;
            nextNode.parent = null;
            removeAfter(node);
            return value;
        }
        nextNode.parent = parent;
        if (parent.left == node) {
            parent.left = nextNode;
        } else {
            parent.right = nextNode;
        }
        node.key = null;
        node.value = null;
        removeAfter(nextNode);
        return value;
    }

    private void removeAfter(Node<K, V> node) {
        if (node == root) {
            return;
        }

        //如果删除节点为黑，传进来的node是替换节点（非删除节点），由红变黑
        //如果删除节点为红，传进来的node是删除节点，这个节点回被断开回收，多做一步染黑操作而已
        if (isRed(node)) {
            black(node);
            return;
        }

        //"兄弟是否能借，长兄为父，父亲下来顶儿子"
        //--兄弟为红，通过旋转变成找到黑兄弟
        boolean isLeft = node.parent.left == null || node.isLeft();
        Node<K, V> sibling = isLeft ? node.parent.right : node.parent.left;
        if (isRed(sibling)) {
            //染黑，升级。sibling.parent 下降需要染红减少一个黑，维持两边平衡
            black(sibling);
            red(sibling.parent);
            if (sibling.isLeft()) {
                rotateRight(sibling.parent);
            } else {
                rotateLeft(sibling.parent);
            }
            sibling = isLeft ? node.parent.right : node.parent.left;
        }

        //--黑兄弟的度 >= 1
        if (isRed(sibling.right) || isRed(sibling.left)) {
            Node<K, V> grand = sibling.parent;
            //删除节点的路径上补充一个黑色节点
            color(grand, BLACK);
            Node<K, V> siblingChild = isRed(sibling.right) ? sibling.right : sibling.left;

            if (sibling.isLeft()) {
                if (siblingChild.isRight()) {
                    color(siblingChild, colorOf(sibling.parent));
                    rotateLeft(sibling);
                } else {
                    //继承父节点的颜色
                    color(siblingChild, colorOf(sibling));
                    color(sibling, colorOf(sibling.parent));
                }
                rotateRight(grand);
            } else {
                if (siblingChild.isLeft()) {
                    color(siblingChild, colorOf(sibling.parent));
                    rotateRight(sibling);
                } else {
                    //继承父节点的颜色
                    color(siblingChild, colorOf(sibling));
                    color(sibling, colorOf(sibling.parent));
                }
                rotateLeft(grand);
            }
            return;
        }

        //"兄弟借不了，只能有难同当，兄弟自损，父亲下来顶孩子"
        //--黑兄弟的度 = 0
        color(sibling, RED);
        if (isRed(sibling.parent)) {
            color(sibling.parent, BLACK);
            return;
        }

        removeAfter(sibling.parent);
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 中序遍历
     * @return
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        inorderTraversal(root, (n) -> {
            set.add(n.key);
        });
        return set;
    }

    private void inorderTraversal(Node<K, V> node, Consumer<Node<K, V>> consumer) {
        if (Objects.isNull(node)) {
            return;
        }
        inorderTraversal(node.left, consumer);
        consumer.accept(node);
        inorderTraversal(node.right, consumer);
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<>();
        inorderTraversal(root, (n) -> {
            set.add(n.value);
        });
        return set;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = getNode(key);
        if (Objects.nonNull(node)) {
            return node.value;
        }
        return null;
    }

    /**
     * 查询节点
     * @param key
     * @return
     */
    private Node<K, V> getNode(K key) {
        if (Objects.isNull(root)) {
            return null;
        }
        Node<K, V> node = root;
        while (node != null) {
            K k = node.key;
            int comp = compare(key, k);
            if (comp > 0) {
                node = node.right;
            } else if (comp < 0) {
                node = node.left;
            } else {
                return node;
            }
        }
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        LinkedList<Node<K, V>> queue = new LinkedList<>();
        queue.add(root);
        Node<K, V> node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            action.accept(node.key, node.value);
            Node<K, V> left = node.left;
            if (left != null) {
                queue.add(left);
            }
            Node<K, V> right = node.right;
            if (right != null) {
                queue.add(right);
            }
        }
    }

    /**
     * 新增完成，维持平衡
     *
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

        Node<K, V> grand = parent.parent;

        //-红黑红，添加在红的两边，上溢（裂变），向上递归 --> 4种情况
        Node<K, V> uncle = node.uncle();
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
        if (k1 == null || k2 == null) {
            throw new IllegalArgumentException("key is null");
        }
        return k1.compareTo(k2);
    }

    /**
     * 节点颜色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : ((Node<K, V>) node).color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 染色
     *
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
     *
     * @param node
     * @return
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 染红
     *
     * @param node
     * @return
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 右旋
     *
     * @param node
     */
    private void rotateRight(Node<K, V> node) {
        if (Objects.isNull(node)) {
            return;
        }

        Node<K, V> newParent = node.left;
        Node<K, V> parent = node.parent;

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
     *
     * @param node
     */
    private void rotateLeft(Node<K, V> node) {

        if (Objects.isNull(node)) {
            return;
        }

        Node<K, V> newParent = node.right;
        Node<K, V> parent = node.parent;

        //旋转
        node.right = newParent.left;
        newParent.left = node;

        //修改 parent : node, node.right, node.parent
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

    /**
     * 前驱节点
     * @return
     */
    private Node<K, V> predecessor(K data) {
        Node<K, V> node = getNode(data);
        if (node == null) {
            return null;
        }
        Node<K, V> left = node.left;
        //左子树最大的节点（一直往右找）
        if (Objects.nonNull(left)) {
            Node<K, V> preNode = left.right;
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
        Node<K, V> parent = node.parent;
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
    private Node<K, V> successor(K data) {
        Node<K, V> node = getNode(data);
        if (node == null) {
            return null;
        }
        Node<K, V> right = node.right;
        //右子树最大的节点（一直往左找）
        if (Objects.nonNull(right)) {
            Node<K, V> preNode = right.left;
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
        Node<K, V> parent = node.parent;
        while (Objects.nonNull(parent)) {
            if (parent.left == node) {
                return parent;
            }
            node = parent;
            parent = node.parent;
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        TreeMap<String, String> tm = new TreeMap<>();
        tm.put("a", "a" + "1");
        tm.put("a", "a" + "2");
        tm.put("a", "a" + "3");
        tm.put("a", "a" + "4");
        tm.put("b", "b" + "1");
        tm.put("c", "c" + "1");
        tm.put("d", "d" + "1");

        System.out.println(tm.get("a"));
        System.out.println(tm.get("b"));
        System.out.println(tm.get("c"));
        System.out.println(tm.keySet());
        System.out.println(tm.values());

        tm.forEach((k, v) -> {
            System.out.println("k=" + k + ";v=" + v);
        });

        /*ExecutorService threadPool = Executors.newFixedThreadPool(2);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                //ignore
            }
            System.out.println("shutdown threadPool");
            threadPool.shutdown();
        }).start();

        int tasks = 10;
        CountDownLatch latch = new CountDownLatch(tasks);
        for (int i=0; i<tasks; i++) {
            int taskId = i;
            Random r = new Random();
            System.out.println(" add task:" + taskId);
            threadPool.execute(() -> {
                System.out.println("----------------" + " start " + taskId + "-------------------");
                try {
                    TimeUnit.SECONDS.sleep(6 + r.nextInt(5));
                } catch (InterruptedException e) {
                    //ignore
                }
                System.out.println("----------------" + " end " + taskId + "-------------------");
                latch.countDown();
            });
        }

        latch.await();

        System.out.println("finish");*/

    }
}
