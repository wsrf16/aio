package com.york.portable.swiss.sugar;

import javafx.animation.Transition;
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

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCapcity() {
        return pageCapcity;
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
    private int pageSize;
    /**
     * 当前页item容量
     */
    private int pageCapcity;
    /**
     * 当前页item
     */
    private List<T> currentPageItems;

    private List<T> currentPageItems() {
        List<T> cutLeft = totalItems.subList((pageIndex - 1) * pageCapcity, totalItems.size());
        if (cutLeft.size() < pageCapcity)
            return cutLeft;
        else
            return cutLeft.subList(0, pageCapcity);
    }

    public final static <T> PagePocket<T> paging(List<T> list, int size, int index) {
        PagePocket<T> instance = new PagePocket<>();
        instance.totalItems = list;
        instance.pageCapcity = size;
        instance.pageIndex = index;
        int totalCount = list.size();
        instance.totalCount = totalCount;

        instance.totalPages = totalCount / size + (totalCount / size + totalCount % size == 0 ? 0 : 1);
        instance.pageSize = instance.currentPageItems().size();
        instance.currentPageItems = instance.currentPageItems();
        return instance;
    }

    public final static <T> PagePocket<T> build(List<T> list, int size) {
        PagePocket<T> instance = new PagePocket<>();
        instance.totalItems = list;
        instance.pageCapcity = size;
        int totalCount = list.size();
        instance.totalCount = totalCount;

        instance.totalPages = totalCount / size + (totalCount / size + totalCount % size == 0 ? 0 : 1);
        return instance;
    }

    public PagePocket<T> paging(int index) {
        this.pageIndex = index;
        this.pageSize = this.currentPageItems().size();
        this.currentPageItems = this.currentPageItems();
        return this;
    }

//    public final static <T> void instance(List<T> list, int size) {
//        class Transition<T> {
//            private List<T> list;
//            private int size;
//            public PagePocket<T> index(int index) {
//                return paging(list, size, index);
//            }
//        }
//        Transition<T> transition = new Transition();
//        transition.list = list;
//        transition.size = size;
//    }

    public <E> PagePocket<E> convert(Class<E> target) {
        BeanCopier beanCopier = BeanCopier.create(this.getClass(), target, false);
        List<E> list = new ArrayList<>();
        try {
            for (T item : this.totalItems) {
                E pojo = target.newInstance();
                beanCopier.copy(item, pojo, null);
                list.add(pojo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return PagePocket.paging(list, this.pageIndex, this.pageSize);
    }
}
