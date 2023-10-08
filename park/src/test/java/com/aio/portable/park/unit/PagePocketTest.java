package com.aio.portable.park.unit;

//import com.aio.portable.swiss.suite.cache.CacheRoom;
import com.aio.portable.swiss.suite.bean.PagePocket;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestComponent
public class PagePocketTest {

    @Test
    public void foobar() {
        List<Integer> ints = Stream.iterate(1, item -> item + 1).limit(101).collect(Collectors.toList());
        PagePocket<Integer> pocket = PagePocket.paging(ints, 3, 31);
        Integer currentPage = pocket.getCurrentIndex();
        List<Integer> currentPageItems = pocket.getCurrentItems();
        Integer currentSize = pocket.getCurrentAmount();
        Integer currentSizeCapcity = pocket.getPageSize();
//        List<Integer> totalItems = pocket.totalItems;
        Integer totalPages = pocket.getTotalPages();
        Integer totalCount = pocket.getTotalItemCount();


//        PagePocket<Integer> pock111 = CacheRoom.popByJson("A1", PagePocket.class);
    }
}
