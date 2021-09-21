package com.aio.portable.swiss.suite.bean.node;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.suite.bean.node.linked.layered.LayeredLinkedNode;
import com.aio.portable.swiss.suite.bean.node.linked.tiled.TiledLinkedNode;
import com.aio.portable.swiss.suite.bean.node.relation.RelationEquals;
import com.aio.portable.swiss.suite.bean.node.relation.RelationNode;
//import com.aio.portable.swiss.suite.bean.node.relation.layered.LayeredRelationNode;
import com.aio.portable.swiss.suite.bean.node.relation.layered.LayeredRelationNode;
import com.aio.portable.swiss.suite.bean.node.relation.tiled.TiledRelationNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LinkedNodeSugar {
    public final static class Lst {
        /**
         * link2TiledNextNode
         * @param list
         * @param <T extends TileNextNode>
         * @return
         */
        public final static <T, R extends TiledLinkedNode> R link2TiledNextNode(List<T> list, Class<R> clazz) {
            R first;
            if (list.size() > 0) {
                T firstItem = list.get(0);
                first = TiledLinkedNode.newInstance(clazz, firstItem);

                R current = first;
                for (int i = 0; i < list.size() - 1; i++) {
                    T nextItem = list.get(i + 1);
                    if (nextItem == null) {
                        continue;
                    }
                    else {
                        R next = TiledLinkedNode.newInstance(clazz, nextItem);

                        current.setNext(next);
                        next.setPrev(current);

                        current = next;
                    }
                }
            } else {
                first = null;
            }
            return first;
        }

        /**
         * link2LayeredNextNode
         * @param list
         * @param returnClazz
         * @param <T>
         * @param <R extends LayeredNextNode<T>>
         * @return
         */
        public final static <T, R extends LayeredLinkedNode<T>> R link2LayeredNextNode(List<T> list, Class<R> returnClazz) {
            R first;
            if (list.size() > 0) {
                T firstItem = list.get(0);
                first = LayeredLinkedNode.newInstance(returnClazz, firstItem);
//            first = NextNode.newInstance1.apply(clazz);

                R current = first;
                for (int i = 0; i < list.size() - 1; i++) {
                    T nextItem = list.get(i + 1);
                    if (nextItem == null) {
                        continue;
                    }
                    else {
                        R next = LayeredLinkedNode.newInstance(returnClazz, nextItem);
//                        next.setItem(nextItem);

                        current.setNext(next);
                        next.setPrev(current);

                        current = next;
                    }
                }
            } else
                first = LayeredLinkedNode.newInstance(returnClazz);
            return first;
        }
    }





    public final static class LayeredRelation {
        /**
         * link2LayeredNextNode
         * @param list
         * @param returnClazz
         * @param <ID>
         * @param <T extends RelationNode<ID>>
         * @param <R extends LayeredNextNode<T>>
         * @return
         */
        public final static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>, R extends LayeredLinkedNode<ITEM>> List<R> link2LayeredNextNode(List<T> list, Class<R> returnClazz) {
            return link2LayeredNextNode(list, RelationEquals.OBJECTS_EQUALS, returnClazz);
        }

        /**
         * link2LayeredNextNode
         * @param list
         * @param relationEquals
         * @param returnClazz
         * @param <ID>
         * @param <T extends RelationNode<ID>>
         * @param <R extends LayeredNextNode<T>>
         * @return
         */
        public final static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>, R extends LayeredLinkedNode<ITEM>> List<R> link2LayeredNextNode(List<T> list, RelationEquals relationEquals, Class<R> returnClazz) {
            List<T> sourceList = list;
            List<T> tails = Utils.getTailList(sourceList, relationEquals);
            List<LinkedList<ITEM>> resultOneStep = new ArrayList<>(tails.size());

            for (int i = 0; i < tails.size(); i++) {
                T current = tails.get(i);
                LinkedList<ITEM> linkedList = new LinkedList<>();
                linkedList.offerFirst(current.getItem());
                resultOneStep.add(linkedList);

                while (true) {
                    ID byNextId = current.getNodeId();
                    T prev = Utils.findByNextId(sourceList, byNextId, relationEquals);
                    if (prev != null) {
                        linkedList.offerFirst(prev.getItem());
                        current = prev;
                    } else
                        break;
                }
            }

            List<R> resultTwoStep = resultOneStep.stream().map(c -> Lst.link2LayeredNextNode(c, returnClazz)).collect(Collectors.toList());
            return resultTwoStep;
        }

        /**
         * link2TiledNextNode
         * @param list
         * @param returnClazz
         * @param <ID>
         * @param <T extends RelationNode<ID>>
         * @param <R extends LayeredNextNode<T>>
         * @return
         */
        public final static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>, R extends TiledLinkedNode> List<R> link2TiledNextNode(List<T> list, Class<R> returnClazz) {
            return link2TiledNextNode(list, RelationEquals.OBJECTS_EQUALS, returnClazz);
        }

        /**
         * link2TiledNextNode
         * @param list
         * @param relationEquals
         * @param returnClazz
         * @param <ID>
         * @param <T extends RelationNode<ID>>
         * @param <R extends LayeredNextNode<T>>
         * @return
         */
        public final static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>, R extends TiledLinkedNode> List<R> link2TiledNextNode(List<T> list, RelationEquals relationEquals, Class<R> returnClazz) {
            List<T> sourceList = list;
            List<T> tails = Utils.getTailList(sourceList, relationEquals);
            List<LinkedList<ITEM>> resultOneStep = new ArrayList<>(tails.size());

            for (int i = 0; i < tails.size(); i++) {
                T current = tails.get(i);
                LinkedList<ITEM> linkedList = new LinkedList<>();
                resultOneStep.add(linkedList);
                linkedList.offer(current.getItem());

                while (true) {
                    ID byNextId = current.getNodeId();
                    T prev = Utils.findByNextId(sourceList, byNextId, relationEquals);
                    if (prev != null) {
                        linkedList.offerFirst(prev.getItem());
                        current = prev;
                    } else
                        break;
                }
            }

            List<R> resultTwoStep = resultOneStep.stream().map(c -> Lst.link2TiledNextNode(c, returnClazz)).collect(Collectors.toList());
            return resultTwoStep;
        }
    }





    public final static class TiledRelation {
        public final static <ID, T extends TiledRelationNode<ID>, R extends LayeredLinkedNode<T>> List<R> link2LayeredNextNode(List<T> list, Class<R> returnClazz) {
            return link2LayeredNextNode(list, RelationEquals.OBJECTS_EQUALS, returnClazz);
        }

        public final static <ID, T extends TiledRelationNode<ID>, R extends LayeredLinkedNode<T>> List<R> link2LayeredNextNode(List<T> list, RelationEquals relationEquals, Class<R> returnClazz) {
            List<T> sourceList = list;
            List<T> tails = Utils.getTailList(sourceList, relationEquals);
            List<LinkedList<T>> resultOneStep = new ArrayList<>(tails.size());

            for (int i = 0; i < tails.size(); i++) {
                T current = tails.get(i);
                LinkedList<T> linkedList = new LinkedList<>();
                linkedList.offerFirst(current);
                resultOneStep.add(linkedList);

                while (true) {
                    ID byNextId = current.getNodeId();
                    T prev = Utils.findByNextId(sourceList, byNextId, relationEquals);
                    if (prev != null) {
                        linkedList.offerFirst(prev);
                        current = prev;
                    } else
                        break;
                }
            }

            List<R> resultTwoStep = resultOneStep.stream().map(c -> Lst.link2LayeredNextNode(c, returnClazz)).collect(Collectors.toList());
            return resultTwoStep;
        }

        public final static <ID, T extends TiledRelationNode<ID>, R extends TiledLinkedNode> List<R> list2TiledNextNode(List<T> list, Class<R> returnClazz) {
            return list2TiledNextNode(list, RelationEquals.OBJECTS_EQUALS, returnClazz);
        }

        public final static <ID, T extends TiledRelationNode<ID>, R extends TiledLinkedNode> List<R> list2TiledNextNode(List<T> list, RelationEquals relationEquals, Class<R> returnClazz) {
            List<T> sourceList = list;
            List<T> tails = Utils.getTailList(sourceList, relationEquals);
            List<LinkedList<T>> resultOneStep = new ArrayList<>(tails.size());

            for (int i = 0; i < tails.size(); i++) {
                T current = tails.get(i);
                LinkedList<T> linkedList = new LinkedList<>();
                resultOneStep.add(linkedList);
                linkedList.offer(current);

                while (true) {
                    ID byNextId = current.getNodeId();
                    T prev = Utils.findByNextId(sourceList, byNextId, relationEquals);
                    if (prev != null) {
                        linkedList.offerFirst(prev);
                        current = prev;
                    } else
                        break;
                }
            }

            List<R> resultTwoStep = resultOneStep.stream().map(c -> Lst.link2TiledNextNode(c, returnClazz)).collect(Collectors.toList());
            return resultTwoStep;
        }
    }





    static class Utils {
        private final static <T extends RelationNode<ID>, ID> List<T> getTailList(List<T> list) {
            return getTailList(list, RelationEquals.OBJECTS_EQUALS);
        }

        private final static <T extends RelationNode<ID>, ID> List<T> getTailList(List<T> list, RelationEquals relationEquals) {
            List<T> nullNextList = list.stream().filter(c -> c.beTail()).collect(Collectors.toList());
            List<T> aloneNextList = list.stream()
                    .filter(c -> !c.beTail())
                    .filter(tail -> list.stream().noneMatch(head -> relationEquals.equals(tail.getNextNodeId(), head.getNodeId())))
                    .collect(Collectors.toList());
            List<T> tailList = CollectionSugar.concat(aloneNextList, nullNextList);
            return tailList;
        }
        // previous

        private final static <T extends RelationNode<ID>, ID> List<T> getPreviousList(List<T> list) {
            return getPreviousList(list, RelationEquals.OBJECTS_EQUALS);
        }

        private final static <T extends RelationNode<ID>, ID> List<T> getPreviousList(List<T> list, RelationEquals relationEquals) {
            List<T> full = list.stream().collect(Collectors.toList());
            List<T> candidateTailList = list.stream().filter(c -> !c.beTail()).collect(Collectors.toList());
            List<T> headList = full.stream().filter(tail -> candidateTailList.stream().noneMatch(head -> relationEquals.equals(tail.getNextNodeId(), head.getNodeId()))).collect(Collectors.toList());
            return headList;
        }

        private final static <T extends RelationNode<ID>, ID> T findByNodeId(List<T> list, ID id, final RelationEquals relationEquals) {
            T t = list.stream().filter(c -> relationEquals.equals(c.getNodeId(), id)).findFirst().orElse(null);
            return t;
        }

        private final static <T extends RelationNode<ID>, ID> T findByNextId(List<T> list, ID id, final RelationEquals relationEquals) {
            T t = list.stream().filter(c -> relationEquals.equals(c.getNextNodeId(), id)).findFirst().orElse(null);
            return t;
        }
    }


}
