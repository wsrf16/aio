package com.aio.portable.swiss;

import com.aio.portable.swiss.middleware.zookeeper.ZooKeeperSugar;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class ZooKeeperTest {
    @Test
    public void foobar() {
        try {
            ZooKeeper zooKeeper = ZooKeeperSugar.build("mecs.com:2181", 120000, null);

            boolean lock1 = ZooKeeperSugar.tryLock(zooKeeper, "/aaa", 20000);
            boolean lock2 = ZooKeeperSugar.tryLock(zooKeeper, "/aaa", 20000);
            boolean lock3 = ZooKeeperSugar.tryLock(zooKeeper, "/aaa", 20000);

            ZooKeeperSugar.create(zooKeeper, "/bbb1/bbb2", null);
            ZooKeeperSugar.clearIfExists(zooKeeper, "/bbb1/bbb2");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
