package com.york.portable.park.other.specification;

public class OutInClassTest {
    public class Inner {
    }
    public static void main(String[] args) {
        Class clazz = new Inner[]{}.getClass();
        String getSimpleName = clazz.getSimpleName();       // Inner[]
        String getTypeName = clazz.getTypeName();           // com.test.Main$Inner[] only since 1.8
        String getName = clazz.getName();                   // [Lcom.test.Main$Inner;
        String getCanonicalName = clazz.getCanonicalName(); // com.test.Main.Inner[]
    }
}
