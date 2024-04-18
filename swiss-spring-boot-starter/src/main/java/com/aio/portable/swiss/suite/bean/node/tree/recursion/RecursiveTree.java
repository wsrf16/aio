package com.aio.portable.swiss.suite.bean.node.tree.recursion;

import java.util.List;

public interface RecursiveTree<T, ID extends Number> {
    ID getId();
    ID getParentId();
    void setParentId(ID parentId);
    void setItemList(List<T> itemList);
    List<T> getItemList();



}
