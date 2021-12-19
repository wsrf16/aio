package com.aio.portable.park.unit.tutorial;

import com.aio.portable.swiss.suite.system.JvmInfo;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class SynchronizedTest {
    public static class User {
        private int age;
        private int age1;
    }

    @Test
    public void todo() {
        User user = new User();
        user.age = 11;
        int age = user.age;
        System.out.println("初始无状态：" + ClassLayout.parseInstance(user).toPrintable(user));




        System.out.println();
        System.out.println();
        System.out.println();
        try {
            Thread.sleep(5000);
            System.out.println("启用偏向锁：" + ClassLayout.parseInstance(user).toPrintable());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            synchronized (user) {
                System.out.println("偏向锁：" + ClassLayout.parseInstance(user).toPrintable());
            }
            System.out.println("释放偏向锁：" + ClassLayout.parseInstance(user).toPrintable());
        }




        System.out.println();
        System.out.println();
        System.out.println();
        new Thread(() -> {
            System.out.println("启用轻量级锁：" + ClassLayout.parseInstance(user).toPrintable(user));
            synchronized (user) {
                try {
                    System.out.println("轻量级锁：" + ClassLayout.parseInstance(user).toPrintable(user));
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("释放轻量级锁：" + ClassLayout.parseInstance(user).toPrintable());
        }).start();




        System.out.println();
        System.out.println();
        System.out.println();
        new Thread(() -> {
            System.out.println("启用重量级锁：" + ClassLayout.parseInstance(age).toPrintable(user));
            synchronized (user) {
                try {
                    System.out.println("重量级锁：" + ClassLayout.parseInstance(user).toPrintable(user));
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("释放重量级锁：" + ClassLayout.parseInstance(user).toPrintable(user));
        }).start();

    }
}
