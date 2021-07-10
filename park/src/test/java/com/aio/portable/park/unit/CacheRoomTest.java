package com.aio.portable.park.unit;

//import com.aio.portable.swiss.suite.cache.CacheRoom;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.Map;

@TestComponent
public class CacheRoomTest {
    @Test
    public void foobar() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");

//        CacheRoom.saveByJson("A1", map);
    }
}
