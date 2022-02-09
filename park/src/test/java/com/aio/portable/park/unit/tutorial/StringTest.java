package com.aio.portable.park.unit.tutorial;

import com.aio.portable.swiss.sugar.UnsafeSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@TestComponent
public class StringTest {

    @Test
    public void todo() {
        String s = "abc";
        System.out.println(s + "|" + UnsafeSugar.getObjectOffset(s));
        try {
            Field field = s.getClass().getDeclaredField("value");
            field.setAccessible(true);
            field.set(s, "abcd".toCharArray());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(s + "|" + UnsafeSugar.getObjectOffset(s));
    }



    @Test
    public void intern() {
        {
            String s = new String("he");
            // false
            System.out.println(s == s.intern());
        }
        {
            String s1 = new String("he") + new String("llo");
            String s2 = s1.intern();
            // true
            System.out.println(s1 == s2);
        }

        {
            String str2 = new StringBuilder("计算机").append("技术").toString();
            System.out.println(str2 == str2.intern());

            String str1 = new StringBuilder("ja").append("va").toString();
            System.out.println(str1 == str1.intern());
        }
    }
}
