package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.suite.resource.ClassLoaderSugar;
import org.springframework.boot.test.context.TestComponent;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

@TestComponent
public class ClassLoaderTest {
    private static void todo() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String name;
        boolean b;
        name = "java.lang.System";
        b = ClassLoaderSugar.hasLoadedInCurrentThread(name);
        printResult(name, b);

        name = "java.sql.Date";
        b = ClassLoaderSugar.hasLoadedInCurrentThread(name);
        printResult(name, b);
        java.sql.Date date = new java.sql.Date(0);
        b = ClassLoaderSugar.hasLoadedInCurrentThread(name);
        printResult(name, b);

        name = "Wood";
        b = ClassLoaderSugar.hasLoadedInCurrentThread(name);
        printResult(name, b);
        b = ClassLoaderSugar.hasLoadedInCurrentThread(name);
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
