package com.aio.portable.park.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Grammar {
    public static List<String> newList() {
        ArrayList<String> arrayList = ((Supplier<ArrayList>) ArrayList<String>::new).get();
        return arrayList;
    }
}
