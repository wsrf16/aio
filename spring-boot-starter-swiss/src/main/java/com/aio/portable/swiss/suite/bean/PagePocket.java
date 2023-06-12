package com.aio.portable.swiss.suite.bean;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by York on 2017/11/23.
 */
public class PagePocket<T> {
    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public int getPageCapacity() {
        return pageCapacity;
    }

    public List<T> getCurrentPageItems() {
        return currentPageItems;
    }

    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 总数量
     */
    private int totalCount;
    /**
     * 全部item
     */
    private List<T> totalItems;
    /**
     * 当前页
     */
    private int pageIndex;
    /**
     * 当前页item数量
     */
    private int currentPageSize;
    /**
     * 当前页item容量
     */
    private int pageCapacity;
    /**
     * 当前页item
     */
    private List<T> currentPageItems;

    private List<T> currentPageItems() {
        List<T> cutLeft = totalItems.subList((pageIndex - 1) * pageCapacity, totalItems.size());
        if (cutLeft.size() < pageCapacity)
            return cutLeft;
        else
            return cutLeft.subList(0, pageCapacity);
    }

    public static final <T> PagePocket<T> paging(List<T> list, int size, int index) {
        PagePocket<T> instance = new PagePocket<>();
        instance.totalItems = list;
        instance.pageCapacity = size;
        instance.pageIndex = index;
        int totalCount = list.size();
        instance.totalCount = totalCount;

        instance.totalPages = totalCount / size + (totalCount / size + totalCount % size == 0 ? 0 : 1);
        instance.currentPageSize = instance.currentPageItems().size();
        instance.currentPageItems = instance.currentPageItems();
        return instance;
    }

    public static final <T> PagePocket<T> paging(List<T> list, int size) {
        PagePocket<T> instance = new PagePocket<>();
        instance.totalItems = list;
        instance.pageCapacity = size;
        int totalCount = list.size();
        instance.totalCount = totalCount;

        instance.totalPages = totalCount / size + (totalCount / size + totalCount % size == 0 ? 0 : 1);
        return instance;
    }

    public PagePocket<T> paging(int index) {
        this.pageIndex = index;
        this.currentPageSize = this.currentPageItems().size();
        this.currentPageItems = this.currentPageItems();
        return this;
    }

    public <E> PagePocket<E> convert(Class<E> target) {
        BeanCopier beanCopier = BeanCopier.create(this.getClass(), target, false);
        List<E> list = new ArrayList<>();
        try {
            for (T item : this.totalItems) {
                E pojo = target.getConstructor().newInstance();
                beanCopier.copy(item, pojo, null);
                list.add(pojo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return PagePocket.paging(list, this.pageIndex, this.currentPageSize);
    }


}
