package com.aio.portable.swiss.bean.node;

import com.aio.portable.swiss.bean.node.next.layered.LayeredNextNode;
import com.aio.portable.swiss.bean.node.relation.tiled.TiledRelationNode;
import com.aio.portable.swiss.bean.node.next.tiled.TiledNextNode;
import com.aio.portable.swiss.bean.node.relation.RelationEquals;
import com.aio.portable.swiss.bean.node.relation.RelationNode;
import com.aio.portable.swiss.bean.node.relation.layered.LayeredRelationNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LinkedNodeWorld {
    public final static class Lst {
        /**
         * link2TiledNextNode
         * @param list
         * @param <T extends TileNextNode>
         * @return
         */
        public final static <T, R extends TiledNextNode> R link2TiledNextNode(List<T> list, Class<R> clazz) {
            R first;
            if (list.size() > 0) {
                T firstItem = list.get(0);
                first = TiledNextNode.newInstance(clazz, firstItem);

                R current = first;
                for (int i = 0; i < list.size() - 1; i++) {
                    T nextItem = list.get(i + 1);
                    if (nextItem == null) {
                        continue;
                    }
                    else {
                        R next = TiledNextNode.newInstance(clazz, nextItem);

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
        public final static <T, R extends LayeredNextNode<T>> R link2LayeredNextNode(List<T> list, Class<R> returnClazz) {
            R first;
            if (list.size() > 0) {
                T firstItem = list.get(0);
                first = LayeredNextNode.newInstance(returnClazz, firstItem);
//            first = NextNode.newInstance1.apply(clazz);

                R current = first;
                for (int i = 0; i < list.size() - 1; i++) {
                    T nextItem = list.get(i + 1);
                    if (nextItem == null) {
                        continue;
                    }
                    else {
                        R next = LayeredNextNode.newInstance(returnClazz, nextItem);
//                        next.setItem(nextItem);

                        current.setNext(next);
                        next.setPrev(current);

                        current = next;
                    }
                }
            } else
                first = LayeredNextNode.newInstance(returnClazz);
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
        public final static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>, R extends LayeredNextNode<ITEM>> List<R> link2LayeredNextNode(List<T> list, Class<R> returnClazz) {
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
        public final static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>, R extends LayeredNextNode<ITEM>> List<R> link2LayeredNextNode(List<T> list, RelationEquals relationEquals, Class<R> returnClazz) {
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
        public final static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>, R extends TiledNextNode> List<R> link2TiledNextNode(List<T> list, Class<R> returnClazz) {
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
        public final static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>, R extends TiledNextNode> List<R> link2TiledNextNode(List<T> list, RelationEquals relationEquals, Class<R> returnClazz) {
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
        public final static <ID, T extends TiledRelationNode<ID>, R extends LayeredNextNode<T>> List<R> link2LayeredNextNode(List<T> list, Class<R> returnClazz) {
            return link2LayeredNextNode(list, RelationEquals.OBJECTS_EQUALS, returnClazz);
        }

        public final static <ID, T extends TiledRelationNode<ID>, R extends LayeredNextNode<T>> List<R> link2LayeredNextNode(List<T> list, RelationEquals relationEquals, Class<R> returnClazz) {
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

        public final static <ID, T extends TiledRelationNode<ID>, R extends TiledNextNode> List<R> link2TiledNextNode(List<T> list, Class<R> returnClazz) {
            return link2TiledNextNode(list, RelationEquals.OBJECTS_EQUALS, returnClazz);
        }

        public final static <ID, T extends TiledRelationNode<ID>, R extends TiledNextNode> List<R> link2TiledNextNode(List<T> list, RelationEquals relationEquals, Class<R> returnClazz) {
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
            List<T> candidateTailList = list.stream().filter(c -> !c.tail()).collect(Collectors.toList());
            List<T> candidateHeadList = list.stream().collect(Collectors.toList());
            List<T> tailList = candidateTailList.stream().filter(tail -> candidateHeadList.stream().noneMatch(head -> relationEquals.equals(tail.getNextNodeId(), head.getNodeId()))).collect(Collectors.toList());
            List<T> nextIdList = list.stream().filter(c -> c.tail()).collect(Collectors.toList());
            tailList.addAll(nextIdList);
            return tailList;
        }

        private final static <T extends RelationNode<ID>, ID> List<T> getHeadList(List<T> list) {
            return getHeadList(list, RelationEquals.OBJECTS_EQUALS);
        }

        private final static <T extends RelationNode<ID>, ID> List<T> getHeadList(List<T> list, RelationEquals relationEquals) {
            List<T> candidateTailList = list.stream().filter(c -> !c.tail()).collect(Collectors.toList());
            List<T> candidateHeadList = list.stream().collect(Collectors.toList());
            List<T> headList = candidateHeadList.stream().filter(tail -> candidateTailList.stream().noneMatch(head -> relationEquals.equals(tail.getNextNodeId(), head.getNodeId()))).collect(Collectors.toList());
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
