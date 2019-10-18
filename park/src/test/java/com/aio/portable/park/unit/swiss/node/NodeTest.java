package com.aio.portable.park.unit.swiss.node;

import com.aio.portable.swiss.structure.bean.node.LinkedNodeSugar;
import com.aio.portable.swiss.structure.bean.node.next.layered.LayeredNextNode;
import com.aio.portable.swiss.structure.bean.node.next.layered.LayeredNextNodeBean;
import com.aio.portable.swiss.structure.bean.node.relation.layered.LayeredRelationNode;
import com.aio.portable.swiss.structure.bean.node.relation.layered.LayeredRelationNodeBean;
import com.aio.portable.swiss.structure.bean.node.next.tiled.TiledNextNode;
import com.aio.portable.swiss.structure.bean.node.relation.tiled.TiledRelationNode;
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

        private final List<TiledNextNode> source2() {
            TiledNextNodeSample m1 = new TiledNextNodeSample("我的A");
            TiledNextNodeSample m2 = new TiledNextNodeSample("我的B");
            TiledNextNodeSample m3 = new TiledNextNodeSample("我的C");
            TiledNextNodeSample m4 = new TiledNextNodeSample("我的D");
            TiledNextNodeSample m5 = new TiledNextNodeSample("我的E");
            List<TiledNextNode> list = new ArrayList<>();
            list.add(m1);
            list.add(m2);
            list.add(m3);
            list.add(m4);
            list.add(m5);
            return list;
        }

        @Test
        public void link2TiledNextNode() {
            List<TiledNextNode> source = source2();
            TiledNextNode node1 = LinkedNodeSugar.Lst.link2TiledNextNode(source, TiledNextNodeSample.class);
            TiledNextNode node2 = LinkedNodeSugar.Lst.link2TiledNextNode(source, TiledNextNodeSample.class);
        }

        @Test
        public void link2LayeredNextNode() {
            List<String> source = source1();
            LayeredNextNode<String> node1 = LinkedNodeSugar.Lst.link2LayeredNextNode(source, LayeredNextNodeBean.class);
            LayeredNextNode<String> node2 = LinkedNodeSugar.Lst.link2LayeredNextNode(source, LayeredNextNodeBean.class);
        }
    }


    @TestComponent
    public static class TiledRelation {
        private List<TiledRelationNode> source() {
            TiledRelationNode u1 = new TiledRelationNodeSample( 111, 222,"我是一串文字A");
            TiledRelationNode u2 = new TiledRelationNodeSample( 222, 333,"我是一串文字B");
            TiledRelationNode u3 = new TiledRelationNodeSample( 333, 444,"我是一串文字C");
            TiledRelationNode u4 = new TiledRelationNodeSample( 444, 555,"我是一串文字D");
            TiledRelationNode u5 = new TiledRelationNodeSample( 888, null,"我是一串文字E");
            List<TiledRelationNode> list = new ArrayList<>();
            list.add(u1);
            list.add(u2);
            list.add(u3);
            list.add(u4);
            list.add(u5);
            return list;
        }

        @Test
        public void link2TiledNextNode() {
            List<TiledRelationNode> source = source();
            List<TiledNextNodeSample> node1 = LinkedNodeSugar.TiledRelation.link2TiledNextNode(source, TiledNextNodeSample.class);
            List<TiledNextNodeSample> node2 = LinkedNodeSugar.TiledRelation.link2TiledNextNode(source, Objects::equals, TiledNextNodeSample.class);
//            LogFactory.singletonInstance().build().i(node1);
//            LogFactory.singletonInstance().build().i(node2);
        }

        @Test
        public void link2LayeredNextNode() {
            List<TiledRelationNode> source = source();
            List<LayeredNextNodeBean> node1 = LinkedNodeSugar.TiledRelation.link2LayeredNextNode(source, LayeredNextNodeBean.class);
            List<LayeredNextNodeBean> node2 = LinkedNodeSugar.TiledRelation.link2LayeredNextNode(source, Objects::equals, LayeredNextNodeBean.class);
//            LogFactory.singletonInstance().build().i(node1);
//            LogFactory.singletonInstance().build().i(node2);
        }
    }

    @TestComponent
    public static class LayeredRelation {
        private List<LayeredRelationNode> source() {
            LayeredRelationNode u1 = new LayeredRelationNodeBean(new Bao("我是一串文字A"), 111, 222);
            LayeredRelationNode u2 = new LayeredRelationNodeBean(new Bao("我是一串文字B"), 222, 333);
            LayeredRelationNode u3 = new LayeredRelationNodeBean(new Bao("我是一串文字C"), 333, 444);
            LayeredRelationNode u4 = new LayeredRelationNodeBean(new Bao("我是一串文字D"), 444, 555);
            LayeredRelationNode u5 = new LayeredRelationNodeBean(new Bao("我是一串文字E"), 888, null);
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
            List<TiledNextNodeSample> node1 = LinkedNodeSugar.LayeredRelation.link2TiledNextNode(source, TiledNextNodeSample.class);
            List<TiledNextNodeSample> node2 = LinkedNodeSugar.LayeredRelation.link2TiledNextNode(source, Objects::equals, TiledNextNodeSample.class);
//            LogFactory.singletonInstance().build().i(node1);
//            LogFactory.singletonInstance().build().i(node2);
        }

        @Test
        public void link2LayeredNextNode() {
            List<LayeredRelationNode> source = source();
            List<LayeredNextNodeBean> node1 = LinkedNodeSugar.LayeredRelation.link2LayeredNextNode(source, LayeredNextNodeBean.class);
            List<LayeredNextNodeBean> node2 = LinkedNodeSugar.LayeredRelation.link2LayeredNextNode(source, Objects::equals, LayeredNextNodeBean.class);
//            LogFactory.singletonInstance().build().i(node1);
//            LogFactory.singletonInstance().build().i(node2);
        }
    }


}
