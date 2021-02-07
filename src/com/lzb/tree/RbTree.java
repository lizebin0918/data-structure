package com.lzb.tree;


import com.lzb.tree.printer.BinaryTreeInfo;
import com.lzb.tree.printer.BinaryTrees;

/**
 * 红黑树<br/>
 * Created on : 2021-01-31 23:10
 * @author lizebin
 */
public class RbTree<E extends Comparable<? super E>> extends BalanceBinarySearchTree<E> implements BinaryTreeInfo {

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    private static class RbNode<E> extends BinarySearchTree.Node<E> {

        private boolean color = RED;

        public RbNode(E data, boolean color, Node<E> parent) {
            this.data = data;
            this.parent = parent;
            this.color = color;
        }

        @Override
        public String toString() {
            return this.data + "(" + (color == RED ? "R" : "B") + ")";
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((RbNode<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((RbNode<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        return ((RbNode<E>)node).toString();
    }

    /**
     * 节点颜色
     * @param node
     * @return
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RbNode<E>)node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    /**
     * 染色
     * @param node
     * @param color
     * @return
     */
    private RbNode<E> color(Node<E> node, boolean color) {
        if (node != null) {
            ((RbNode<E>) node).color = color;
        }
        return (RbNode<E>) node;
    }

    /**
     * 染黑
     * @param node
     * @return
     */
    private RbNode<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    /**
     * 染红
     * @param node
     * @return
     */
    private RbNode<E> red(Node<E> node) {
        return color(node, RED);
    }


    @Override
    protected Node<E> createNode(E data, Node<E> parent) {
        return new RbNode<>(data, RED, parent);
    }

    /**
     * -红黑树（2-3-4树）添加都发生在叶子节点，一共12种情况
     * -4节点：左右叶子节点都可以添加，一共4种情况
     * -3节点：黑红（黑的左边，红的两边），红黑（红的量表，黑的右边），一共6种情况
     * -2节点：黑的两边添加
     * @param node
     */
    @Override
    protected void addAfter(Node<E> node) {
        Node<E> parent = node.parent;

        //根节点染黑返回
        if (node == root || parent == null) {
            black(node);
            return;
        }

        //-父节点为黑色，直接添加 --> 4种情况
        if (isBlack(node.parent)) {
            return;
        }

        Node<E> grand = parent.parent;

        //-红黑红，添加在红的两边，上溢（裂变），向上递归 --> 4种情况
        Node<E> uncle = node.uncle();
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

    /**
     * 四阶B树删除逻辑：自己是否有多？否->兄弟是否有多？否->父亲是否有多？否，那就“有福同享，有难同当”，父亲和兄弟自损相当于父亲和兄弟合成一个节点变成自己
     * (这里说的是四阶B树兄弟节点，不是红黑树的兄弟节点，因为四阶B树的兄弟节点都是以黑色节点为参照)
     *
     * 注意:先变色，再旋转（两步缺一不可），这样才能维持红黑树的性质，至于染成什么颜色，根据四阶B树性质可知。
     * 比如删除黑色节点，通过旋转补充的节点染黑，其他节点继承父亲颜色
     *
     * @param node 被删除节点或者取代被删除节点的子节点
     */
    @Override
    protected void removeAfter(Node<E> node) {

        if (node == root) {
            return;
        }

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
        /*Node<E> replacement = node.left != null ? node.left : node.right != null ? node.right : null;
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
        Node<E> sibling = isLeft ? node.parent.right : node.parent.left;
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
            Node<E> grand = sibling.parent;
            //删除节点的路径上补充一个黑色节点
            color(grand, BLACK);
            Node<E> siblingChild = isRed(sibling.right) ? sibling.right : sibling.left;

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

    public static void main(String[] args) {
        /*int[] i = {5,6,9,3,7,10,1,4,8,11,2};
        List<Integer> list = IntStream.of(i).boxed().collect(Collectors.toList());
        Collections.shuffle(list);
        System.out.println(list);*/

        int[] array = {8, 1, 15, 2, 3, 4, 7, 9, 6, 20, 11, 5};

        RbTree<Integer> tree = new RbTree<>();
        for (int i : array) {
            System.out.println("-------------" + i + "---------------");
            tree.add(i);
            BinaryTrees.print(tree);
            System.out.println("");
            System.out.println("---------------");
        }
        BinaryTrees.print(tree);
        System.out.println("");

        int delete = 3;
        System.out.println("------------------delete:" + delete);
        tree.remove(delete);
        BinaryTrees.print(tree);
        System.out.println("");

        delete = 7;
        System.out.println("------------------delete:" + delete);
        tree.remove(delete);
        BinaryTrees.print(tree);
        System.out.println("");

        //bugfix
        delete = 6;
        System.out.println("------------------delete:" + delete);
        tree.remove(delete);

        BinaryTrees.print(tree);
        System.out.println("");

        //bugfix
        delete = 2;
        System.out.println("------------------delete:" + delete);
        tree.remove(delete);

        BinaryTrees.print(tree);
        System.out.println("");

        //bugfix
        delete = 1;
        System.out.println("------------------delete:" + delete);
        tree.remove(delete);

        BinaryTrees.print(tree);
        System.out.println("");

        delete = 15;
        System.out.println("------------------delete:" + delete);
        tree.remove(delete);

        BinaryTrees.print(tree);
        System.out.println("");
    }
}
