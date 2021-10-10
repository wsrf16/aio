package com.aio.portable.swiss.suite.bean.node.tree;

import java.util.*;

public class BinaryTreeNode<T> {
    private T data;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }





    public BinaryTreeNode(T t) {
        data = t;
    }

//    public BinaryTreeNode(T[] array) {
//        this(Arrays.asList(array));
//    }


    public final static  <T> BinaryTreeNode<T> toBinaryTreeNode(T[] array) {
        return toBinaryTreeNode(Arrays.asList(array));
    }

    public final static  <T> BinaryTreeNode<T> toBinaryTreeNode(List<T> list) {
        BinaryTreeNode<T> root = new BinaryTreeNode<>(list.get(0));
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (queue != null) {
            BinaryTreeNode vertex = queue.poll();
            if (list.get(i) != null) {
                vertex.left = new BinaryTreeNode(list.get(i));
                queue.offer(vertex.left);
            }
            if (++i >= list.size()) {
                break;
            }

            if (list.get(i) != null) {
                vertex.right = new BinaryTreeNode(list.get(i));
                queue.offer(vertex.right);
            }
            if (++i >= list.size()) {
                break;
            }
        }

        return root;
    }

}
