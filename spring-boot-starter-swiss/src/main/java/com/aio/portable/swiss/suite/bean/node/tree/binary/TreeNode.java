package com.aio.portable.swiss.suite.bean.node.tree.binary;

import java.util.*;

public class TreeNode<T> {
    private T data;
    private TreeNode left;
    private TreeNode right;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }





    public TreeNode(T t) {
        data = t;
    }

//    public BinaryTreeNode(T[] array) {
//        this(Arrays.asList(array));
//    }


    public final static  <T> TreeNode<T> toBinaryTreeNode(T[] array) {
        return toBinaryTreeNode(Arrays.asList(array));
    }

    public final static  <T> TreeNode<T> toBinaryTreeNode(List<T> list) {
        TreeNode<T> root = new TreeNode<>(list.get(0));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (queue != null && queue.size() > 0) {
            TreeNode vertex = queue.poll();
            if (list.get(i) != null) {
                vertex.left = new TreeNode(list.get(i));
                queue.offer(vertex.left);
            }
            if (++i >= list.size()) {
                break;
            }

            if (list.get(i) != null) {
                vertex.right = new TreeNode(list.get(i));
                queue.offer(vertex.right);
            }
            if (++i >= list.size()) {
                break;
            }
        }

        return root;
    }

}
