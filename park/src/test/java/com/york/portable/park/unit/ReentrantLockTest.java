package com.york.portable.park.unit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    class MyRunnable implements Runnable {
        Lock lock = new ReentrantLock();

        @Override
        public void run() {
            try {
//                new LinkedList<>().add()
                lock.lock();
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(50000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void main() {
        Runnable runnable = new MyRunnable();
        Thread t1 = new Thread(runnable, "t1");
        Thread t2 = new Thread(runnable, "t2");
        t1.start();
        t2.start();
        try {
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
