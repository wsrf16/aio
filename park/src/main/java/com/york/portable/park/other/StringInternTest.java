package com.york.portable.park.other;

public class StringInternTest {
    public static void main() {
//        System.out.println(System.getProperty("java.version"));
//        String a = new String("abc");
//        // 第一次，创建了两个对象，一个是堆中的string对象，一个是常量池中的"abc"
//        String b = new String("abc");
//        String c = "abc";
//        // 第二次，创建一个对象，堆中的另外一个string对象
//        System.out.println(a.intern() == b.intern());// true
//        System.out.println(a.intern() == b);// false
//        System.out.println(a.intern() == a);// false
//        System.out.println(c == a.intern());


        {
            String str = new String("a") + new String("b");
            System.out.println(str.intern() == "ab");       // true
            System.out.println(str == "ab");                // true
        }
        {
            String strtwo = "ab";
            String str2 = new String("a") + new String("b");
            System.out.println(str2.intern() == "ab");      // true
            System.out.println(str2 == "ab");               // false
        }
    }
}
