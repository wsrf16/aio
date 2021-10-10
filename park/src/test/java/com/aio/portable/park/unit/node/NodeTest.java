package com.aio.portable.park.unit.node;

import com.aio.portable.swiss.suite.bean.node.tree.BinaryTreeNode;
import com.aio.portable.swiss.suite.bean.node.LinkedNodeSugar;
import com.aio.portable.swiss.suite.bean.node.linked.ReferenceLinkedNode;
import com.aio.portable.swiss.suite.bean.node.relation.RelationLinkedNode;
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
        BinaryTreeNode binaryTreeNode1 = new BinaryTreeNode(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        BinaryTreeNode binaryTreeNode2 = BinaryTreeNode.toBinaryTreeNode(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
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





    private List<RelationLinkedNode<String, String>> source2() {
        RelationLinkedNode u1 = new RelationLinkedNode(new Bao("我是一串文字A"), 111, 222);
        RelationLinkedNode u2 = new RelationLinkedNode(new Bao("我是一串文字B"), 222, 333);
        RelationLinkedNode u3 = new RelationLinkedNode(new Bao("我是一串文字C"), 333, 444);
        RelationLinkedNode u4 = new RelationLinkedNode(new Bao("我是一串文字D"), 444, 555);
        RelationLinkedNode u5 = new RelationLinkedNode(new Bao("我是一串文字E"), 888, null);
        List<RelationLinkedNode<String, String>> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);
        list.add(u5);
        return list;
    }

    @Test
    public void relationLinkedNode() {
        List<RelationLinkedNode<String, String>> source = source2();
        List<ReferenceLinkedNode<String>> node1 = LinkedNodeSugar.buildOfRelationLinkedNode(source);
//            List<SimpleLinkedNode> node2 = LinkedNodeSugar.toSimpleLinkedNodes(source, Objects::equals, SimpleLinkedNode.class);
    }


}
