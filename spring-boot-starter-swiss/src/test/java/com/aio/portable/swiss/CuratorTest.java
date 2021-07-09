package com.aio.portable.swiss;

import com.aio.portable.swiss.middleware.zookeeper.CuratorSugar;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

@TestComponent
public class CuratorTest {
    @Test
    public void foobar() {
        try {
            final ExponentialBackoffRetry exponentialBackoffRetry = CuratorSugar.RetryPolicyBuilder.newExponentialBackoffRetry(5000, 3, 5000);
            final CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("mecs.com:32181", exponentialBackoffRetry);
            curatorFramework.start();

            final String result1 = CuratorSugar.createIfNotExist(curatorFramework, CreateMode.PERSISTENT, "/abc/def");
            final Stat stat1 = CuratorSugar.setData(curatorFramework, "/abc/def", "something".getBytes());

            final List<String> children1 = CuratorSugar.getChildren(curatorFramework, "/abc");

            final CuratorCacheListener curatorCacheListener = CuratorSugar.ListenerUtil.newCuratorCacheListenerBuilder().forChanges((oldNode, node) -> System.out.println(String.format("Node changed. Old: [%s] New: [%s]", oldNode, node))).build();
            CuratorSugar.ListenerUtil.addListenerAndStart(curatorFramework, "/abc/def", curatorCacheListener);


            final CuratorOp curatorOp1 = CuratorSugar.TransactionUtil.buildCuratorOp(curatorFramework).create().forPath("/111");
            final CuratorOp curatorOp2 = CuratorSugar.TransactionUtil.buildCuratorOp(curatorFramework).create().forPath("/111/222");
            final CuratorOp curatorOp3 = CuratorSugar.TransactionUtil.buildCuratorOp(curatorFramework).create().forPath("/111/222");
            try {
                final List<CuratorTransactionResult> commit = CuratorSugar.TransactionUtil.commit(curatorFramework, curatorOp1, curatorOp2, curatorOp3);
            } catch (Exception e) {
                e.printStackTrace();
            }


            InterProcessMutex lock = CuratorSugar.InterProcessBuilder.newInterProcessMutex(curatorFramework, "/man-lock");


            for (int i = 0; i < 30; i++) {
//                if (lock.acquire(i++, TimeUnit.SECONDS)) {
//                    Thread.sleep(500);
//                }
                lock.acquire();
                Thread.sleep(500);
                lock.release();
            }


            if (1 == 1) {
                Thread.sleep(100000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
