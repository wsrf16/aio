package com.aio.portable.park.task;

import java.io.IOException;

public class ThreadLocalTest {
    interface Bank {
        Integer get();
        void increase();
    }

    static class ThreadLocalBank implements Bank {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 0;
            }

        };

        public Integer get() {
            return threadLocal.get();
        }

        public void increase() {
            Integer integer = threadLocal.get();
            threadLocal.set(integer + 10);
        }
    }

    static class ShareBank implements Bank {
        Integer integer = 0;

        public Integer get() {
            return integer;
        }

        public void increase() {
            this.integer = integer + 10;
        }
    }

    static class Transfer implements Runnable {
        Bank bank;

        public Transfer(Bank bank) {
            this.bank = bank;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                bank.increase();
                System.out.println(Thread.currentThread() + "|" + bank.get());
            }
        }
    }

    public static void main() {
        bankMethod(new ThreadLocalBank());
        bankMethod(new ShareBank());

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bankMethod(Bank bank) {
        Transfer transfer = new Transfer(bank);
        Thread t1 = new Thread(transfer);
        Thread t2 = new Thread(transfer);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(bank.get());
    }


}
