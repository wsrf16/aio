package com.aio.portable.park;

import com.aio.portable.park.bean.Student;
import com.aio.portable.swiss.sugar.concurrent.ThreadSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TestComponent
public class ReferenceTest {
    @Test
    public void strong() {
        byte[] myByte = new byte[1024 * 1024 * 5];
        while (true) {
            ThreadSugar.sleep(Duration.ofSeconds(5));
        }
    }

    // -Xmx10m -Xms10m
    @Test
    public void soft() {
        SoftReference<byte[]> softStr = new SoftReference<>(new byte[1024 * 1024]);
        System.out.println("before gc -> " + softStr.get());
        List<byte[]> list = new ArrayList<>();
        int i = 9;
        while (true) {
            list.add(new byte[1024 * 1924]);
            System.out.println(++i + "allow memory -> " + softStr.get());
        }
    }

    @Test
    public void weak() {
        WeakReference<String> softstr = new WeakReference<>(new String("jeff.chan"));
        System.out.println("before gc -> " + softstr.get());
        System.gc();
        System.out.println("after gc -> " + softstr.get());
    }

    @Test
    public void phantom() {
        ReferenceQueue<Student> queue = new ReferenceQueue<>();
        PhantomReference<Student> reference = new PhantomReference<>(new Student(), queue);
        System.out.println("before gc->" + reference.get());
        System.out.println("before gc->" + queue.poll());
        System.gc();
        System.out.println("after gc->" + reference.get());
        System.out.println("after gc->" + queue.poll());
    }

    @Test
    public void foobar() {
        A a = firstStack();
        System.gc();
        ThreadSugar.sleep(Duration.ofSeconds(1));
        Thread thread = Thread.currentThread();
        System.out.println(thread); // 在这里打断点，观察thread对象里的ThreadLocalMap数据
    }


    private static A firstStack() {
        A a = new A();
        System.out.println("value: " + a.get());
        return a;
    }

    private static class A {
        private ThreadLocal<String> local = ThreadLocal.withInitial(() -> "in class A");

        public String get() {
            return local.get();
        }

        public void set(String str) {
            local.set(str);
        }
    }
}
