package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@TestComponent
public class CollectionTest {
    @Test
    public static void todo() {
        {
            Iterator<String> iterator = java.util.Collections.emptyIterator();
            List<String> list = CollectionSugar.toList(iterator);
        }
        {
            Enumeration<String> enumeration = java.util.Collections.emptyEnumeration();
            List<String> arrayList = java.util.Collections.list(enumeration);
        }
        {
            List<String> arrayList = java.util.Collections.emptyList();
            Enumeration<String> enumeration = java.util.Collections.enumeration(arrayList);
        }
    }
}
