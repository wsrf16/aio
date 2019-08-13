package com.york.portable.park.other;

public class NotifyTest {
    private String flag[] = { "true" };

    class NotifyThread extends Thread {
        public NotifyThread(String name) {
            super(name);
        }

        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (flag) {
                flag[0] = "false";
                flag.notify();
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                flag.notifyAll();
            }
        }
    };

    class WaitThread extends Thread {
        public WaitThread(String name) {
            super(name);
        }

        public void run() {
            synchronized (flag) {
                while (flag[0] != "false") {
                    System.out.println(getName() + " begin waiting!");
                    long waitTime = System.currentTimeMillis();
                    try {
                        flag.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitTime = System.currentTimeMillis() - waitTime;
                    System.out.println("wait time :" + waitTime);
                }
                System.out.println(getName() + " end waiting!");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Main Thread Run!");
        NotifyTest test = new NotifyTest();
        NotifyThread notifyThread01 = test.new NotifyThread("notify01");
        NotifyThread notifyThread02 = test.new NotifyThread("notify02");
        WaitThread waitThread01 = test.new WaitThread("waiter01");
        WaitThread waitThread02 = test.new WaitThread("waiter02");
        WaitThread waitThread03 = test.new WaitThread("waiter03");
        notifyThread01.start();
        notifyThread02.start();
        waitThread01.start();
        waitThread02.start();
        waitThread03.start();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
