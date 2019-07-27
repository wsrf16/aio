package com.york.portable.park.other;

import java.util.HashMap;
import java.util.Map;

public class MapCloneTest {
    public void cloneTest() {
        // shallow clone
        {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("name", "Marydon");
            Map<String, String> paramMapClone = paramMap;
            paramMap.remove("name");
            System.out.println(paramMapClone);
        }
        // deep clone
        {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("name", "Marydon");
            Map<String, String> paramMapClone = new HashMap<String, String>();
            paramMapClone.putAll(paramMap);
            paramMap.remove("name");
            System.out.println(paramMapClone);
        }
    }
}
