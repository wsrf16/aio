package com.aio.portable.park.unit.node;

import com.aio.portable.swiss.suite.bean.node.tree.binary.TreeNode;
import com.aio.portable.swiss.suite.bean.node.LinkedNodeSugar;
import com.aio.portable.swiss.suite.bean.node.linked.ReferenceLinkedNode;
import com.aio.portable.swiss.suite.bean.node.relation.IDLinkedNode;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;


@TestComponent
public class NodeTest {

    public static class Bao {
        public Bao() {
        }

        public Bao(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void treeNode() {
        TreeNode treeNode1 = new TreeNode(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode treeNode2 = TreeNode.toBinaryTreeNode(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
    }





    private List<String> source1() {
        List<String> list = new ArrayList<>();
        list.add("我是一串文字A");
        list.add("我是一串文字B");
        list.add("我是一串文字C");
        list.add("我是一串文字D");
        list.add("我是一串文字E");
        return list;
    }

    @Test
    public void list() {
        List<String> source = source1();
        ReferenceLinkedNode<String> node = LinkedNodeSugar.buildOfList(source);
        return;
    }





    private List<IDLinkedNode<String, String>> source2() {
        IDLinkedNode u1 = new IDLinkedNode(new Bao("我是一串文字A"), 111, 222);
        IDLinkedNode u2 = new IDLinkedNode(new Bao("我是一串文字B"), 222, 333);
        IDLinkedNode u3 = new IDLinkedNode(new Bao("我是一串文字C"), 333, 444);
        IDLinkedNode u4 = new IDLinkedNode(new Bao("我是一串文字D"), 444, 555);
        IDLinkedNode u5 = new IDLinkedNode(new Bao("我是一串文字E"), 888, null);
        List<IDLinkedNode<String, String>> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);
        list.add(u5);
        return list;
    }

    @Test
    public void relationLinkedNode() {
        List<IDLinkedNode<String, String>> source = source2();
        List<ReferenceLinkedNode<String>> node1 = LinkedNodeSugar.buildOfRelationLinkedNode(source);
//            List<SimpleLinkedNode> node2 = LinkedNodeSugar.toSimpleLinkedNodes(source, Objects::equals, SimpleLinkedNode.class);
    }


}
