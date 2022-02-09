package com.aio.portable.park.unit.tutorial;

import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@TestComponent
public class ThreadTest {

    static int count = 0;
    AtomicStampedReference atomicStampedReference = new AtomicStampedReference(1, 1);


    @Test
    public void foo() {
        CasLock casLock = new CasLock();
        for (int i = 0; i < 100000; i++) {
            new Thread(() -> {
            while (true) {
                if (casLock.lock()) {
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    casLock.unlock();
                    break;
                }
            }
            }).start()
            ;
        }
        System.out.println(count);
    }

    static class CasLock {
        AtomicReference<Integer> lock = new AtomicReference<>();

        public CasLock() {
            lock.set(0);
        }


//        public boolean lock() {
//            Long offset = UnsafeSugar.getDeclaredFieldOffset(lock, "value");
//            boolean b = UnsafeSugar.getUnsafe().compareAndSwapInt(lock, offset, 0, 1);
//            return b;
//        }
//
//        public boolean unlock() {
//            Long offset = UnsafeSugar.getDeclaredFieldOffset(lock, "value");
//            boolean b = UnsafeSugar.getUnsafe().compareAndSwapInt(lock, offset, 1, 0);
//            return b;
//        }

        public boolean lock() {
            boolean b = lock.compareAndSet(0, 1);
            return b;
        }

        public boolean unlock() {
            boolean b = lock.compareAndSet(1, 0);
            return b;
        }
    }
}
