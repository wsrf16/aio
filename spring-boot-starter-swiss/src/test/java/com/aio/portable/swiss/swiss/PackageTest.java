package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sandbox.a中文.Flag;
import com.aio.portable.swiss.sugar.resource.PackageSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.util.List;

@TestComponent
public class PackageTest {
    @Test
    public void todo() throws IOException, ClassNotFoundException {
        List<String> list = PackageSugar.getQualifiedClassName(this.getClass().getPackage().getName());
        for (String name : list) {
//            Class<?> clazz = Class.forName(name);
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(name);
            if (clazz.isAnnotationPresent(Flag.class)) {
                Flag flag = clazz.getAnnotation(Flag.class);
                int age = flag.age();
                System.out.println(name);
            }
        }
    }
}