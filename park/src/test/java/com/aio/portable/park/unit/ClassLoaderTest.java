package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.resource.ClassLoaderSugar;
import org.springframework.boot.test.context.TestComponent;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

@TestComponent
public class ClassLoaderTest {
    private static void foobar() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String name;
        boolean b;
        name = "java.lang.System";
        b = ClassLoaderSugar.hasLoaded(name);
        printResult(name, b);

        name = "java.sql.Date";
        b = ClassLoaderSugar.hasLoaded(name);
        printResult(name, b);
        java.sql.Date date = new java.sql.Date(0);
        b = ClassLoaderSugar.hasLoaded(name);
        printResult(name, b);

        name = "Wood";
        b = ClassLoaderSugar.hasLoaded(name);
        printResult(name, b);
        b = ClassLoaderSugar.hasLoaded(name);
        printResult(name, b);

        name = "org.yaml.snakeyaml.Yaml";
        boolean isPresent1 = ClassLoaderSugar.isPresent(name);
        boolean hasLoaded = ClassLoaderSugar.hasLoaded(name);
        Object yaml1 = ClassLoaderSugar.load(name, false);
        org.yaml.snakeyaml.Yaml yaml2 = new org.yaml.snakeyaml.Yaml();

    }

    private static void printResult(String name, boolean b) {
        if (b) {
            System.out.println(MessageFormat.format("{0}已经加载!", name));
        } else {
            System.out.println(MessageFormat.format("{0}尚未加载!", name));
        }
    }
}
