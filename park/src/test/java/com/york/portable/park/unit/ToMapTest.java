package com.york.portable.park.unit;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ToMapTest {
    static class Wrap {
        private String key;
        private Integer val;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getVal() {
            return val;
        }

        public void setVal(Integer val) {
            this.val = val;
        }
    }

    public static void main() {
        List<Wrap> list = new ArrayList<>();
        list.add(new Wrap(){{setKey("a");setVal(1);}});
        list.add(new Wrap(){{setKey("a");setVal(3);}});
        list.add(new Wrap(){{setKey("b");setVal(null);}});
        list.add(new Wrap(){{setKey("c");setVal(5);}});
        list.add(new Wrap(){{setKey("d");setVal(7);}});

        Map<String, Integer> map = list.stream().collect(Collector.of(HashMap::new, (m, v) -> m.put(v.getKey(), v.getVal()), (map1, map2) -> {map1.putAll(map2);return map1;}));
        Map<String, Integer> map1 = list.stream().collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getVal()), HashMap::putAll);
        Map<String, Integer> map2 = list.stream().collect(Collectors.toMap(Wrap::getKey, e -> Optional.ofNullable(e.getVal()).orElse(0), (val1 , val2)-> val1 + val2 ));

        map = map;

    }
}
