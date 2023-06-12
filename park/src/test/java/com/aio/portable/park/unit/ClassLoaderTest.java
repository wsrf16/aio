package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import com.aio.portable.swiss.sugar.resource.StreamClassLoader;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import org.springframework.boot.test.context.TestComponent;

import javax.tools.DiagnosticCollector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        Object yaml1 = ClassLoaderSugar.load(name, ClassLoaderSugar.getDefaultClassLoader(), false);
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
            Object result = ClassSugar.invoke(cls, "todo");
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
}
