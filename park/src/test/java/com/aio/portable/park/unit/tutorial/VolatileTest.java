package com.aio.portable.park.unit.tutorial;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class VolatileTest {
//    volatile
    boolean value = true;

    @Test
    public void todo() {
        new Thread(() -> {
            while (value) {
            }
            System.out.println("thread1: " + value);
        }).start();


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            System.out.println("thread2: " + value);
            value = false;
            System.out.println("thread2: " + value);
        }).start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
