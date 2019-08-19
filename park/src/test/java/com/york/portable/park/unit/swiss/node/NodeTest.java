package com.york.portable.park.unit.swiss.node;

import com.york.portable.park.common.CustomLogHubFactory;
import com.york.portable.swiss.bean.node.NodeUtils;
import com.york.portable.swiss.bean.node.next.layered.LayeredNextNode;
import com.york.portable.swiss.bean.node.next.layered.LayeredNextNodeBean;
import com.york.portable.swiss.bean.node.relation.RelationNode;
import com.york.portable.swiss.bean.node.relation.RelationNodeBean;
import com.york.portable.swiss.bean.node.next.tiled.TiledNextNode;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@TestComponent
public class NodeTest {

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
        public void pressIntoTiledNextNode() {
            List<TiledNextNode> source = source2();
            TiledNextNode tiledNextNode1 = NodeUtils.Lst.pressIntoTiledNextNode(source, TiledNextNodeSample.class);
            TiledNextNode tiledNextNode2 = NodeUtils.Lst.pressIntoTiledNextNode(source, TiledNextNodeSample.class);
        }

        @Test
        public void pressIntoLayeredNextNode() {
            List<String> source = source1();
            LayeredNextNode<String> node1 = NodeUtils.Lst.pressIntoLayeredNextNode(source, LayeredNextNodeBean.class);
            LayeredNextNode<String> node2 = NodeUtils.Lst.pressIntoLayeredNextNode(source, LayeredNextNodeBean.class);
        }
    }

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
    public static class Relation {
        private List<RelationNode> source() {

            RelationNode u1 = new RelationNodeBean(new Bao("我是一串文字A"), 111, 222);
            RelationNode u2 = new RelationNodeBean(new Bao("我是一串文字B"), 222, 333);
            RelationNode u3 = new RelationNodeBean(new Bao("我是一串文字C"), 333, 444);
            RelationNode u4 = new RelationNodeBean(new Bao("我是一串文字D"), 444, 555);
            RelationNode u5 = new RelationNodeBean(new Bao("我是一串文字E"), 888, null);
//            RelationNode u1 = new RelationNodeBean("我是一串文字A", 111, 222);
//            RelationNode u2 = new RelationNodeBean("我是一串文字B", 222, 333);
//            RelationNode u3 = new RelationNodeBean("我是一串文字C", 333, 444);
//            RelationNode u4 = new RelationNodeBean("我是一串文字D", 444, 555);
//            RelationNode u5 = new RelationNodeBean("我是一串文字E", 888, null);
            List<RelationNode> list = new ArrayList<>();
            list.add(u1);
            list.add(u2);
            list.add(u3);
            list.add(u4);
            list.add(u5);
            return list;
        }

        @Test
        public void pressIntoTiledNextNode() {
            List<RelationNode> source = source();
            List<TiledNextNodeSample> node1 = NodeUtils.Relation.pressIntoTiledNextNode(source, TiledNextNodeSample.class);
            List<TiledNextNodeSample> node2 = NodeUtils.Relation.pressIntoTiledNextNode(source, Objects::equals, TiledNextNodeSample.class);
            CustomLogHubFactory.singletonInstance().build().i(node1);
            CustomLogHubFactory.singletonInstance().build().i(node2);
        }

        @Test
        public void pressIntoLayeredNextNode() {
            List<RelationNode> source = source();
            List<LayeredNextNodeBean> node1 = NodeUtils.Relation.pressIntoLayeredNextNode(source, LayeredNextNodeBean.class);
            List<LayeredNextNodeBean> node2 = NodeUtils.Relation.pressIntoLayeredNextNode(source, Objects::equals, LayeredNextNodeBean.class);
            CustomLogHubFactory.singletonInstance().build().i(node1);
            CustomLogHubFactory.singletonInstance().build().i(node2);
        }
    }


}
