package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.structure.cache.CacheRoom;
import com.aio.portable.swiss.structure.bean.PagePocket;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestComponent
public class PagePocketTest {

    @Test
    public void pagePocket() {
        List<Integer> ints = Stream.iterate(1, item -> item + 1).limit(101).collect(Collectors.toList());
        PagePocket<Integer> pocket = PagePocket.paging(ints, 3, 31);
        Integer currentPage = pocket.getPageIndex();
        List<Integer> currentPageItems = pocket.getCurrentPageItems();
        Integer currentSize = pocket.getPageSize();
        Integer currentSizeCapcity = pocket.getPageCapcity();
//        List<Integer> totalItems = pocket.totalItems;
        Integer totalPages = pocket.getTotalPages();
        Integer totalCount = pocket.getTotalCount();


        PagePocket<Integer> pock111 = CacheRoom.popByJson("A1", PagePocket.class);
    }
}
