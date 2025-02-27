package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.bean.GenericSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestComponent
public class GenericTest {
    class OneHashMap extends HashMap<String, List<Integer>> {
    }
    public HashMap<String, List<Integer>> param = new HashMap<>();

    @Test
    public void todo() {
        HashMap<String, Integer> cl = new HashMap<String, Integer>();
        List<Type> parameterizedType = GenericSugar.parseParameterizedTypeOfSuperClass(OneHashMap.class);
        List<Type> params = GenericSugar.parseTypeArgumentsOfField(GenericTest.class, "param");
        ResolvableType resolvableType2 = ResolvableType.forClass(Map.class, HashMap.class);
        System.out.println();
    }
}
