package com.york.portable.swiss.sandbox;


import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;

/**
 * Created by York on 2018/1/31.
 */
class CyclicBarrierCase {
    static CyclicBarrier c = new CyclicBarrier(2, new CyclicBarrierCase.A());

    public static void todo() {
        Callable<String> oneCallable = new Callable<String>(){
            @Override
            public String call() throws Exception {
                return null;
            }
        };
        //由Callable<Integer>创建一个FutureTask<Integer>对象：
        FutureTask<String> oneTask = new FutureTask<String>(oneCallable);
        //注释：FutureTask<Integer>是一个包装器，它通过接受Callable<Integer>来创建，它同时实现了Future和Runnable接口。
        //由FutureTask<Integer>创建一个Thread对象：
        Thread oneThread = new Thread(oneTask);

        Runnable run = new Runnable() {
            @Override
            public void run() {

            }
        };
        Thread twoThread = new Thread(run);
        oneThread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
                System.out.println(1);
            }
        }).start();

        try {
            c.await();
        } catch (Exception e) {

        }
        System.out.println(2);
    }

    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println(3);
        }
    }
}



