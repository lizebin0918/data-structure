package com.lzb.map;

import com.lzb.map.itf.Map;
import com.lzb.util.printer.BinaryTreeInfo;
import com.lzb.util.printer.BinaryTrees;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * hash表实现映射，数组+红黑树 <br/>
 * Created on : 2021-02-15 17:04
 *
 * @author lizebin
 */
public class MyHashMap<K, V> implements Map<K, V> {

    /**
     * 散列表:容量设置成2的n次方
     */
    private TreeNode<K, V>[] table;

    private static final int DEFAULT_CAPACITY = 16;

    private static final double FACTOR = 0.75d;

    private int threshold = 0;

    public MyHashMap() {
        table = new TreeNode[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * FACTOR);
    }

    private int size;

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
        int index = index(key);
        return node(table[index], key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedList<TreeNode<K, V>> queue = new LinkedList<>();
        if (table != null) {
            for (TreeNode<K, V> node : table) {
                if (node == null) {
                    continue;
                }
                queue.add(node);
                while (!queue.isEmpty()) {
                    node = queue.poll();
                    if (Objects.equals(node.value, value)) {
                        return true;
                    }
                    Optional.ofNullable(node.left).ifPresent(queue::add);
                    Optional.ofNullable(node.right).ifPresent(queue::add);
                }
            }
        }
        return false;
    }

    @Override
    public V put(K key, V value) {
        resize();
        int hash = (key == null ? 0 : key.hashCode());
        int index = index(key);
        TreeNode<K, V> node = table[index];

        ++size;
        if (Objects.isNull(node)) {
            table[index] = new TreeNode<>(key, value, null);
            addAfter(table[index]);
            return null;
        }

        TreeNode<K, V> exist = null;
        TreeNode<K, V> parent = node;

        int cmp = 0;
        boolean isSearch = false;
        while (node != null) {
            if (node.hash < hash) {
                cmp = 1;
            } else if (node.hash > hash) {
                cmp = -1;
            } else if (Objects.equals(node.key, key)) {
                exist = node;
            } else {
                //hash 相等 and equals() --> false
                if (key != null && node.key != null && key.getClass() == node.key.getClass() && key instanceof Comparable) {
                    cmp = ((Comparable<K>) key).compareTo(node.key);
                }
                //hash --> true and equals() --> false and class 相同 and Comparable.compareTo() = 0，
                //比如一个类Key，hashCode() -> value < 20 ? 1 : 0; equals() -> value == value;
                //当添加:map.put(new Key(2), "");map.put(new Key(1), "")，这时 System.identityHashCode(key1) > System.identityHashCode(node.key2)
                //所以放到右子树，如果再添加:map.put(new Key(1), "")，还是通过 System.identityHashCode() 比较，无法保证向右边找
                //只能左右子树遍历，看看是否存在相同的key，最后通过System.identityHashCode()判断放左还是放右。HashMap做法一样：
                /**
                 * // 先找
                 * if (!searched) {
                 *     TreeNode<K,V> q, ch;
                 *     searched = true;
                 *     if (((ch = p.left) != null &&
                 *          (q = ch.find(h, k, kc)) != null) ||
                 *         ((ch = p.right) != null &&
                 *          (q = ch.find(h, k, kc)) != null))
                 *         return q;
                 * }
                 *
                 * // 加时赛
                 * dir = tieBreakOrder(k, pk);
                 *
                 * static int tieBreakOrder(Object a, Object b) {
                 *   int d;
                 *   if (a == null || b == null ||
                 *       (d = a.getClass().getName().
                 *        compareTo(b.getClass().getName())) == 0)
                 *       d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
                 *            -1 : 1);
                 *   return d;
                 * }
                 *
                 */
                if (cmp == 0) {
                    //性能优化
                    if (!isSearch) {
                        exist = node(node, key);
                        isSearch = true;
                    }
                    if (exist == null) {
                        cmp = System.identityHashCode(node.key) > System.identityHashCode(key) ? 1 : -1;
                    }
                }
            }

            //替换
            if (exist != null) {
                V oldValue = exist.value;
                exist.key = key;
                exist.value = value;
                exist.hash = hash;
                return oldValue;
            }

            parent = node;
            node = cmp > 0 ? node.right : node.left;
        }

        TreeNode<K, V> newNode = new TreeNode<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        addAfter(newNode);
        return null;
    }

