package com.aio.portable.swiss.suite.bean.node.tree.recursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface RecursiveTree<T, ID extends Number> {
    ID getId();
    ID getParentId();
    void setParentId(ID parentId);
    void setItemList(List<T> itemList);
    List<T> getItemList();



}
