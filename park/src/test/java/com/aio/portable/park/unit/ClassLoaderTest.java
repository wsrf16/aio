package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.UnsafeSugar;
import com.aio.portable.swiss.sugar.location.URLSugar;
import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.meta.StreamClassLoader;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.ClassUtils;

import javax.tools.DiagnosticCollector;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
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
        name = "com.aio.portable.park.common.UserInfoEntity";
        b = ClassLoaderSugar.hasLoaded(name);
        printResult(name, b);

        name = "org.yaml.snakeyaml.Yaml";
        boolean isPresent = ClassLoaderSugar.isPresent(name);
        boolean hasLoaded = ClassLoaderSugar.hasLoaded(name);
        Object yaml1 = ClassLoaderSugar.loadClass(name, ClassLoaderSugar.getDefaultClassLoader(), false);
        Object yaml2 = ClassLoaderSugar.forName(name, ClassLoaderSugar.getDefaultClassLoader(), false);
        org.yaml.snakeyaml.Yaml yaml3 = new org.yaml.snakeyaml.Yaml();


        String contents = "package com.company;\n" +
                "class Demo {\n" +
                "    Demo() {\n" +
                "    }\n" +
                "\n" +
                "    public static String todo() {\n" +
                "        byte var0 = 0;\n" +
                "        System.out.println(\"测试运行 \" + var0);\n" +
                "        return \"测试运行 \" + var0;\n" +
                "    }\n" +
                "}";
        KeyValuePair<Boolean, DiagnosticCollector> ret = ClassLoaderSugar.compileCode("c:\\", "com.company.Demo", contents);
        if (ret.getKey()) {
            Class cls = StreamClassLoader.buildByFile("D:/com/company/Demo.class").loadClassByBinary("com.company.Demo");
            Object result = ClassSugar.Methods.invoke(cls, "todo");
            System.out.println(result);
        }
    }

    private static void printResult(String name, boolean b) {
        if (b) {
            System.out.println(MessageFormat.format("{0}已经加载!", name));
        } else {
            System.out.println(MessageFormat.format("{0}尚未加载!", name));
        }
    }

    @Test
    public void todo() {
        String path = "_jar/jagent-1.0.0-SNAPSHOT.jar";
        File file = new File(path);

        URLClassLoader applicationClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
        ClassLoaderSugar.addURL(applicationClassLoader, file);
        Class<?> clazz = ClassLoaderSugar.loadClass("com.aio.portable.Config", applicationClassLoader);

        URLClassLoader classLoader1 = new URLClassLoader(new URL[]{URLSugar.toFileURL(path)});
        Class<?> clazz1 = ClassLoaderSugar.loadClass("com.aio.portable.Config", classLoader1);

        Class<?> clazz2 = ClassLoaderSugar.loadClass("com.aio.portable.Config", file);
        Class<?> clazz3 = ClassLoaderSugar.loadClass("com.aio.portable.Config", URLSugar.toFileURL(path));
        Class<?> class4 = ClassLoaderSugar.loadClass(URLSugar.toURL("jar:file:/D:/NutDisk/jar/jagent-1.0.0-SNAPSHOT.jar!/com/aio/portable/Config.class"));

        Object o = ClassSugar.newInstance(clazz);
        ClassSugar.Methods.invoke(o, "setAaa", "11111");
        UnsafeSugar.releaseClassLoader(applicationClassLoader);

        System.out.println();
    }

    @Test
    public void todoList() {
        System.out.println("ClassUtils.getDefaultClassLoader()----->" + ClassUtils.getDefaultClassLoader());
        System.out.println("Thread.currentThread().getContextClassLoader()----->" + Thread.currentThread().getContextClassLoader());
        System.out.println("ClassLoaderSugar.class.getClassLoader()----->" + ClassLoaderSugar.class.getClassLoader());
        System.out.println("ClassLoader.getSystemClassLoader()----->" + ClassLoader.getSystemClassLoader());
        System.out.println("ClassLoaderSugar.getDefaultClassLoader()----->" + ClassLoaderSugar.getDefaultClassLoader());
        System.out.println("-------------------");
    }
}
