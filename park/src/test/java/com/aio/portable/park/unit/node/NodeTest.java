package com.aio.portable.park.unit.node;

import com.aio.portable.swiss.suite.bean.node.LinkedNodeSugar;
import com.aio.portable.swiss.suite.bean.node.linked.layered.LayeredLinkedNode;
import com.aio.portable.swiss.suite.bean.node.relation.layered.LayeredRelationNode;
import com.aio.portable.swiss.suite.bean.node.linked.tiled.TiledLinkedNode;
import com.aio.portable.swiss.suite.bean.node.relation.tiled.TiledRelationNode;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    @TestComponent
    public static class Lst {
        private final List<String> source1() {
            List<String> list = new ArrayList<>();
            list.add("我是一串文字A");
            list.add("我是一串文字B");
            list.add("我是一串文字C");
            list.add("我是一串文字D");
            list.add("我是一串文字E");
            return list;
        }

        private final List<TiledLinkedNode> source2() {
            TiledLinkedNode m1 = new TiledLinkedNodeSample("我的A");
            TiledLinkedNode m2 = new TiledLinkedNodeSample("我的B");
            TiledLinkedNode m3 = new TiledLinkedNodeSample("我的C");
            TiledLinkedNode m4 = new TiledLinkedNodeSample("我的D");
            TiledLinkedNode m5 = new TiledLinkedNodeSample("我的E");
            List<TiledLinkedNode> list = new ArrayList<>();
            list.add(m1);
            list.add(m2);
            list.add(m3);
            list.add(m4);
            list.add(m5);
            return list;
        }

        @Test
        public void link2TiledNextNode() {
            List<TiledLinkedNode> source = source2();
            TiledLinkedNode node = LinkedNodeSugar.Lst.link2TiledNextNode(source, TiledLinkedNodeSample.class);
            return;
        }

        @Test
        public void link2LayeredNextNode() {
            List<String> source = source1();
            LayeredLinkedNode<String> node = LinkedNodeSugar.Lst.link2LayeredNextNode(source, LayeredLinkedNode.class);
            return;
        }
    }


    @TestComponent
    public static class TiledRelation {
        private List<TiledRelationNode> source() {
            TiledRelationNode u1 = new TiledRelationNodeSample( 111, 222,"我是一串文字A");
            TiledRelationNode u2 = new TiledRelationNodeSample( 222, 333,"我是一串文字B");
            TiledRelationNode u3 = new TiledRelationNodeSample( 333, 444,"我是一串文字C");
            TiledRelationNode u4 = new TiledRelationNodeSample( 444, 555,"我是一串文字D");
            TiledRelationNode u5 = new TiledRelationNodeSample( 555, 777,"我是一串文字E");
            TiledRelationNode u6 = new TiledRelationNodeSample( 999, null,"我是一串文字I");
            List<TiledRelationNode> list = new ArrayList<>();
            list.add(u1);
            list.add(u2);
            list.add(u3);
            list.add(u4);
            list.add(u5);
            list.add(u6);
            return list;
        }

        @Test
        public void link2TiledNextNode() {
            List<TiledRelationNode> source = source();
            List<TiledLinkedNodeSample> node1 = LinkedNodeSugar.TiledRelation.list2TiledNextNode(source, TiledLinkedNodeSample.class);
            List<TiledLinkedNodeSample> node2 = LinkedNodeSugar.TiledRelation.list2TiledNextNode(source, Objects::equals, TiledLinkedNodeSample.class);
//            LogFactory.singletonInstance().build().i(node1);
//            LogFactory.singletonInstance().build().i(node2);
        }

        @Test
        public void link2LayeredNextNode() {
            List<TiledRelationNode> source = source();
            List<LayeredLinkedNode> node1 = LinkedNodeSugar.TiledRelation.link2LayeredNextNode(source, LayeredLinkedNode.class);
            List<LayeredLinkedNode> node2 = LinkedNodeSugar.TiledRelation.link2LayeredNextNode(source, Objects::equals, LayeredLinkedNode.class);
//            LogFactory.singletonInstance().build().i(node1);
//            LogFactory.singletonInstance().build().i(node2);
        }
    }

    @TestComponent
    public static class LayeredRelation {
        private List<LayeredRelationNode> source() {
            LayeredRelationNode u1 = new LayeredRelationNode(new Bao("我是一串文字A"), 111, 222);
            LayeredRelationNode u2 = new LayeredRelationNode(new Bao("我是一串文字B"), 222, 333);
            LayeredRelationNode u3 = new LayeredRelationNode(new Bao("我是一串文字C"), 333, 444);
            LayeredRelationNode u4 = new LayeredRelationNode(new Bao("我是一串文字D"), 444, 555);
            LayeredRelationNode u5 = new LayeredRelationNode(new Bao("我是一串文字E"), 888, null);
            List<LayeredRelationNode> list = new ArrayList<>();
            list.add(u1);
            list.add(u2);
            list.add(u3);
            list.add(u4);
            list.add(u5);
            return list;
        }

        @Test
        public void link2TiledNextNode() {
            List<LayeredRelationNode> source = source();
            List<TiledLinkedNodeSample> node1 = LinkedNodeSugar.LayeredRelation.link2TiledNextNode(source, TiledLinkedNodeSample.class);
            List<TiledLinkedNodeSample> node2 = LinkedNodeSugar.LayeredRelation.link2TiledNextNode(source, Objects::equals, TiledLinkedNodeSample.class);
//            LogFactory.singletonInstance().build().i(node1);
//            LogFactory.singletonInstance().build().i(node2);
        }

        @Test
        public void link2LayeredNextNode() {
            List<LayeredRelationNode> source = source();
            List<LayeredLinkedNode> node1 = LinkedNodeSugar.LayeredRelation.link2LayeredNextNode(source, LayeredLinkedNode.class);
            List<LayeredLinkedNode> node2 = LinkedNodeSugar.LayeredRelation.link2LayeredNextNode(source, Objects::equals, LayeredLinkedNode.class);
//            LogFactory.singletonInstance().build().i(node1);
//            LogFactory.singletonInstance().build().i(node2);
        }
    }


}
