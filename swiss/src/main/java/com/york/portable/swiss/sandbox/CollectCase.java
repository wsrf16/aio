package com.york.portable.swiss.sandbox;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class CollectCase {
    public void test(){
        List<MA> list = new ArrayList<MA>();
        list.add(MA.instance("A", 1));
        list.add(MA.instance("A", 2));
        list.add(MA.instance("A", 3));
        list.add(MA.instance("A", 4));
        list.add(MA.instance("B", 11));
        list.add(MA.instance("B", 12));
        list.add(MA.instance("B", 12));
        list.add(MA.instance("C", 21));
        list.add(MA.instance("C", 22));
        //Function.identity()

        List<Integer> array = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }};


        Map<String, Long> map1 = list.stream().collect(Collectors.groupingBy(MA::getKey, Collectors.counting()));
        Map<String, Integer> map2 = list.stream().collect(Collectors.groupingBy(MA::getKey, Collectors.summingInt(MA::getValue)));

        Map<String, Set<String>> map3 = list.stream().collect(Collectors.groupingBy(MA::getKey, Collectors.mapping(MA::getKey, Collectors.toSet())));

        String[] cityArr = {"Beijing", "Shanghai", "Chengdu"};
        String[] cityArr11 = new String[] {"Beijing", "Shanghai", "Chengdu"};

        Set<String> sset = Stream.of("You", "may", "assume").collect(Collectors.toSet());
        //Stream<Integer> stream1 = Arrays.stream(1, 2, 3);
        Stream<Integer> stream11 = Arrays.stream(new Integer[]{1, 2, 3});
        Stream<int[]> stream111 = Stream.of(new int[]{1, 2, 3});
        IntStream stream2 = Arrays.stream(new int[]{1, 2, 3});

        //new Hashtable().keySet()
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("1", "a1");
//        map.put("1", "a3");
//        map.put("2", "b1");
//        map.put("2", "b2");
//        map.put("3", "c");
//        map.put("6", "f123");
//        map.put("6", "f112");


//        Arrays.asList("11212","dfd","2323","dfhgf").stream().collect(toList());
//        Arrays.asList("11212","dfd","2323","dfhgf").stream().toArray(String[]::new);

    }




     static class MA {
        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        public String key;
        public Integer value;

        public static MA instance(String key, Integer value) {
            MA ma = new MA();
            ma.key = key;
            ma.value = value;
            return ma;

        }
    }
}
