package com.aio.portable.park.unit;

import com.aio.portable.park.bean.MenuEntity;
import com.aio.portable.park.bean.Student;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@TestComponent
public class CollectionTest {
    @Test
    public void todo() {
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

        {
            List<Student> conditionInputList = new ArrayList<>();
            Student add1 = new Student();
            add1.setName("1");
            Student add2 = new Student();
            add2.setName("2");
            Student add3 = new Student();
            add3.setName("3");
            Student add4 = new Student();
            add4.setName("3");
            conditionInputList.add(add1);
            conditionInputList.add(add2);
            conditionInputList.add(add3);
            int i = 0;
            while (i++ < 1000000)
                conditionInputList.add(add4);

            boolean repeatBy = CollectionSugar.beRepeatBy(conditionInputList, Student::getName);
            System.out.println(DateTimeSugar.UnixTime.nowUnix());
            List<Student> distinctBy = CollectionSugar.distinctBy(conditionInputList, c -> c.getName());
            System.out.println(DateTimeSugar.UnixTime.nowUnix());
            List<Student> distinctBy1 = CollectionSugar.distinctByComparable(conditionInputList, c -> c.getName());
            System.out.println(DateTimeSugar.UnixTime.nowUnix());

            System.out.println();
        }
        {
            MenuEntity m1 = new MenuEntity(1, null);
            MenuEntity m2 = new MenuEntity(2, 1);
            MenuEntity m3 = new MenuEntity(3, 1);
            MenuEntity m4 = new MenuEntity(4, 3);
            List<MenuEntity> list = new ArrayList<>();
            list.add(m1);
            list.add(m2);
            list.add(m3);
            list.add(m4);
            List<MenuEntity> tree = CollectionSugar.flatToNested(list);
            List<MenuEntity> flat = CollectionSugar.nestedToFlat(tree);
        }
    }
}
