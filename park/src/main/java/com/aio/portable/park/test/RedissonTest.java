//package com.aio.portable.park.test;
//
//import org.junit.Test;
//import org.redisson.Redisson;
//import org.redisson.RedissonRedLock;
//import org.redisson.api.*;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//
//import java.util.concurrent.TimeUnit;
//
//public class RedissonTest {
//    @Autowired
//    RedissonClient redisson;
//
//    @Bean
//    public final RedissonClient create() {
//        String host = null;
//        Integer port = null;
//        String password = null;
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }
//
//
//    @Test
//    public void lock() {
//        {
//            RLock lock = redisson.getLock("grade3:class2:123");
//            // 具有Watch Dog 自动延期机制 默认续30s 每隔30/3=10 秒续到30s。可以通过修改Config.lockWatchdogTimeout来另行指定
//            try {
//                lock.lock();
//                lock.lock(20000, TimeUnit.MILLISECONDS);
//                lock.tryLock(10000, 20000, TimeUnit.MILLISECONDS);
//            } catch (Exception e) {
//                e.printStackTrace();
//                lock.unlock();
//            }
//        }
//
//
//        {
//            RLock fairLock = redisson.getFairLock("anyLock");
//        }
//
//
//        {
//            RReadWriteLock rwlock = redisson.getReadWriteLock("anyRWLock");
//            RLock rLock = rwlock.readLock();
//            rLock.lock();
//
//            RLock wLock = rwlock.writeLock();
//            wLock.lock();
//        }
//
//        {
//            try {
//                RSemaphore semaphore = redisson.getSemaphore("semaphore");
//                semaphore.acquire();
////或
//                semaphore.acquireAsync();
//                semaphore.acquire(23);
//                semaphore.tryAcquire();
////或
//                semaphore.tryAcquireAsync();
//                semaphore.tryAcquire(23, TimeUnit.SECONDS);
////或
//                semaphore.tryAcquireAsync(23, TimeUnit.SECONDS);
//                semaphore.release(10);
//                semaphore.release();
////或
//                semaphore.releaseAsync();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
//
//        {
//            try {
//                {
//                    RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
//                    latch.trySetCount(1);
//                    latch.await();
//                }
//
//                {// 在其他线程或其他JVM里
//                    RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
//                    latch.countDown();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
//        {
//            try {
//                RedissonClient redissonInstance1 = null;
//                RedissonClient redissonInstance2 = null;
//                RedissonClient redissonInstance3 = null;
//
//                RLock lock1 = redissonInstance1.getLock("lock1");
//                RLock lock2 = redissonInstance2.getLock("lock2");
//                RLock lock3 = redissonInstance3.getLock("lock3");
//
//                RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
//// 同时加锁：lock1 lock2 lock3
//// 红锁在大部分节点上加锁成功就算成功。
//                lock.lock();
//                // 给lock1，lock2，lock3加锁，如果没有手动解开的话，10秒钟后将会自动解开
//                lock.lock(10, TimeUnit.SECONDS);
//
//// 为加锁等待100秒时间，并在加锁成功10秒钟后自动解开
//                boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
//                lock.unlock();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    @Test
//    public void bloomFilter() {
//        RBloomFilter<Object> bloomFilter = redisson.getBloomFilter("sample");
//        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
//        bloomFilter.tryInit(55000000L, 0.03);
//        bloomFilter.add(new Object());
//        bloomFilter.add(new Object());
//        bloomFilter.contains(new Object());
//    }
//
//    @Test
//    public void adder() {
//        RLongAdder atomicLong = redisson.getLongAdder("myLongAdder");
//        atomicLong.add(12);
//        atomicLong.increment();
//        atomicLong.decrement();
//        atomicLong.sum();
//
//        RDoubleAdder atomicDouble = redisson.getDoubleAdder("myLongDouble");
//        atomicDouble.add(12);
//        atomicDouble.increment();
//        atomicDouble.decrement();
//        atomicDouble.sum();
//    }
//
//    @Test
//    public void map() {
//        {
//            RMap<String, Object> map = redisson.getMap("anyMap");
//            Object prevObject = map.put("123", new Object());
//            Object currentObject = map.putIfAbsent("323", new Object());
//            Object obj = map.remove("123");
//
//            map.fastPut("321", new Object());
//            map.fastRemove("321");
//
//            RFuture<Object> putAsyncFuture = map.putAsync("321", new Object());
//
//            map.fastPutAsync("321", new Object());
//            map.fastRemoveAsync("321");
//        }
//
//
//        {
//            RSetMultimapCache<String, String> multimap = redisson.getSetMultimapCache("myMultimap");
//            multimap.put("1", "a");
//            multimap.put("1", "b");
//            multimap.put("1", "c");
//
//            multimap.put("2", "e");
//            multimap.put("2", "f");
//
//            multimap.expireKey("2", 10, TimeUnit.MINUTES);
//        }
//
//        {
//            RSet<Object> set = redisson.getSet("anySet");
//            set.add(new Object());
//            set.remove(new Object());
//        }
//
//        {
//            RList<Object> list = redisson.getList("anyList");
//            list.add(new Object());
//            list.get(0);
//            list.remove(new Object());
//        }
//
//
//        {
//            RQueue<Object> queue = redisson.getQueue("anyQueue");
//            queue.add(new Object());
//            Object obj = queue.peek();
//            Object Obj = queue.poll();
//        }
//
//        {
//            RDeque<Object> queue = redisson.getDeque("anyDeque");
//            queue.addFirst(new Object());
//            queue.addLast(new Object());
//            Object obj = queue.removeFirst();
//            Object Obj = queue.removeLast();
//        }
//
//        {
//            RBlockingQueue<Object> queue = redisson.getBlockingQueue("anyQueue");
//            queue.offer(new Object());
//            Object obj = queue.peek();
//            Object obj1 = queue.poll();
//            try {
//                Object obj2 = queue.poll(10, TimeUnit.MINUTES);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    @Test
//    public void lock1() {
//
//    }
//
//    @Test
//    public void lock2() {
//
//    }
//}
