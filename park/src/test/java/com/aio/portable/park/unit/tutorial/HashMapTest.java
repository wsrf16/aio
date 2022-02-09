package com.aio.portable.park.unit.tutorial;

import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@TestComponent
public class HashMapTest {
    @Test
    public void hashMapBlock1_7() {
        HashMap<Integer, Integer> integerIntegerHashMap = new HashMap<>(2);
        AtomicInteger atomic = new AtomicInteger();
        for (int i = 0; i < 1000000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    integerIntegerHashMap.put(atomic.get(), atomic.get());
                    atomic.incrementAndGet();
                }
            }).start();
        }
    }
}
