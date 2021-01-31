package com.aio.portable.swiss.module.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorMultiTransaction;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.api.transaction.TransactionOp;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.function.Supplier;

public abstract class CuratorSugar {
//    public final public void newcuratorFramework(int baseSleepTimeMs, int maxRetries, int maxSleepMs) {
//        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
//        CreateBuilder createBuilder = curatorFramework.create();
//        createBuilder. if
//    }

    public final static class RetryPolicyBuilder {
        public final static ExponentialBackoffRetry newExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries, int maxSleepMs) {
            ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries, maxSleepMs);
            return retryPolicy;
        }

        public final static RetryForever newRetryForever(int retryIntervalMs) {
            RetryForever retryPolicy = new RetryForever(retryIntervalMs);
            return retryPolicy;
        }

        public final static RetryNTimes newRetryNTimes(int n, int sleepMsBetweenRetries) {
            RetryNTimes retryPolicy = new RetryNTimes(n, sleepMsBetweenRetries);
            return retryPolicy;
        }

        public final static RetryUntilElapsed newRetryUntilElapsed(int maxElapsedTimeMs, int sleepMsBetweenRetries) {
            RetryUntilElapsed retryPolicy = new RetryUntilElapsed(maxElapsedTimeMs, sleepMsBetweenRetries);
            return retryPolicy;
        }
    }

    public final static class InterProcessBuilder {
        /**
         * 公平锁
         * @param client
         * @param path
         * @return
         */
        public final static InterProcessMutex newInterProcessMutex(CuratorFramework client, String path) {
            return new InterProcessMutex(client, path);
        }

        public final static InterProcessMutex newInterProcessMutex(CuratorFramework client, String path, LockInternalsDriver driver) {
            return new InterProcessMutex(client, path, driver);
        }

        public final static <T> T interProcessMutex(CuratorFramework client, String path, Supplier<T> supplier) {
            final InterProcessMutex interProcessMutex = newInterProcessMutex(client, path);
            return execute(interProcessMutex, supplier);
        }

        public final static <T> T execute(InterProcessMutex interProcessMutex, Supplier<T> supplier) {
            try {
                interProcessMutex.acquire();
                final T t = supplier.get();
                interProcessMutex.release();
                return t;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    interProcessMutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public final static InterProcessMultiLock newInterProcessMultiLock(CuratorFramework client, List<String> paths) {
            return new InterProcessMultiLock(client, paths);
        }

        public final static InterProcessMultiLock newInterProcessMultiLock(List<InterProcessLock> locks) {
            return new InterProcessMultiLock(locks);
        }

        public final static InterProcessReadWriteLock newInterProcessReadWriteLock(CuratorFramework client, String basePath) {
            return new InterProcessReadWriteLock(client, basePath);
        }

        public final static InterProcessReadWriteLock newInterProcessReadWriteLock(CuratorFramework client, String basePath, byte[] lockData) {
            return new InterProcessReadWriteLock(client, basePath, lockData);
        }

        public final static InterProcessSemaphoreV2 newInterProcessSemaphoreV2(CuratorFramework client, String path, int maxLeases) {
            return new InterProcessSemaphoreV2(client, path, maxLeases);
        }

        public final static InterProcessSemaphoreV2 newInterProcessSemaphoreV2(CuratorFramework client, String path, SharedCountReader count) {
            return new InterProcessSemaphoreV2(client, path, count);
        }
    }

    // https://github.com/apache/curator/blob/master/curator-examples/src/main/java/cache/CuratorCacheExample.java
    public final static class ListenerUtil {
        public final static CuratorCacheListenerBuilder newCuratorCacheListenerBuilder() {
            return CuratorCacheListener.builder();
        }

        public final static CuratorCache addListener(CuratorFramework curatorFramework, String path, CuratorCacheListener curatorCacheListener) {
            try {
                CuratorCache curatorCache = CuratorCache.build(curatorFramework, path);
                curatorCache.listenable().addListener(curatorCacheListener);
                return curatorCache;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static void addListener(CuratorCache curatorCache, CuratorCacheListener curatorCacheListener) {
            try {
                curatorCache.listenable().addListener(curatorCacheListener);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static CuratorCache addListenerAndStart(CuratorFramework curatorFramework, String path, CuratorCacheListener curatorCacheListener) {
            final CuratorCache curatorCache = addListener(curatorFramework, path, curatorCacheListener);
            curatorCache.start();
            return curatorCache;
        }

        public final static void addListenerAndStart(CuratorCache curatorCache, CuratorCacheListener curatorCacheListener) {
            addListener(curatorCache, curatorCacheListener);
            curatorCache.start();
        }
    }

    public final static class TransactionUtil {
        public final static TransactionOp buildCuratorOp(CuratorFramework curatorFramework) {
            try {
                TransactionOp transactionOp = curatorFramework.transactionOp();
                return transactionOp;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static List<CuratorTransactionResult> commit(CuratorFramework curatorFramework, CuratorOp... curatorOps) {
            try {
                CuratorMultiTransaction transaction = curatorFramework.transaction();
                List<CuratorTransactionResult> curatorTransactionResults = transaction.forOperations(curatorOps);
                return curatorTransactionResults;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static List<CuratorTransactionResult> commit(CuratorFramework curatorFramework, List<CuratorOp> curatorOpList) {
            try {
                CuratorMultiTransaction transaction = curatorFramework.transaction();
                List<CuratorTransactionResult> curatorTransactionResults = transaction.forOperations(curatorOpList);
                return curatorTransactionResults;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }


    }


    public final static String create(CuratorFramework curatorFramework, CreateMode createMode, String path) {
        try {
            String forPath = curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).forPath(path);
            return forPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String create(CuratorFramework curatorFramework, CreateMode createMode, String path, byte[] bytes) {
        try {
            String forPath = curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).forPath(path, bytes);
            return forPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String createIfNotExist(CuratorFramework curatorFramework, CreateMode createMode, String path) {
        String forPath = null;
        final Stat stat = checkExists(curatorFramework, path);
        if (stat == null) {
            forPath = create(curatorFramework, createMode, path);
        }
        return forPath;
    }

    public final static String createIfNotExist(CuratorFramework curatorFramework, CreateMode createMode, String path, byte[] bytes) {
        String forPath = null;
        final Stat stat = checkExists(curatorFramework, path);
        if (stat == null) {
            forPath = create(curatorFramework, createMode, path, bytes);
        }
        return forPath;
    }

    public final static List<String> getChildren(CuratorFramework curatorFramework, String path) {
        try {
            List<String> forPath = curatorFramework.getChildren().forPath(path);
            return forPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public final static List<String> getGrandChildren(CuratorFramework curatorFramework, String path) {
//        try {
//            List<String> forPath = curatorFramework.getChildren().forPath(path);
//            return forPath;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    public final static void delete(CuratorFramework curatorFramework, String path) {
        try {
            curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static void delete(CuratorFramework curatorFramework, String path, int version) {
        try {
            curatorFramework.delete().deletingChildrenIfNeeded().withVersion(version).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static Stat checkExists(CuratorFramework curatorFramework, String path) {
        try {
            return curatorFramework.checkExists().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static byte[] getData(CuratorFramework curatorFramework, String path) {
        try {
            return curatorFramework.getData().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static Stat setData(CuratorFramework curatorFramework, String path, byte[] bytes) {
        try {
            Stat forPath = curatorFramework.setData().forPath(path, bytes);
            return forPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static Stat setData(CuratorFramework curatorFramework, String path, byte[] bytes, int version) {
        try {
            Stat forPath = curatorFramework.setData().withVersion(version).forPath(path, bytes);
            return forPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
