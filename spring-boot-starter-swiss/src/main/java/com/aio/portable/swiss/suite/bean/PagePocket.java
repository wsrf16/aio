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

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getCurrentItems() {
        return currentItems;
    }

    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 总数量
     */
    private int totalItemCount;
    /**
     * 全部item
     */
    private List<T> totalItems;
    /**
     * 当前页号
     */
    private int currentIndex;
    /**
     * 当前页item数量
     */
    private int currentAmount;
    /**
     * 当前页item大小
     */
    private int pageSize;
    /**
     * 当前页item
     */
    private List<T> currentItems;

    private List<T> currentItems() {
        List<T> cutLeft = totalItems.subList((currentIndex - 1) * pageSize, totalItems.size());
        if (cutLeft.size() < pageSize)
            return cutLeft;
        else
            return cutLeft.subList(0, pageSize);
    }

    public static final <T> PagePocket<T> paging(List<T> list, int size, int index) {
        PagePocket<T> instance = new PagePocket<>();
        instance.totalItems = list;
        instance.pageSize = size;
        instance.currentIndex = index;
        int totalCount = list.size();
        instance.totalItemCount = totalCount;

        instance.totalPages = totalCount / size + (totalCount / size + totalCount % size == 0 ? 0 : 1);
        instance.currentAmount = instance.currentItems().size();
        instance.currentItems = instance.currentItems();
        return instance;
    }

    public static final <T> PagePocket<T> paging(List<T> list, int size) {
        PagePocket<T> instance = new PagePocket<>();
        instance.totalItems = list;
        instance.pageSize = size;
        int totalCount = list.size();
        instance.totalItemCount = totalCount;

        instance.totalPages = totalCount / size + (totalCount / size + totalCount % size == 0 ? 0 : 1);
        return instance;
    }

    public PagePocket<T> paging(int index) {
        this.currentIndex = index;
        this.currentAmount = this.currentItems().size();
        this.currentItems = this.currentItems();
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
        return PagePocket.paging(list, this.currentAmount, this.currentIndex);
    }


}
