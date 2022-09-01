package com.aio.portable.swiss.suite.bean.node;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.suite.bean.node.linked.ReferenceLinkedNode;
import com.aio.portable.swiss.suite.bean.node.relation.IDEquals;
import com.aio.portable.swiss.suite.bean.node.relation.IDLinkedNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LinkedNodeSugar {
    /**
     * buildOfList
     * @param list
     * @param <T>
     * @return
     */
    public static final <T> ReferenceLinkedNode<T> buildOfList(List<T> list) {
        Class<ReferenceLinkedNode> returnClazz = ReferenceLinkedNode.class;
        ReferenceLinkedNode<T> first;
        if (list.size() > 0) {
            T firstItem = list.get(0);
            first = ReferenceLinkedNode.newInstance(returnClazz, firstItem);

            ReferenceLinkedNode<T> current = first;
            for (int i = 0; i < list.size() - 1; i++) {
                T nextItem = list.get(i + 1);
                if (nextItem == null) {
                    continue;
                }
                else {
                    ReferenceLinkedNode<T> next = ReferenceLinkedNode.newInstance(returnClazz, nextItem);

                    current.setNext(next);
                    next.setPrev(current);

                    current = next;
                }
            }
        } else
            first = ReferenceLinkedNode.newInstance(returnClazz);
        return first;
    }


    /**
     * buildOfRelationLinkedNode
     * @param list
     * @param <ITEM>
     * @param <ID>
     * @return
     */
    public static final <ITEM, ID> List<ReferenceLinkedNode<ITEM>> buildOfRelationLinkedNode(List<IDLinkedNode<ITEM, ID>> list) {
        return buildOfRelationLinkedNode(list, IDEquals.OBJECTS_EQUALS);
    }

    /**
     * buildOfRelationLinkedNode
     * @param list
     * @param IDEquals
     * @param <ID>
     * @return
     */
    private static final <ITEM, ID> List<ReferenceLinkedNode<ITEM>> buildOfRelationLinkedNode(List<IDLinkedNode<ITEM, ID>> list, IDEquals IDEquals) {
        List<IDLinkedNode<ITEM, ID>> tails = Utils.getTailList(list, IDEquals);
        List<LinkedList<ITEM>> resultOneStep = new ArrayList<>(tails.size());

        for (int i = 0; i < tails.size(); i++) {
            IDLinkedNode<ITEM, ID> current = tails.get(i);
            LinkedList<ITEM> linkedList = new LinkedList<>();
            linkedList.offerFirst(current.getValue());
            resultOneStep.add(linkedList);

            while (true) {
                ID byNextId = current.getId();
                IDLinkedNode<ITEM, ID> prev = Utils.findByNextId(list, byNextId, IDEquals);
                if (prev != null) {
                    linkedList.offerFirst(prev.getValue());
                    current = prev;
                } else
                    break;
            }
        }

        List<ReferenceLinkedNode<ITEM>> resultTwoStep = resultOneStep.stream().map(c -> buildOfList(c)).collect(Collectors.toList());
        return resultTwoStep;
    }












    private static class Utils {
        private static final <T extends IDLinkedNode<?, ID>, ID> List<T> getTailList(List<T> list) {
            return getTailList(list, IDEquals.OBJECTS_EQUALS);
        }

        private static final <T extends IDLinkedNode<?, ID>, ID> List<T> getTailList(List<T> list, IDEquals IDEquals) {
            List<T> nullNextList = list.stream().filter(c -> c.tail()).collect(Collectors.toList());
            List<T> aloneNextList = list.stream()
                    .filter(c -> !c.tail())
                    .filter(tail -> list.stream().noneMatch(head -> IDEquals.equals(tail.getNextId(), head.getId())))
                    .collect(Collectors.toList());
            List<T> tailList = CollectionSugar.concat(aloneNextList, nullNextList);
            return tailList;
        }

        private static final <T extends IDLinkedNode<?, ID>, ID> List<T> getPreviousList(List<T> list) {
            return getPreviousList(list, IDEquals.OBJECTS_EQUALS);
        }

        private static final <T extends IDLinkedNode<?, ID>, ID> List<T> getPreviousList(List<T> list, IDEquals IDEquals) {
            List<T> full = list.stream().collect(Collectors.toList());
            List<T> candidateTailList = list.stream().filter(c -> !c.tail()).collect(Collectors.toList());
            List<T> headList = full.stream().filter(tail -> candidateTailList.stream().noneMatch(head -> IDEquals.equals(tail.getNextId(), head.getId()))).collect(Collectors.toList());
            return headList;
        }

        private static final <T extends IDLinkedNode<?, ID>, ID> T findByNodeId(List<T> list, ID id, final IDEquals IDEquals) {
            T t = list.stream().filter(c -> IDEquals.equals(c.getId(), id)).findFirst().orElse(null);
            return t;
        }

        private static final <T extends IDLinkedNode<?, ID>, ID> T findByNextId(List<T> list, ID id, final IDEquals IDEquals) {
            T t = list.stream().filter(c -> IDEquals.equals(c.getNextId(), id)).findFirst().orElse(null);
            return t;
        }
    }


}
