package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.module.zookeeper.ZooKeeperSugar;
import com.aio.portable.swiss.suite.storage.nosql.zookeeper.ZooKeeperPO;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestComponent
public class KeyValuePersistenceTest {
    @Test
    public static void todo() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", 1111);
        map.put("key2", "bbbb");

        ZooKeeper build = ZooKeeperSugar.build("192.168.133.133:2181", 600000, null);
//        ZooKeeperSugar.clearIfExists(build, "/database1/group-group-task");
        ZooKeeperPO zooKeeperStorage = ZooKeeperPO.singletonInstance(build);
        zooKeeperStorage.setDatabase("database1");
        zooKeeperStorage.set("table1", "key1", map);
        zooKeeperStorage.set("table1", "key2", map);
        zooKeeperStorage.set("table1", "key3", map);
        zooKeeperStorage.remove("table1", "key2");
        Map<String, Map> all = zooKeeperStorage.getAll("table1", Map.class);
        Map<String, Map> table1 = zooKeeperStorage.getAll("table1", Map.class);
        zooKeeperStorage.set("table1", "key1", "abc");
        Map key1 = zooKeeperStorage.get("table1", "key1", Map.class);
        List<String> tables = zooKeeperStorage.tables();
        zooKeeperStorage.clearTable("table1");
        zooKeeperStorage.clearDatabase();
    }
}
