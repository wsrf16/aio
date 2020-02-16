package com.aio.portable.swiss.suite.bean.serializer.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class YamlSugar {
    private final static Yaml YAML = new Yaml();

    public static <T> T yaml2T(ClassLoader classLoader, String resource) {
        InputStream inputStream = classLoader
                .getResourceAsStream(resource);
        T t = YAML.load(inputStream);
        return t;
    }

    public static <T> T yaml2T(String yaml) throws FileNotFoundException {
        T t = YamlSugar.YAML.load(yaml);
        return t;
    }

    public static <T> T yaml2T(File file) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(file);
        T t = YAML.load(inputStream);
        return t;
    }

    public static <T> T yaml2T(InputStream inputStream) {
        T t = YAML.load(inputStream);
        return t;
    }

    public static String obj2Yaml(Object obj) {
        return YAML.dump(obj);
    }

    public static void obj2Yaml(Object obj, Writer writer) {
        YAML.dump(obj, writer);
    }
}