    /**
     * 扩容
     */
    private void resize() {
        if ((float)(size + 1) / table.length <= FACTOR) {
            return;
        }
        TreeNode<K, V>[] oldTable = table;
        table = new TreeNode[table.length << 1];
        //遍历所有节点，移动到新的table上
        LinkedList<TreeNode<K, V>> queue = new LinkedList<>();
        for (TreeNode<K, V> node : oldTable) {
            if (Objects.isNull(node)) {
                continue;
            }
            queue.add(node);
            while (!queue.isEmpty()) {
                TreeNode<K, V> subNode = queue.poll();
                TreeNode<K, V> left = subNode.left, right = subNode.right;
                if (Objects.nonNull(left)) {
                    queue.add(left);
                }
                if (Objects.nonNull(right)) {
                    queue.add(right);
                }
                move(subNode);
            }
        }
    }

    /**
     * 扩容移动节点
     * @param newNode
     */
    private void move(TreeNode<K, V> newNode) {
        //重置"亲戚"关系
        newNode.left = null;
        newNode.right = null;
        newNode.parent = null;
        red(newNode);
        //重新计算索引值
        int index = index(newNode.key);
        //复用put()放回对应位置
        TreeNode<K, V> root = table[index];
        if (Objects.isNull(root)) {
            table[index] = newNode;
            addAfter(table[index]);
            return;
        }

        TreeNode<K, V> parent = root.parent, node = root;
        K key = newNode.key;
        int hash = (key == null ? 0 : key.hashCode());
        int cmp = 0;
        boolean isSearch = false;
        while (node != null) {
            if (node.hash < hash) {
                cmp = 1;
            } else if (node.hash > hash) {
                cmp = -1;
            }
            //不会存在key.equals()相同的情况
            /*else if (Objects.equals(node.key, key)) {
                exist = node;
            } */
            else {
                cmp = System.identityHashCode(node.key) > System.identityHashCode(key) ? 1 : -1;
            }

            parent = node;
            node = cmp > 0 ? node.right : node.left;
        }

        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        addAfter(newNode);
    }

