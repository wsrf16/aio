package com.york.portable.park.unit.swiss;

import com.york.portable.swiss.resource.ClassLoaderUtils;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

@TestComponent
public class ClassLoaderTest {

    @Test
    public void main() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String name;
        boolean b;
        name = "java.lang.System";
        b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
        printResult(name, b);

        name = "java.sql.Date";
        b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
        printResult(name, b);
        java.sql.Date date = new java.sql.Date(0);
        b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
        printResult(name, b);

        name = "com.york.portable.swiss.sandbox.Wood";
        b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
        printResult(name, b);
        b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
        printResult(name, b);

    }

    private static void printResult(String name, boolean b) {
        if (b) {
            System.out.println(MessageFormat.format("{0}已经加载!", name));
        } else {
            System.out.println(MessageFormat.format("{0}尚未加载!", name));
        }
    }
}
