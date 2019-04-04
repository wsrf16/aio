package com.york.portable.swiss.sandbox;

import java.util.concurrent.CountDownLatch;


//class CountDownLatchCase {
//    static int max = 100;
//    static CountDownLatch latch = new CountDownLatch(max);
//
//    public static void todo(String[] args) throws InterruptedException {
//        for (int i = 0; i < max; i++) {
//            new Thread(new Task(i, latch)).start();
//        }
//
//        latch.await();
//        System.out.println("finish!");
//    }
//}
//
//class Task implements Runnable {
//    CountDownLatch latch;
//    int id;
//
//    Task(int id, CountDownLatch latch) {
//        this.id = id;
//        this.latch = latch;
//    }
//
//    @Override
//    public void run() {
//        System.out.println(id);
//        latch.countDown();
//    }
//}