    private TreeNode<K, V> node(TreeNode<K, V> node, final K key) {
        if (Objects.isNull(node)) {
            return null;
        }
        int hash = (key == null ? 0 : key.hashCode());
        if (node.hash < hash) {
            return node(node.right, key);
        } else if (node.hash > hash) {
            return node(node.left, key);
        } else if (Objects.equals(node.key, key)) {
            return node;
        } else {
            //hash 相等 and equals() --> false
            if (key != null && node.key != null) {
                int cmp = 0;
                if (key != null && node.key != null && key.getClass() == node.key.getClass() && key instanceof Comparable) {
                    cmp = ((Comparable<K>) key).compareTo(node.key);
                }
                if (cmp > 0) {
                    return node(node.right, key);
                }
                if (cmp < 0) {
                    return node(node.left, key);
                }
                //hash 相等 and equals() --> false and class 相同 and Comparable.compareTo() = 0，只能左右子树遍历，看看是否存在相同的key
                TreeNode<K, V> result = null;
                if (node.left != null && (result = node(node.left, key)) != null) {
                    return result;
                }
                if (node.right != null && (result = node(node.right, key)) != null) {
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = index(key);
        TreeNode<K, V> root = table[index];
        if (root == null) {
            return null;
        }
        TreeNode<K, V> node = node(root, key);
        if (node == null) {
            return null;
        }

        V oldValue = node.value;
        --size;

        //度为2的节点
        if (Objects.nonNull(node.left) && Objects.nonNull(node.right)) {
            TreeNode<K, V> removeNode = predecessor(node);
            removeNode = Objects.isNull(removeNode) ? successor(node) : removeNode;
            //删除root
            if (Objects.isNull(removeNode)) {
                table[index] = null;
                return oldValue;
            }
            node.value = removeNode.value;
            node.hash = removeNode.hash;
            node.key = removeNode.key;
            node = removeNode;
        }

        //叶子（度为零）节点
        if (node.left == null && node.right == null) {
            TreeNode<K, V> parent = node.parent;
            if (parent == null) {
                table[index] = null;
                return oldValue;
            }
            if (parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            node.value = null;
            node.key = null;
            removeAfter(node);
            return oldValue;
        }

        //度为1的节点
        TreeNode<K, V> parent = node.parent;
        TreeNode<K, V> nextNode = Objects.nonNull(node.left) ? node.left : node.right;
        if (parent == null) {
            root = nextNode;
            nextNode.parent = null;
            removeAfter(node);
            return oldValue;
        }
        nextNode.parent = parent;
        if (parent.left == node) {
            parent.left = nextNode;
        } else {
            parent.right = nextNode;
        }
        node.value = null;
        node.key = null;
        removeAfter(nextNode);
        return oldValue;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        LinkedList<TreeNode<K, V>> queue = new LinkedList<>();
        for (TreeNode<K, V> node : table) {
            if (Objects.isNull(node)) {
                continue;
            }
            queue.add(node);
            while (!queue.isEmpty()) {
                node = queue.poll();
                set.add(node.key);
                TreeNode<K, V> left = node.left, right = node.right;
                if (Objects.nonNull(left)) {
                    queue.add(left);
                }
                if (Objects.nonNull(right)) {
                    queue.add(right);
                }
            }
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        LinkedList<V> values = new LinkedList<>();
        LinkedList<TreeNode<K, V>> queue = new LinkedList<>();
        for (TreeNode<K, V> node : table) {
            if (Objects.isNull(node)) {
                continue;
            }
            queue.add(node);
            while (!queue.isEmpty()) {
                node = queue.poll();
                values.add(node.value);
                TreeNode<K, V> left = node.left, right = node.right;
                if (Objects.nonNull(left)) {
                    queue.add(left);
                }
                if (Objects.nonNull(right)) {
                    queue.add(right);
                }
            }
        }
        return values;
    }


    @Override
    public V get(K key) {
        TreeNode<K, V> node = node(table[index(key)], key);
        return node == null ? null : node.value;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        LinkedList<TreeNode<K, V>> queue = new LinkedList<>();
        for (TreeNode<K, V> node : table) {
            if (Objects.isNull(node)) {
                continue;
            }
            queue.add(node);
            while (!queue.isEmpty()) {
                node = queue.poll();
                action.accept(node.key, node.value);
                TreeNode<K, V> left = node.left;
                if (left != null) {
                    queue.add(left);
                }
                TreeNode<K, V> right = node.right;
                if (right != null) {
                    queue.add(right);
                }
            }
        }
    }

    /**
     * 根据key生成对应的索引（在桶数组中的位置）
     */
    private int index(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    /*---------------------------------红黑树代码--------------------------------------*/

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    private static class TreeNode<K, V> {

        private boolean color = RED;

        private K key;
        private V value;
        private int hash;

        public TreeNode<K, V> left;
        public TreeNode<K, V> right;
        public TreeNode<K, V> parent;

        public TreeNode(K key, V value, TreeNode<K, V> parent) {
            this.value = value;
            this.key = key;
            this.parent = parent;
            this.hash = (key == null ? 0 : key.hashCode());
        }

        public TreeNode() {
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

        public TreeNode<K, V> sibling() {
            if (isLeft()) {
                return parent.right;
            }

            if (isRight()) {
                return parent.left;
            }

            return null;
        }

        public TreeNode<K, V> uncle() {
            if (parent != null) {
                return parent.sibling();
            }
            return null;
        }

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }

    private void addAfter(TreeNode<K, V> node) {
        TreeNode<K, V> parent = node.parent;

        //根节点染黑返回
        if (parent == null) {
            black(node);
            return;
        }

        //-父节点为黑色，直接添加 --> 4种情况
        if (isBlack(node.parent)) {
            return;
        }

        TreeNode<K, V> grand = parent.parent;

        //-红黑红，添加在红的两边，上溢（裂变），向上递归 --> 4种情况
        TreeNode<K, V> uncle = node.uncle();
        if (uncle != null && isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grand);
            addAfter(grand);
            return;
        }

        //通过旋转重新平衡
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

    /**
     * 染色
     *
     * @param node
     * @param color
     * @return
     */
    private TreeNode<K, V> color(TreeNode<K, V> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
        return node;
    }

    /**
     * 染黑
     *
     * @param node
     * @return
     */
    private TreeNode<K, V> black(TreeNode<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 染红
     *
     * @param node
     * @return
     */
    private TreeNode<K, V> red(TreeNode<K, V> node) {
        return color(node, RED);
    }

    /**
     * 节点颜色
     *
     * @param node
     * @return
     */
    private boolean colorOf(TreeNode<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(TreeNode<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(TreeNode<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 右旋
     *
     * @param node
     */
    private void rotateRight(TreeNode<K, V> node) {
        if (Objects.isNull(node)) {
            return;
        }

        TreeNode<K, V> newParent = node.left;
        TreeNode<K, V> parent = node.parent;

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
                table[index(node.key)] = newParent;
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
    private void rotateLeft(TreeNode<K, V> node) {

        if (Objects.isNull(node)) {
            return;
        }

        TreeNode<K, V> newParent = node.right;
        TreeNode<K, V> parent = node.parent;

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
                table[index(node.key)] = newParent;
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
     *
     * @return
     */
    private TreeNode<K, V> predecessor(TreeNode<K, V> node) {
        if (node == null) {
            return null;
        }
        TreeNode<K, V> left = node.left;
        //左子树最大的节点（一直往右找）
        if (Objects.nonNull(left)) {
            TreeNode<K, V> preNode = left.right;
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
        TreeNode<K, V> parent = node.parent;
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
     *
     * @return
     */
    private TreeNode<K, V> successor(TreeNode<K, V> node) {
        if (node == null) {
            return null;
        }
        TreeNode<K, V> right = node.right;
        //右子树最大的节点（一直往左找）
        if (Objects.nonNull(right)) {
            TreeNode<K, V> preNode = right.left;
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
        TreeNode<K, V> parent = node.parent;
        while (Objects.nonNull(parent)) {
            if (parent.left == node) {
                return parent;
            }
            node = parent;
            parent = node.parent;
        }
        return null;
    }

    private void removeAfter(TreeNode<K, V> node) {

     /*   if (node == root) {
            return;
        }*/

        //-如果节点为红色，直接删除
        //改成下面执行 --> 198行
        /*if (isRed(node)) {
            return;
        }*/

        //-如果删除的节点为黑色

        //"自己能搞定"
        //--黑（删）红（替代）
        //--红（替代）黑（删）
        //删除肯定发生在四阶B树的叶子节点，而且在二叉搜索树中，删除的节点的度要么0，要么1，找到对应的替代节点即可
        //这里有bug，如果是兄弟自损变红，递归删父节点，这时候发现有红色替代，则直接替代，有问题 -- 20200205
        //通过外层传参解决，当删除度=1的节点，可以视为删除叶子节点，所以入参的【node】实际是叶子节点，而非当前删除的节点，例如 2的左子节点为1，参数就传1的节点，不用传2的节点，而且两个节点的parent都一样
        /*TreeNode<K, V> replacement = node.left != null ? node.left : node.right != null ? node.right : null;
        if (isRed(replacement)) {
            black(replacement);
            return;
        }*/
        //如果删除节点为黑，传进来的node是替换节点（非删除节点），由红变黑
        //如果删除节点为红，传进来的node是删除节点，这个节点回被断开回收，多做一步染黑操作而已
        if (isRed(node)) {
            black(node);
            return;
        }

        //"兄弟是否能借，长兄为父，父亲下来顶儿子"
        //--兄弟为红，通过旋转变成找到黑兄弟
        boolean isLeft = node.parent.left == null || node.isLeft();
        TreeNode<K, V> sibling = isLeft ? node.parent.right : node.parent.left;
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
            TreeNode<K, V> grand = sibling.parent;
            //删除节点的路径上补充一个黑色节点
            color(grand, BLACK);
            TreeNode<K, V> siblingChild = isRed(sibling.right) ? sibling.right : sibling.left;

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

    public void print() {
        if (size == 0)
            return;
        for (int i = 0; i < table.length; i++) {
            final TreeNode<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object string(Object node) {
                    return node;
                }

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object right(Object node) {
                    return ((TreeNode<K, V>) node).right;
                }

                @Override
                public Object left(Object node) {
                    return ((TreeNode<K, V>) node).left;
                }
            });
            System.out.println("---------------------------------------------------");
        }
    }

}
