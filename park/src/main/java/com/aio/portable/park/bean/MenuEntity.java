package com.aio.portable.park.bean;

import com.aio.portable.swiss.suite.bean.node.tree.recursion.RecursiveTree;

import java.util.List;

public class MenuEntity implements RecursiveTree<MenuEntity, Integer> {
    private Integer id;
    private Integer parentId;
    private List<MenuEntity> itemList;

    public MenuEntity() {
    }

    public MenuEntity(Integer id, Integer parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public void setItemList(List<MenuEntity> itemList) {
        this.itemList = itemList;
    }

    @Override
    public List<MenuEntity> getItemList() {
        return this.itemList;
    }
}
